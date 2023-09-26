package com.hanium.seeku.Controller.alert;

import com.hanium.seeku.Model.MetricAlertSettings;
import com.hanium.seeku.Service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alert/field")
@RequiredArgsConstructor
public class FieldController {
    private final FieldService fieldService;

    @GetMapping
    public String Field(){
        return "/alert/Field";
    }
    @PostMapping("/save")
    public String saveField(MetricAlertSettings mAlertSetting, Model model){
        fieldService.saveMetricAlertSettings(mAlertSetting);

        return "/alert/Field";
    }
}
