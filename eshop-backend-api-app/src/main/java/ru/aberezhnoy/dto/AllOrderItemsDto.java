package ru.aberezhnoy.dto;

import java.math.BigDecimal;
import java.util.List;

public class AllOrderItemsDto {

    private List<OrderLineItemDto> orderLineItems;

    private BigDecimal subtotal;

    public AllOrderItemsDto() {
    }

    public AllOrderItemsDto(List<OrderLineItemDto> orderLineItems, BigDecimal subtotal) {
        this.orderLineItems = orderLineItems;
        this.subtotal = subtotal;
    }

    public List<OrderLineItemDto> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItemDto> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
