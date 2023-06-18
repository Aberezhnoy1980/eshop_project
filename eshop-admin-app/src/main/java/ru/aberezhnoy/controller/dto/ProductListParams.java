package ru.aberezhnoy.controller.dto;

public class ProductListParams {

    private String productNameFilter;

    private Integer page;

    private Integer size;

    private String sortField;

    public String getProductNameFilter() {
        return productNameFilter;
    }

    public void setProductNameFilter(String brandNameFilter) {
        this.productNameFilter = productNameFilter;
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
