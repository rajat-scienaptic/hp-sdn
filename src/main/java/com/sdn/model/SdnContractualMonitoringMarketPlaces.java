package com.sdn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "sdn_contractual_monitoring_marketplaces")
@Data
public class SdnContractualMonitoringMarketPlaces {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "url")
    private String url;
    @Column(name = "platform")
    private String platform;
    @Column(name = "country_marketplace")
    private String countryMarketPlace;
    @Column(name = "seller_name")
    private String sellerName;
    @Column(name = "seller_id")
    private Integer sellerId;
    @Column(name = "seller_location")
    private String sellerLocation;
    @Column(name = "country_seller")
    private String countrySeller;
    @Column(name = "sku")
    private String sku;
    @Column(name = "sku_type")
    private String skuType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @Column(name = "publication_date")
    private Date publicationDate;
    @Column(name = "created_timestamp")
    private LocalDateTime createdTimestamp;
}
