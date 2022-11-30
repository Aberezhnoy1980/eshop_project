package ru.aberezhnoy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aberezhnoy.controller.dto.OrderDto;
import ru.aberezhnoy.controller.dto.OrderLineItemDto;
import ru.aberezhnoy.persist.OrderRepository;
import ru.aberezhnoy.persist.ProductRepository;
import ru.aberezhnoy.persist.UserRepository;
import ru.aberezhnoy.persist.model.Order;
import ru.aberezhnoy.persist.model.OrderLineItem;
import ru.aberezhnoy.persist.model.Product;
import ru.aberezhnoy.persist.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CartService cartService;

//    private final SimpMessagingTemplate template;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            ProductRepository productRepository,
                            CartService cartService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
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
//                template.convertAndSend("/order_out/order", new OrderStatus(order.getId(), status.toString()));
            }
        }).start();
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No product with id"));
    }
}