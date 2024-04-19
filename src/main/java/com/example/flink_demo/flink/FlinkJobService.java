package com.example.flink_demo.flink;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class FlinkJobService {

    @Resource
    private StringProcessingJob stringProcessingJob;

    @Resource
    private RealTimeProcessing realTimeProcessing;

    public void runFlinkJob() throws Exception {
        stringProcessingJob.main(new String[]{});
    }

    public void runRealTimeProcessing() throws Exception {
        realTimeProcessing.main(new String[]{});
    }
}
