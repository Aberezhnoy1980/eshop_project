package ru.aberezhnoy.controller.dto;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ProductDto {

    private Long id;

    private String name;

    private BrandDto brand;

    private String description;

    private BigDecimal price;

    private CategoryDto category;

    private MultipartFile[] newPicture;

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

    public MultipartFile[] getNewPicture() {
        return newPicture;
    }

    public void setNewPicture(MultipartFile[] newPicture) {
        this.newPicture = newPicture;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDto)) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getBrand(), that.getBrand()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getCategory(), that.getCategory()) && Arrays.equals(getNewPicture(), that.getNewPicture()) && Objects.equals(getPictures(), that.getPictures());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getName(), getBrand(), getDescription(), getPrice(), getCategory(), getPictures());
        result = 31 * result + Arrays.hashCode(getNewPicture());
        return result;
    }
}
