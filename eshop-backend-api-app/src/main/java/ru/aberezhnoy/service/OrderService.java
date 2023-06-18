package ru.aberezhnoy.service;

import ru.aberezhnoy.dto.OrderDto;
import ru.aberezhnoy.dto.OrderLineItemDto;
import ru.aberezhnoy.persist.model.Order;
import ru.aberezhnoy.persist.model.OrderLineItem;
import ru.aberezhnoy.persist.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    void createOrder(String username);

    List<OrderDto> findOrdersByUsername(String username);

    void clear(String username);

    void deleteOrder(Long id);

    BigDecimal getSubtotal(Long id);

    void deleteOrderLineItem(Long id);

    List<OrderLineItemDto> findItemsByOrder(Long id);

    Optional<Order> findOrderById(Long id);

    Optional<OrderLineItem> findItemById(Long id);


}
