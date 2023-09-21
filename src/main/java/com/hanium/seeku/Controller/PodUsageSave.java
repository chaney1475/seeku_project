package com.hanium.seeku.Controller;

import com.hanium.seeku.Model.PodUsage;
import com.hanium.seeku.Repository.PodUsageRepository;
import com.hanium.seeku.Service.UsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class PodUsageSave {
    private final UsageService usageService;
    @GetMapping("/alert/field")
    public String newForm(){
        return "alert/Field";
    }

    @PostMapping("/alert/field/save")
    public String saveForm(@ModelAttribute PodUsage podUsage){
        log.info("podUsage : " + podUsage.toString());
        log.info("Pod Usage information is saved");
        usageService.savePodUsageForm(podUsage);
        return "alert/Field";
    }

}
