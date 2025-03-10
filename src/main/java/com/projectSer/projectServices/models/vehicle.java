package com.projectSer.projectServices.models;

import com.projectSer.projectServices.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;

import com.projectSer.projectServices.enums.vehicleType;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int veh_id;

    @ManyToOne
    @JoinColumn(name = "driver_id",nullable = false)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private vehicleType type;

    private String Veh_name;
    private double price;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
}
