package ru.aberezhnoy.persist.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 1024)
    private String name;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "storage_file_name", unique = true, nullable = false, length = 256)
    private String storageFileName;

    @ManyToOne
    private Product product;

    public Picture() {
    }

    public Picture(Long id, String name, String contentType, String storageFileName, Product product) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.storageFileName = storageFileName;
        this.product = product;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getStorageFileName() {
        return storageFileName;
    }

    public void setStorageFileName(String storageFileName) {
        this.storageFileName = storageFileName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Picture picture = (Picture) o;
        return Objects.equals(id, picture.id) && Objects.equals(name, picture.name) && Objects.equals(contentType, picture.contentType) && Objects.equals(storageFileName, picture.storageFileName);
    }

    public int hashCode() {
        return Objects.hash(id, name, contentType, storageFileName);
    }
}
