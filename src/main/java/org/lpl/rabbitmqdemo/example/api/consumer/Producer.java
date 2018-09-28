package org.lpl.rabbitmqdemo.example.api.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * Created by liupenglei on 2018/9/28.
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建一个连接Factory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.74.184.193");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2.创建一个connection
        Connection connection = connectionFactory.newConnection();
        //3.创建一个channel信道
        Channel channel = connection.createChannel();

        //4。指定消息的确认模式
        channel.confirmSelect();

        String exchangeName = "test_consumer_exchange";
        String routingKey = "consumer.save";

        String msgBody = "Hello RabbitMq Consumer message";
        for(int i=0;i<5;i++){
            channel.basicPublish(exchangeName, routingKey, null, msgBody.getBytes());
        }
    }
}
