package com.sdn.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sku_type")
public class SkuType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "sku_type")
    private String skuType;
}
