package ru.aberezhnoy.service;

import ru.aberezhnoy.controller.dto.OrderDto;

import java.util.List;

public interface OrderService {

    void createOrder(String username);

    List<OrderDto> findOrdersByUsername(String username);
}
