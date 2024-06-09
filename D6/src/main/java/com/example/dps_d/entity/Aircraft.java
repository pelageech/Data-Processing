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
@Table(name = "aircrafts")
@AllArgsConstructor
@NoArgsConstructor
public class Aircraft {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "aircraft_code")
    private String aircraftCode;

    @Column(name = "model")
    private String model;

    @Column(name = "range")
    private Long range;
}
