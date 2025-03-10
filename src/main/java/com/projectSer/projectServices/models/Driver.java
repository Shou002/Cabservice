package com.projectSer.projectServices.models;

import com.projectSer.projectServices.enums.DriverStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private int driverId;

    private String name;
    private int age;
    private String pn;
    private String address;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;
}
