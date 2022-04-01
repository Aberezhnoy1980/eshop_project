package ru.aberezhnoy.controller.dto;

public class CategoryListParams {

    private String categoryNameFilter;

    private Integer page;

    private Integer size;

    private String sortField;

    public String getCategoryNameFilter() {
        return categoryNameFilter;
    }

    public void setCategoryNameFilter(String categoryNameFilter) {
        this.categoryNameFilter = categoryNameFilter;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }
}
