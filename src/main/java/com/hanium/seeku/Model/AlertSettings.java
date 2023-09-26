package com.hanium.seeku.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "AlertSettings")
public class AlertSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String ruleName;

    @Column
    private Boolean status;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    public AlertSettings() {}

    public AlertSettings(String ruleName, Boolean status) {
        this.ruleName = ruleName;
        this.status = status;
    }
}
