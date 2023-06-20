package ru.aberezhnoy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.aberezhnoy.dto.AllOrderItemsDto;
import ru.aberezhnoy.dto.OrderDto;
import ru.aberezhnoy.dto.OrderLineItemDto;
import ru.aberezhnoy.exception.ResourceNotFoundException;
import ru.aberezhnoy.persist.model.Order;
import ru.aberezhnoy.persist.model.OrderLineItem;
import ru.aberezhnoy.service.OrderService;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RequestMapping("/v1/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Orders
    @PostMapping
    public void createOrder(Authentication auth) {
        orderService.createOrder(auth.getName());
    }

    @GetMapping("/all")
    public List<OrderDto> findAll(Authentication auth) {
        return orderService.findOrdersByUsername(auth.getName());
    }

    @PutMapping
    public OrderDto updateOrder(@RequestBody OrderDto orderDto) {
        return new OrderDto(orderService
                .findOrderById(orderDto
                        .getId()).orElseThrow(() -> new ResourceNotFoundException(
                        "Order with id = " + orderDto.getId() + " not found")
                ));
    }

    @DeleteMapping()
    public void deleteOrder(@RequestBody OrderDto orderDto) {
        orderService.deleteOrder(orderDto.getId());
    }

    // OrderItems
    @GetMapping("/{id}")
    public List<OrderLineItemDto> findItemsByOrder(@PathVariable Long id) {
        return orderService.findItemsByOrder(id);
    }

    @GetMapping("/item/{id}")
    public AllOrderItemsDto allItemsByOrder(@PathVariable Long id) {
        return new AllOrderItemsDto(orderService.findItemsByOrder(id), orderService.getSubtotal(id));
    }

    @PutMapping("/item")
    public void updateOrderItem(@RequestBody OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = orderService
                .findItemById(orderLineItemDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item with id = " + orderLineItemDto.getId() + " not found")
                );
        orderLineItem.setQty(orderLineItemDto.getQty());
        orderLineItem.setColor(orderLineItemDto.getColor());
        orderLineItem.setMaterial(orderLineItemDto.getMaterial());
    }

    @DeleteMapping("/item")
    public void deleteOrderItem(@RequestBody OrderLineItemDto orderLineItemDto) {
        orderService.deleteOrderLineItem(orderLineItemDto.getId());
    }

    @GetMapping("/clear")
    public void clear(Authentication auth) {
        orderService.clear(auth.getName());
    }
}
