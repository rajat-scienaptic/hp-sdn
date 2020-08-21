package com.sdn.model.sdn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sdn_enforcements")
@Builder(toBuilder = true)
@Data
public class SdnEnforcements {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
    @Column(name = "notification_letter_sent_date")
    private String noticeLetterSentDate;
    @Column(name = "warning_letter_sent_date")
    private String warningLetterSentDate;
}
