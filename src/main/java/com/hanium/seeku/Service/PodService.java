package com.hanium.seeku.Service;
import com.hanium.seeku.Model.PodData;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.apps.DeploymentList;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static io.fabric8.kubernetes.client.impl.KubernetesClientImpl.logger;

@Service
@RequiredArgsConstructor
public class PodService {

    private final KubernetesClient kubernetesClient;
    private final DateUtils dateUtils;
    public DeploymentList getDeploymentsList() {
        return kubernetesClient.apps().deployments().list();
    }

    public List<PodData> getPodList() {
        List<Pod> pods = kubernetesClient.pods().inAnyNamespace().list().getItems();
        List<PodData> podDataList = new ArrayList<>();

        for (Pod pod : pods) {
            PodData podData = new PodData();
            podData.setName(pod.getMetadata().getName());
            podData.setNode(pod.getSpec().getNodeName());
            podData.setStatus( pod.getStatus().getPhase());
            podData.setRestartCount(pod.getStatus().getContainerStatuses().get(0).getRestartCount());
            podData.setCreationTime(dateUtils.formatDuration(pod.getMetadata().getCreationTimestamp()));

            podData.setImage(pod.getSpec().getContainers().get(0).getImage());
            Map<String, String> labels = pod.getMetadata().getLabels();
            String label = labels != null ? labels.toString() : null;
            podData.setLabel(label);


            kubernetesClient.top().pods().metrics("default").getItems().stream()
                    .filter(podMetrics -> podData.getName().equals(podMetrics.getMetadata().getName()))
                    .forEach(podMetrics -> {
                        podMetrics.getContainers().forEach(containerMetrics -> {
                            // CPU 사용량 추출 및 podData에 설정
                            String cpuUsage = containerMetrics.getUsage().get("cpu").getAmount();
                            String format = containerMetrics.getUsage().get("cpu").getFormat();
                            podData.setCpuUsageCores(cpuUsage + format);

                        });
                    });
            podDataList.add(podData);
        }

        return podDataList;
    }
}
