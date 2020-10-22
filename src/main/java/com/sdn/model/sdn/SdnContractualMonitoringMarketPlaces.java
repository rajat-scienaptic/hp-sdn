package com.sdn.model.sdn;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sdn_contractual_monitoring_marketplaces")
@Data
public class SdnContractualMonitoringMarketPlaces {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "url")
    private String url;
    @Column(name = "platform")
    private String platform;
    @Column(name = "country_marketplace")
    private String countryMarketplace;
    @Column(name = "seller_name")
    private String sellerName;
    @Column(name = "seller_id")
    private String sellerId;
    @Column(name = "seller_location")
    private String sellerLocation;
    @Column(name = "sku")
    private String sku;
    @Column(name = "sku_type")
    private String skuType;
    @Column(name = "publication_date")
    private String publicationDate;
//    @Column(name = "created_timestamp")
//    private LocalDateTime createdTimestamp;
}
