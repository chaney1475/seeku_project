package com.hanium.seeku.Service;
import com.hanium.seeku.Model.PodUsage;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MetricService {

    private final RestTemplate restTemplate;
    private final String PROMETHEUS_URL = "http://3.15.239.235:32236/api/v1/query";

    public MetricService() {
        this.restTemplate = new RestTemplate();
    }

    public PodUsage getPodMetrics() {
        PodUsage podUsage = new PodUsage();

        podUsage.setCpuUsage(getMetricValue("YOUR_CPU_USAGE_QUERY"));
        podUsage.setMemoryUsage(getMetricValue("YOUR_MEMORY_USAGE_QUERY"));
        podUsage.setProcessCpuUsage(getMetricValue("YOUR_PROCESS_CPU_USAGE_QUERY"));
        podUsage.setNetworkUsage(getMetricValue("YOUR_NETWORK_USAGE_QUERY"));
        podUsage.setName(getMetricValueAsString("YOUR_NAME_QUERY"));

        return podUsage;
    }

    public Double getPodCpuUsage(String namespace, String podName) {
        String query = "rate(container_cpu_usage_seconds_total{namespace=\"" + namespace + "\", pod_name=\"" + podName + "\"}[5m])";
        String fullUrl = PROMETHEUS_URL + "?query=" + query;

        String response = restTemplate.getForObject(fullUrl, String.class);

        // Parse the JSON response to get the metric value
        JSONObject jsonResponse = new JSONObject(response);
        Double cpuUsage = jsonResponse.getJSONObject("data").getJSONArray("result").getJSONObject(0).getJSONObject("value").getDouble(String.valueOf(1));

        return cpuUsage;
    }
    private double getMetricValue(String query) {
        ResponseEntity<String> response = restTemplate.getForEntity(PROMETHEUS_URL + "?query=" + query, String.class);
        String responseBody = response.getBody();
        return extractMetricValueFromResponse(responseBody);
    }

    private String getMetricValueAsString(String query) {
        ResponseEntity<String> response = restTemplate.getForEntity(PROMETHEUS_URL + "?query=" + query, String.class);
        String responseBody = response.getBody();
        // 값 추출 로직에 따라 적절한 문자열 값을 반환하도록 코드를 수정하세요.
        return "";
    }

    private double extractMetricValueFromResponse(String responseBody) {
        // 실제 메트릭 값을 추출하는 로직을 구현합니다.
        return 0.0;  // 임시 코드입니다.
    }
}