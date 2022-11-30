package ru.aberezhnoy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.aberezhnoy.aspect.TrackTime;
import ru.aberezhnoy.controller.dto.AddLineItemDto;
import ru.aberezhnoy.controller.dto.AllCartDto;
import ru.aberezhnoy.controller.dto.ProductDto;
import ru.aberezhnoy.service.CartService;
import ru.aberezhnoy.service.ProductService;
import ru.aberezhnoy.service.dto.LineItem;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RequestMapping("v1/cart")
@RestController
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @TrackTime
    @PostMapping(produces = "application/json", consumes = "application/json")
    public List<LineItem> addToCart(@RequestBody AddLineItemDto addLineItemDto) {
        logger.info("New LineItem. ProductId = {}, qty = {}", addLineItemDto.getProductId(), addLineItemDto.getQty());

        ProductDto productDto = productService.findById(addLineItemDto.getProductId())
                .orElseThrow(RuntimeException::new);
        cartService.addProductQty(productDto, addLineItemDto.getColor(), addLineItemDto.getMaterial(), addLineItemDto.getQty());
        return cartService.getLineItems();
    }

    //TODO реализовать на фронте метод update (счетчик кол-ва в корзине) при изменении количества меняется totalItem & subTotal, происходит добавление/удаление по количеству
    @PutMapping(produces = "application/json", consumes = "application/json")
    public void updateLineItemByQty(@RequestBody LineItem lineItem, @RequestBody int qty) {
        if (qty > lineItem.getQty())
            cartService.addProductQty(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial(), lineItem.getQty());
        else if (qty < lineItem.getQty())
            cartService.removeProductQty(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial(), lineItem.getQty());
        else
            cartService.removeProduct(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial());
    }

    @DeleteMapping(consumes = "application/json")
    public void deleteLineItem(@RequestBody LineItem lineItem) {
        cartService.removeProduct(lineItem.getProductDto(),
                lineItem.getColor(),
                lineItem.getMaterial());
    }

    @DeleteMapping
    public void deleteLineItemQty(@RequestBody LineItem lineItem) {
        cartService.removeProductQty(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial(), lineItem.getQty());
    }

    @GetMapping("/all")
    public AllCartDto findAll() {
        return new AllCartDto(cartService.getLineItems(), cartService.getSubTotal());
    }

    @GetMapping("/clear")
    public void clear() {
        cartService.clear();
    }
}
