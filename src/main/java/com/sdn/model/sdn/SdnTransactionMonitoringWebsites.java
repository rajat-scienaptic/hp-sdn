package com.sdn.model.sdn;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sdn_transaction_monitoring_websites")
@Data
public class SdnTransactionMonitoringWebsites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String reviewDate;
    @Column(name = "seller_id_and_contract_details")
    private String sellerIdAndContractDetails;
    @Column(name = "vat")
    private String vat;
    @Column(name = "physical_address")
    private String physicalAddress;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "phone_number")
    private String phoneNumber;
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
