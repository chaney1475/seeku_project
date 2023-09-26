package com.hanium.seeku.Model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class PodData {
    private String name;
    private String image;
    private String label;
    private String node;
    private String status;
    private int restartCount;
    private String creationTime;
    private String cpuUsageCores;

    public PodData() {

    }
}
