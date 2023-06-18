package ru.aberezhnoy.controller.dto;

public class BrandListParams {

    private String brandNameFilter;

    private Integer page;

    private Integer size;

    private String sortField;

    public String getBrandNameFilter() {
        return brandNameFilter;
    }

    public void setBrandNameFilter(String brandNameFilter) {
        this.brandNameFilter = brandNameFilter;
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
