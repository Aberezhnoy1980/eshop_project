package ru.aberezhnoy.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.aberezhnoy.persist.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class OrderDto {

    private Long id;

    private LocalDateTime orderDate;

    private String status;

    private String username;

    private List<OrderLineItemDto> orderLineItems;

    public OrderDto() {
    }

    public OrderDto(Long id,
                    LocalDateTime orderDate,
                    String status,
                    String username,
                    List<OrderLineItemDto> orderLineItems) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.username = username;
        this.orderLineItems = orderLineItems;
    }

    public OrderDto(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus().name();
        this.username = order.getUser().getUsername();
        this.orderLineItems = order
                .getOrderLineItems()
                .stream()
                .map(li -> new OrderLineItemDto())
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderLineItemDto> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItemDto> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }
}
