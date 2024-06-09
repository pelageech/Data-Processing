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
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "aircraft_code")
    private String aircraftCode;

    @EqualsAndHashCode.Include
    @Column(name = "seat_no")
    private String seatNo;

    @Column(name = "fare_conditions")
    private String fareConditions;

}
