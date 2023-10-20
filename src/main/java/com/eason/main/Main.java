package com.eason.main;

import com.eason.consumer.Consumer;
import com.eason.producer.SyncProducer;

public class Main {
    public static void main(String[] args) throws Exception {
        String producerNameA = "hqc-producerA";
        String producerNameB = "hqc-producerB";
        String consumerNameA = "hqc-consumerA";
        String consumerNameB = "hqc-consumerB";
        String topic = "TopicTest";
        String tagA = "TagA";
        String tagB = "TagB";

        SyncProducer.start(producerNameA, topic, tagA, 2);
//        Consumer.start(consumerName, topic, tagA);
//        SyncProducer.start(producerNameA, topic, tagB, 1);
        Consumer.start(consumerNameA, topic, "*");
//        Consumer.start(consumerNameB, topic, "*");
    }
}
