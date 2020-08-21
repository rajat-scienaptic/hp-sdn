package com.sdn.model.sdn;

import lombok.Data;

import javax.persistence.Column;

@Data
public class SdcViolationsType {
    @Column(name = "seller_id_and_contract_details")
    private Integer sellerIdAndContractDetails;
    @Column(name = "correct_image")
    private Integer correctImage;
    @Column(name = "naming")
    private Integer naming;
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
    @Column(name = "vat")
    private Integer vat;
    @Column(name = "email_address")
    private Integer emailAddress;
    @Column(name = "physical_address")
    private Integer physicalAddress;
    @Column(name = "phone_number")
    private Integer phoneNumber;
    @Column(name = "product_name")
    private Integer productName;
    @Column(name = "sku_number")
    private Integer skuNumber;
}
