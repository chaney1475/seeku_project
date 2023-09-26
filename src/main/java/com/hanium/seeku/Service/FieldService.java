package com.hanium.seeku.Service;

import com.hanium.seeku.Model.MetricAlertSettings;
import com.hanium.seeku.Repository.MetricAlertSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldService {
    private final MetricAlertSettingsRepository metricRepository;

    public void saveMetricAlertSettings(MetricAlertSettings mAlertSetting){
        metricRepository.save(mAlertSetting);
    }
}
