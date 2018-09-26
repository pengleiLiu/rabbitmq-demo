package org.lpl.rabbitmqdemo.example.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by liupenglei on 2018/9/26.
 */
public class Producer4TopicExchange {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.74.184.193");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_topic_exchange";
        String rountingKey1 = "user.save";
        String rountingKey2 = "user.update";
        String rountingKey3 = "user.delete.abc";

        String msg = "Hello Word RabbitMQ 4 Topic Exchange Message....";
        channel.basicPublish(exchangeName,rountingKey1,null,(msg+rountingKey1).getBytes());
        channel.basicPublish(exchangeName,rountingKey2,null,(msg+rountingKey2).getBytes());
        channel.basicPublish(exchangeName,rountingKey3,null,(msg+rountingKey3).getBytes());

        channel.close();
        connection.close();
    }
}
