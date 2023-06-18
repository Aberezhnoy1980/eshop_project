package ru.aberezhnoy.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.aberezhnoy.persist.model.Brand;

import java.io.Serializable;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class BrandDto implements Serializable {

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

    public BrandDto(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
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
        if (o == null || this.getClass() != o.getClass()) return false;
        BrandDto brandDto = (BrandDto) o;
        return Objects.equals(id, brandDto.id) && Objects.equals(name, brandDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
