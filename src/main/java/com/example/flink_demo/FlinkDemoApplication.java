package com.example.flink_demo;

import com.example.flink_demo.flink.FlinkJobService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlinkDemoApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(FlinkDemoApplication.class, args);

        // 获取 FlinkJobService Bean，并运行 Flink 作业
        FlinkJobService flinkJobService = context.getBean(FlinkJobService.class);
        flinkJobService.runFlinkJob();
        flinkJobService.runRealTimeProcessing();
    }

}
