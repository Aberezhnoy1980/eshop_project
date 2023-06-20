package ru.aberezhnoy.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.aberezhnoy.persist.model.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class ProductDto implements Serializable {

    private Long id;

    private String name;

    private BrandDto brand;

    private String description;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
    @JsonSubTypes({ @JsonSubTypes.Type(name = "BIG_DECIMAL", value = BigDecimal.class) })
    private BigDecimal price;

    private CategoryDto category;

    private List<Long> pictures;

    public ProductDto() {
    }

    public ProductDto(Long id, String name, BrandDto brand, String description, BigDecimal price,
                      CategoryDto category, List<Long> pictures) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.category = category;
        this.pictures = pictures;
    }

    public ProductDto(Product product, BrandDto brandDto, CategoryDto categoryDto) {
        this.id = product.getId();
        this.name = product.getName();
        this.brand = brandDto;
        this.category = categoryDto;
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.pictures = getPictures();
    }

    public BrandDto getBrand() {
        return brand;
    }

    public void setBrand(BrandDto brand) {
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public List<Long> getPictures() {
        return pictures;
    }

    public void setPictures(List<Long> pictures) {
        this.pictures = pictures;
    }

}
