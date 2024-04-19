package com.example.flink_demo.flink;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;


@Service
public class RealTimeProcessing {

    public static void main(String[] args) throws Exception {

        String topicName = "flink_topic";
        String groupName = "flink_group";
        String kafkaServers = "dev-aitoc-0.persistent.uc.mobvoi-idc.com:9092,dev-aitoc-1.persistent.uc.mobvoi-idc.com:9092,dev-aitoc-2.persistent.uc.mobvoi-idc.com:9092";

        // 创建 Kafka Producer
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        // 发送数据到 Kafka
        for (int i = 0; i < 100; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("flink_topic", Integer.toString(i), Integer.toString(i));
            producer.send(record);
        }

        // 创建 Flink Job
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(
                3, // 尝试重启的次数
                Time.of(10, TimeUnit.SECONDS) // 间隔
        ));

        Properties consumerProperties = new Properties();
        consumerProperties.setProperty("bootstrap.servers", kafkaServers);
        consumerProperties.setProperty("group.id", groupName);

        DataStream<String> stream = env.addSource(new FlinkKafkaConsumer<>("flink_topic", new SimpleStringSchema(), consumerProperties));

        // 数据处理
        DataStream<String> processedText = stream
                .map(new MapFunction<String, String>() {
                    @Override
                    public String map(String value) {
                        String res = "---Processed---: " + value;
                        System.out.println(res);
                        return res;
                    }
                });

        // 输出结果到控制台
        processedText.print();

        // 启动 Flink Job
        env.execute("String flink Processing Job");

        // // 异步启动  Flink Job
        // env.executeAsync("Flink Job Name");
    }
}