package com.example.catalogservice.vo;

import com.example.catalogservice.jpa.CatalogEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalog {
    private String productId;
    private String productName;
    private Integer unitPrice;
    private Integer totalPrice;
    private Integer stock;
    private Date createdAt;
}
