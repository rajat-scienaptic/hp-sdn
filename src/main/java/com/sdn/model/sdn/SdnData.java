package com.sdn.model.sdn;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@Table(name = "sdn_data")
public class SdnData {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "country_marketplace")
    private String countryMarketPlace;
    @Column(name = "country_seller")
    private String countrySeller;
    @Column(name = "seller_name")
    private String sellerName;
    @Column(name = "partner_id")
    private Integer partnerId;
    @Column(name = "type_of_partner")
    private String typeOfPartner;
    @Column(name = "attended_seller")
    private String attendedSeller;
    @Column(name = "url")
    private String url;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @Column(name = "publish_date")
    private Date date;
    @Column(name = "sdc_scoring")
    private Integer sdcScoring;
    @Column(name = "enforcements_status")
    private String enforcementsStatus;
    @Column(name = "enforcements_date")
    private String enforcementsDate;
    @Column(name = "sku_number")
    private String skuNumber;
    @Column(name = "type_of_sku")
    private String typeOfSku;
    @Column(name = "sdc_violations")
    private String sdcViolations;
    @Column(name = "notes")
    private String notes;
    @Column(name = "type")
    private String type;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    @Column(name = "created_timestamp")
    private LocalDateTime createdTimestamp;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    @Column(name = "last_modified_timestamp")
    private LocalDateTime lastModifiedTimestamp;
}
