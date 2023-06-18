package ru.aberezhnoy.dto;

import ru.aberezhnoy.persist.model.OrderLineItem;

import java.math.BigDecimal;

public class OrderLineItemDto {

    private Long id;

    private Long orderId;

    private Long productId;

    private String productName;

    private BigDecimal price;

    private Integer qty;

    private String color;

    private String material;

    public OrderLineItemDto() {
    }

    public OrderLineItemDto(Long id,
                            Long orderId,
                            Long productId,
                            String productName,
                            BigDecimal price,
                            Integer qty,
                            String color,
                            String material) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.qty = qty;
        this.color = color;
        this.material = material;
    }

    public OrderLineItemDto(OrderLineItem orderLineItem) {
        this.id = orderLineItem.getId();
        this.orderId = orderLineItem.getOrder().getId();
        this.productId = orderLineItem.getProduct().getId();
        this.productName = orderLineItem.getProduct().getName();
        this.price = orderLineItem.getPrice();
        this.qty = orderLineItem.getQty();
        this.color = orderLineItem.getColor();
        this.material = orderLineItem.getMaterial();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public BigDecimal getOrderItemTotal() {
        return getPrice().multiply(new BigDecimal(qty));
    }
}
