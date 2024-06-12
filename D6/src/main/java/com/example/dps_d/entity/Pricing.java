package com.example.dps_d.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString
@Entity
@Table(name = "pricings")
@AllArgsConstructor
@NoArgsConstructor
public class Pricing {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "flight_no")
    private String flightNo;

    @Column(name = "fare_conditions")
    private String fareConditions;

    @Column(name = "amount")
    private Float amount;
}
