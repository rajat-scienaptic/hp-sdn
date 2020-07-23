package com.sdn.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@Table(name = "sku")
public class SKU {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type_of_sku")
    private String typeOfSku;
    @Column(name = "sku_number")
    private String skuNumber;
    @Column(name = "country_id")
    private int countryId;
}
