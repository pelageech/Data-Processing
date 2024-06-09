package com.example.dps_d.dto;

import com.example.dps_d.entity.Flight;
import lombok.Data;

@Data
public class RoutesModel {

    private Integer connectionNum;
    private Flight flight1;
    private Flight flight2;
    private Flight flight3;
    private Flight flight4;
    private String route;

    public RoutesModel(Integer connectionNum, Flight flight1, String route) {
        this.connectionNum = connectionNum;
        this.flight1 = flight1;
        this.route = route;
    }

    public RoutesModel(Integer connectionNum, Flight flight1, Flight flight2, String route) {
        this.connectionNum = connectionNum;
        this.flight1 = flight1;
        this.flight2 = flight2;
        this.route = route;
    }

    public RoutesModel(Integer connectionNum, Flight flight1, Flight flight2, Flight flight3, String route) {
        this.connectionNum = connectionNum;
        this.flight1 = flight1;
        this.flight2 = flight2;
        this.flight3 = flight3;
        this.route = route;
    }

    public RoutesModel(Integer connectionNum, Flight flight1, Flight flight2, Flight flight3, Flight flight4, String route) {
        this.connectionNum = connectionNum;
        this.flight1 = flight1;
        this.flight2 = flight2;
        this.flight3 = flight3;
        this.flight4 = flight4;
        this.route = route;
    }
}
