package ru.aberezhnoy.controller.dto;

import java.util.Objects;

public class BrandDto {

    private Long id;

    private String name;

    public BrandDto() {
    }

    public BrandDto(Long id) {
        this.id = id;
    }

    public BrandDto(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o ==null || this.getClass() != o.getClass()) return false;
        BrandDto brandDto = (BrandDto) o;
        return Objects.equals(id, brandDto.id) && Objects.equals(name, brandDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
