package com.hanium.seeku.Model;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class MetricAlertSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 5, scale = 2, nullable = true)
    private BigDecimal cpuUsage;

    @Column(precision = 5, scale = 2, nullable = true)
    private BigDecimal memoryUsage;

    @Column(precision = 5, scale = 2, nullable = true)
    private BigDecimal processCpuUsage;

    @Column(precision = 10, scale = 2, nullable = true)
    private BigDecimal networkUsage;

    @Column(nullable = false)
    private String settingName;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}
