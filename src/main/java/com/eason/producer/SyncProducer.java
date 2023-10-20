package com.eason.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class SyncProducer {
    public static void start(String consumerName, String topic, String tags, int msgNum) throws Exception {
        DefaultMQProducer producer = new
            DefaultMQProducer(consumerName);
        producer.setNamesrvAddr("10.112.117.255:9876");

        producer.start();
        for (int i = 0; i < msgNum; i++) {
            Message msg = new Message(topic, tags,
                //"key"+i,
                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}
