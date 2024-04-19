// package com.example.flink_demo.task;
//
// import com.example.flink_demo.flink.FlinkJobService;
// import java.time.LocalDateTime;
// import javax.annotation.Resource;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Service;
//
// @Service
// public class TaskSchedule {
//
//     @Resource
//     private FlinkJobService flinkJobService;
//
//     @Scheduled(fixedRate = 3000)
//     public void schedule() throws Exception {
//         System.out.println("schedule:" + LocalDateTime.now());
//         flinkJobService.runFlinkJob();
//     }
//
// }
