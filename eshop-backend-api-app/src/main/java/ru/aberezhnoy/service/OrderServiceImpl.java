package ru.aberezhnoy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aberezhnoy.dto.OrderDto;
import ru.aberezhnoy.dto.OrderLineItemDto;
import ru.aberezhnoy.dto.OrderStatus;
import ru.aberezhnoy.exception.ResourceNotFoundException;
import ru.aberezhnoy.persist.OrderLineItemRepository;
import ru.aberezhnoy.persist.OrderRepository;
import ru.aberezhnoy.persist.ProductRepository;
import ru.aberezhnoy.persist.UserRepository;
import ru.aberezhnoy.persist.model.Order;
import ru.aberezhnoy.persist.model.OrderLineItem;
import ru.aberezhnoy.persist.model.Product;
import ru.aberezhnoy.persist.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final OrderLineItemRepository orderLineItemRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CartService cartService;

    private final SimpMessagingTemplate template;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderLineItemRepository orderLineItemRepository, UserRepository userRepository,
                            ProductRepository productRepository,
                            CartService cartService,
                            SimpMessagingTemplate template) {
        this.orderRepository = orderRepository;
        this.orderLineItemRepository = orderLineItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
        this.template = template;
    }

    @Override
    public List<OrderDto> findOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username).stream()
                .map(o -> new OrderDto(
                        o.getId(),
                        o.getOrderDate(),
                        o.getStatus().name(),
                        o.getUser().getUsername(),
                        o.getOrderLineItems().stream()
                                .map(li -> new OrderLineItemDto(
                                        li.getId(),
                                        li.getOrder().getId(),
                                        li.getProduct().getId(),
                                        li.getProduct().getName(),
                                        li.getPrice(),
                                        li.getQty(),
                                        li.getColor(),
                                        li.getMaterial()
                                )).collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<OrderLineItem> findItemById(Long id) {
        return orderLineItemRepository.findById(id);
    }

    @Override
    @Transactional
    public void createOrder(String username) {
        if (cartService.getLineItems().isEmpty()) {
            logger.info("Can't create order for empty Cart");
            return;
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.save(new Order(
                null,
                LocalDateTime.now(),
                Order.OrderStatus.CREATED,
                user
        ));

        List<OrderLineItem> orderLineItems = cartService.getLineItems()
                .stream()
                .map(li -> new OrderLineItem(
                        null,
                        order,
                        findProductById(li.getProductId()),
                        li.getProductDto().getPrice(),
                        li.getQty(),
                        li.getColor(),
                        li.getMaterial()
                ))
                .collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);
        orderRepository.save(order);
        cartService.clear();

        new Thread(() -> {
            for (Order.OrderStatus status : Order.OrderStatus.values()) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("Sending next status {} for order {}", status, order.getId());
                template.convertAndSend("/order_out/order", new OrderStatus(order.getId(), status.toString()));
            }
        }).start();
    }


    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteOrderLineItem(Long id) {
        orderLineItemRepository.deleteById(id);
    }

    @Override
    public List<OrderLineItemDto> findItemsByOrder(Long id) {
        return orderRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException(
                        "Order with id " + id + " not found"
                ))
                .getOrderLineItems()
                .stream()
                .map(OrderLineItemDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void clear(String username) {
        orderRepository.deleteAll(orderRepository.findAllByUsername(username));
    }

    @Override
    public BigDecimal getSubtotal(Long id) {
        return findItemsByOrder(id)
                .stream()
                .map(OrderLineItemDto::getOrderItemTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No product with id"));
    }
}