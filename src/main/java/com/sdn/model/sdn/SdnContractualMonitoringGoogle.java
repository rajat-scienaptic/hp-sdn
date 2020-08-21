package com.sdn.model.sdn;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sdn_contractual_monitoring_google")
@Data
public class SdnContractualMonitoringGoogle {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "sku")
    private String sku;
    @Column(name = "sku_type")
    private String skuType;
    @Column(name = "country_seller")
    private String countrySeller;
    @Column(name = "domain_name")
    private String domainName;
    @Column(name = "url")
    private String url;
    @Column(name = "country_monitored")
    private String countryMonitored;
    @Column(name = "position")
    private Integer position;
    @Column(name = "collection_date")
    private String collectionDate;
    @Column(name = "created_timestamp")
    private LocalDateTime createdTimestamp;
}
