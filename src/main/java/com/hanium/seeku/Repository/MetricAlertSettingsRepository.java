package com.hanium.seeku.Repository;

import com.hanium.seeku.Model.MetricAlertSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricAlertSettingsRepository extends JpaRepository<MetricAlertSettings, Long> {
}
