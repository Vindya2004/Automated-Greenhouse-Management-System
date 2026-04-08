package com.agms.zone_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Zone {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double minTemp;
    private Double maxTemp;
    private String deviceId;
}