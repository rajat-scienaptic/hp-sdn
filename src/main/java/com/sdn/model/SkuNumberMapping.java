package com.sdn.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sku_number_mapping")
public class SkuNumberMapping {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "sku_number")
    private String skuNumber;

    @Column(name = "sku_type_id")
    private Integer skuTypeId;
}
