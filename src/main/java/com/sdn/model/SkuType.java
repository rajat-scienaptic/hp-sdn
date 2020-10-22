package com.sdn.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "sku_type")
public class SkuType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "sku_type")
    private String type;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "sku_type_id", referencedColumnName = "id")
    List<SkuNumberMapping> skuNumbers;
}
