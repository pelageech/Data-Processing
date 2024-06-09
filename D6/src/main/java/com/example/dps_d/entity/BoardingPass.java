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
@Table(name = "boarding_passes")
@AllArgsConstructor
@NoArgsConstructor
public class BoardingPass {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "ticket_no")
    private String ticketNo;

    @Column(name = "flight_id")
    private Long flightId;

    @Column(name = "boarding_no")
    private Long boardingNo;

    @Column(name = "seat_no")
    private String seatNo;
}
