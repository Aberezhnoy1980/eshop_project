package ru.aberezhnoy.service;

import ru.aberezhnoy.controller.dto.ProductDto;
import ru.aberezhnoy.service.dto.LineItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface CartService extends Serializable {

    void addProductQty(ProductDto productDto, String color, String material, int qty);

    void removeProductQty(ProductDto productDto, String color, String material, int qty);

    void removeProduct(ProductDto productDto, String color, String material);

    List<LineItem> getLineItems();

    BigDecimal getSubTotal();

    void clear();
}
