package com.hanium.seeku.Controller.dashboard.workloads;

import com.hanium.seeku.Service.PodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dashboard/workloads/pods")
@RequiredArgsConstructor
public class PodsController{

    private final PodService podService;
    @GetMapping()
    public String Pods(Model model){
        model.addAttribute("podDataList",podService.getPodList());
        return "dashboard/workloads/Pods";
    }
    @GetMapping("/details")
    public String getPodDetail(Model model){
        return "dashboard/workloads/podsDetail";
    }

}

