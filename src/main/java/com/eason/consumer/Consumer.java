package com.eason.consumer;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

public class Consumer {

    public static void start(String consumerName, String topic, String tags) throws  MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerName);

        consumer.setNamesrvAddr("10.112.117.255:9876");

        consumer.setConsumeThreadMax(2);
        consumer.setConsumeThreadMin(2);
        consumer.subscribe(topic, tags);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
//        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.setMessageModel(MessageModel.CLUSTERING);

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                ConsumeConcurrentlyContext context) {
                msgs.stream().map(msg -> msg.getKeys()+"  "+Thread.currentThread().getName()+"   "+consumerName).forEach(System.out::println);
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        System.out.printf(consumerName + "Consumer Started.%n");
    }
}