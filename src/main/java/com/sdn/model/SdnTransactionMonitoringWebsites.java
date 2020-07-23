package com.sdn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "sdn_transaction_monitoring_websites")
@Data
public class SdnTransactionMonitoringWebsites {
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @Column(name = "review_date")
    private Date reviewDate;
    @Column(name = "seller_id_and_contract_details")
    private Integer sellerIdAndContractDetails;
    @Column(name = "vat")
    private Integer vat;
    @Column(name = "physical_address")
    private Integer physicalAddress;
    @Column(name = "email_address")
    private Integer emailAddress;
    @Column(name = "phone_number")
    private Integer phoneNumber;
    @Column(name = "correct_image")
    private Integer correctImage;
    @Column(name = "naming")
    private Integer naming;
    @Column(name = "product_name")
    private Integer productName;
    @Column(name = "sku_number")
    private Integer skuNumber;
    @Column(name = "compatible_printers")
    private Integer compatiblePrinters;
    @Column(name = "co_operation")
    private Integer coOperation;
    @Column(name = "avoid_customer_confusion")
    private Integer avoidCustomerConfusion;
    @Column(name = "grey_trade")
    private Integer greyTrade;
    @Column(name = "shopping_experience")
    private Integer shoppingExperience;
    @Column(name = "created_timestamp")
    private LocalDateTime createdTimestamp;
}
