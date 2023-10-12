package com.hanium.seeku;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.api.model.apps.ReplicaSetList;
import io.fabric8.kubernetes.api.model.metrics.v1beta1.NodeMetricsList;
import io.fabric8.kubernetes.client.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.fabric8.kubernetes.client.impl.KubernetesClientImpl.logger;

public class RestApiTest {
    private String kubernetesUrl = "https://api-seeku-k8s-local-9oflqk-c6b797be949ae642.elb.us-east-2.amazonaws.com";
    private String API_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkQ5TGpDcUg4cmxpVnVZbWQ1R3lsNnJ6NXQ4dkh6ekx1Y3dHWWxoY01OUXMifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6InVzZXItc2Vla3Utc2VjcmV0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6InVzZXItc2Vla3UiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIxYjQwYzlkNy1lZWQzLTRkY2ItYjE0Yy03YmEzODkyNjVlNzQiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6ZGVmYXVsdDp1c2VyLXNlZWt1In0.BHFle0a4GSl6lzZ0kgNUr9mkZVYNSUy6lVOwyuiT5QYGRve6silZKozKHPH3COJ0TIUy-GrUysO-_4GBcW_C2v1OvPXEzq3nC4_Ih-uRyEQCh2hmHt1HQ5z3ctvgwAZ9FKS7-liC5TExwF3cCTN3kTV12Ku3SKXPbMBOdxT8bxJMZJ_WzVPX4mk8JPxm3lZyDcRaolwlXxkbClsExuSMq50Uqj7J576PvTyWzsTjRqKCqGStbWz0X__dNwWknTYnWOUsC5yTsEiPJmrV0brQTbfhI8b0DMqTxQyIZ8zC8G7duysagk8lLoEihvw-q2LYKG8eh0xBRkhZh-imKovY8w";;
    private String caCert = " LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUMrRENDQWVDZ0F3SUJBZ0lNRjRHNWZDakpmUnFkd2toTE1BMEdDU3FHU0liM0RRRUJDd1VBTUJneEZqQVUKQmdOVkJBTVREV3QxWW1WeWJtVjBaWE10WTJFd0hoY05Nak13T1RBeU1UUTBOelU0V2hjTk16TXdPVEF4TVRRMApOelU0V2pBWU1SWXdGQVlEVlFRREV3MXJkV0psY201bGRHVnpMV05oTUlJQklqQU5CZ2txaGtpRzl3MEJBUUVGCkFBT0NBUThBTUlJQkNnS0NBUUVBN2N6bnpqYUN5TW9VUGRwNmVNZWVRcFRrNXJIekE5K2I5SkNXNmE1VDZHR08KR1YzZ3NJYWRFcTFnN3haVVBqTDBjNkg0bHdmOVIwRTV6dmNFOElQK294ckhJYTBCRVhoVHVnYytSSGUrazhIKwpKZ2Y1a1h3dkxIamQza2FnejIrK1IvNkRDTUI0SWIvVndDMjNPTUlTcmtOWWFYeWRYODBsQWNKQmxBVFJFZ3h4CmJlNGlQZzNpdnl1QlQ2aEpjVXZpV09tdXVubVVrZloxVTZ1WW00QVZEUS9JWkw3ZjB4RU54MXVDOGxEaEpYbG0KQS80Qzc2dVhpS0JYdXdWM0lwUnlkOUFSTmRtbGNmV2NjbGtZSkN1NFNhVjVIMXMxYlJFYUFkd2NjdW1jVWlvWApTVUhjMDhzRnRMdVJYNC9adWt0SWkyTThXNnRHaVY4MzZpTldxOGFOMXdJREFRQUJvMEl3UURBT0JnTlZIUThCCkFmOEVCQU1DQVFZd0R3WURWUjBUQVFIL0JBVXdBd0VCL3pBZEJnTlZIUTRFRmdRVUxGdlNuNXJqVFlIWEQxSEoKY2UvWk8raTcyd2t3RFFZSktvWklodmNOQVFFTEJRQURnZ0VCQURWMzJKK2xDbGFMSWVlT1ZxZnBJVVBsMWlzVgowSjc2QkZ6WU4vQ1lIdFB1dy9RMTlrelA2N1RqeFU0NW0vbXdsWWZ1WkM5WWVVOGc5b3liTjExalc5NTc3YkZwCkI2WDZDVmZYdmhsbkNwcC9iZXJPU3IrZ0svRkdoUGg2dElGTk5CMUlDVHVSb3gzN2hrTk9LTi9lQzJiYWJxRVAKMHNFQm5lMFVMZEc0WTBTbW1uMTMyL1dFVEV2UGVzbzVxWjVJY080ZU40ZjIxZFFCbTZUaTBFSURGNUlIMTM0WApobm5qaFBtQjBTS25sdytscWVBV2hteDltcUdwWUhLMjkrTTdIb21mSXdTMjFrUktBNU9pR3dHaUlHbEgzdEJYCjYrS3c3WmNWVkxYTFhySlpwZ3ZYcDdPRmpvNzNhZVpRYkNWZjhvV2lzcmk5TjQ1T1ZjQnhXYU5qK3V3PQotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tCg==";
    Config config = new ConfigBuilder()
            .withCaCertData(caCert)
            .withMasterUrl(kubernetesUrl)
            .withOauthToken(API_TOKEN)
            .withTrustCerts(true)
            .build();
    KubernetesClient client = new DefaultKubernetesClient(config);

    public List<Pod> getPodList() {

        PodList pods = client.pods().inNamespace("default").list();
        return pods.getItems();
    }

    @Test
    public void getPodName() {
        logger.info("=== Pod Names ===");

        for (Pod pod : getPodList()) {
            System.out.println(pod.getMetadata().getName());
        }
    }

    @Test
    public void getPodCPUAndMemory() {
        logger.info("==== Pod Metrics ====");

        client.top().pods().metrics("default").getItems().forEach(podMetrics ->
                podMetrics.getContainers().forEach(containerMetrics ->
                        logger.info("{}\t{}\tCPU: {}{}\tMemory: {}{}",
                                podMetrics.getMetadata().getName(), containerMetrics.getName(),
                                containerMetrics.getUsage().get("cpu").getAmount(), containerMetrics.getUsage().get("cpu").getFormat(),
                                containerMetrics.getUsage().get("memory").getAmount(), containerMetrics.getUsage().get("memory").getFormat()
                        ))
        );
    }

    @Test
    public void getNodeCPUAndMemory() {
        NodeMetricsList nodeMetricList = client.top().nodes().metrics();


        logger.info("==== Node Metrics  ====");
        nodeMetricList.getItems().forEach(nodeMetrics ->
                logger.info("{}\tCPU: {}{}\tMemory: {}{}",
                        nodeMetrics.getMetadata().getName(),
                        nodeMetrics.getUsage().get("cpu").getAmount(), nodeMetrics.getUsage().get("cpu").getFormat(),
                        nodeMetrics.getUsage().get("memory").getAmount(), nodeMetrics.getUsage().get("memory").getFormat()
                ));
    }

    @Test
    public void getReplicaSetCount() {
        logger.info("==== ReplicaSetList count  ====");
        ReplicaSetList replicaSetList = client.apps().replicaSets().inNamespace("default").list();
        logger.info(String.valueOf(replicaSetList.getItems().size()));
    }

}