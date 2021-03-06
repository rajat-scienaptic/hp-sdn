package com.sdn.model.sdn;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "region")
@Data
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "region")
    private String customerMarket;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    List<Country> countries;
}
