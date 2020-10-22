package com.sdn.model.sdn;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sdn_transaction_monitoring_marketplaces")
@Data
public class SdnTransactionMonitoringMarketplaces {
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @Column(name = "publication_date")
    private String publicationDate;
    @Column(name = "seller_id_and_contract_details")
    private String sellerIdAndContractDetails;
    @Column(name = "vat")
    private String vat;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "physical_address")
    private String physicalAddress;
    @Column(name = "correct_image")
    private String correctImage;
    @Column(name = "naming")
    private String naming;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "sku_naming")
    private String skuNaming;
    @Column(name = "compatible_printers")
    private String compatiblePrinters;
    @Column(name = "co_operation")
    private String coOperation;
    @Column(name = "avoid_customer_confusion")
    private String avoidCustomerConfusion;
    @Column(name = "grey_trade")
    private String greyTrade;
    @Column(name = "shopping_experience")
    private String shoppingExperience;
//    @Column(name = "created_timestamp")
//    private LocalDateTime createdTimestamp;
}
