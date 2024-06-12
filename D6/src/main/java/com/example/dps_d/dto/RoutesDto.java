package com.example.dps_d.dto;

import com.example.dps_d.entity.Flight;
import java.util.List;
import lombok.Data;

@Data
public class RoutesDto {

    private Integer connectionNum;
    private List<Flight> flights;
    private String route;
    private Float price;

}
