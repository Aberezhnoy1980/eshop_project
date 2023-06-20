package ru.aberezhnoy.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.aberezhnoy.persist.model.Category;

import java.io.Serializable;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class CategoryDto implements Serializable {

    private Long id;

    private String name;

    public CategoryDto() {
    }

    public CategoryDto(Long id) {
        this.id = id;
    }

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
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
        if (o == null || o.getClass() != this.getClass()) return false;
        CategoryDto categoryDto = (CategoryDto) o;
        return Objects.equals(id, categoryDto.id) && Objects.equals(name, categoryDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
