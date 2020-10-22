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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "country_marketplace")
    private String countryMarketPlace;
    @Column(name = "country_seller")
    private String countrySeller;
    @Column(name = "seller_name")
    private String sellerName;
    @Column(name = "partner_name")
    private String partnerName;
    @Column(name = "partner_id")
    private String partnerId;
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
    @Column(name = "outcome")
    private String outcome;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    @Column(name = "created_timestamp")
    private LocalDateTime createdTimestamp;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    @Column(name = "last_modified_timestamp")
    private LocalDateTime lastModifiedTimestamp;
}
