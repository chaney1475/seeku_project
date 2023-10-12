package com.hanium.seeku.Service;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hanium.seeku.Model.NamespaceData;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NamespaceService {

    private final KubernetesClient kubernetesClient;
    private final DateUtils dateUtils;
    public List<NamespaceData> getNamespaceList() {

        List<NamespaceData> dataList = new ArrayList<>();

        for(Namespace n : kubernetesClient.namespaces().list().getItems()){
            NamespaceData namespaceData = new NamespaceData();

            namespaceData.setName(n.getMetadata().getName());
            Map<String, String> labelsMap = n.getMetadata().getLabels();
            namespaceData.setLabels(labelsMap != null ? labelsMap.toString() : null);
            namespaceData.setPhase(n.getStatus().getPhase());

            namespaceData.setCreationTime(dateUtils.formatDuration(n.getMetadata().getCreationTimestamp()));
            dataList.add(namespaceData);
        }
        return dataList;

    }

}
