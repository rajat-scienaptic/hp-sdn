package com.sdn.model.testbuy;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "test_buy_data_change_logs")
public class TestBuyDataChangeLogs {
    @Id
    @Column(name = "unique_id")
    private String uniqueId;
    @Column(name = "country_marketplace")
    private String countryMarketPlace;
    @Column(name = "seller_name")
    private String sellerName;
    @Column(name = "country_seller")
    private String countrySeller;
    @Column(name = "co_search_seller_info")
    private String coSearchSellerInfo;
    @Column(name = "partner_id")
    private String partnerId;
    @Column(name = "attended_seller")
    private String attendedSeller;
    @Column(name = "ink_toner")
    private String inkToner;
    @Column(name = "sku_number")
    private String skuNumber;
    @Column(name = "total_price_paid")
    private String totalPricePaid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @Column(name = "date_of_request")
    private Date dateOfRequest;
    @Column(name = "determination")
    private String determination;
    @Column(name = "is_fraudulent_and_used")
    private String isFraudulentAndUsed;
    @Column(name = "is_valid_warranty")
    private String isValidWarranty;
    @Column(name = "is_correct_product_shipment")
    private String isCorrectProductShipment;
    @Column(name = "is_incorrect_packaging")
    private String isIncorrectPackaging;
    @Column(name = "t1_seller")
    private String t1Seller;
    @Column(name = "t2_seller")
    private String t2Seller;
    @Column(name = "last_modified_timestamp")
    private LocalDateTime lastModifiedTimestamp;
}
