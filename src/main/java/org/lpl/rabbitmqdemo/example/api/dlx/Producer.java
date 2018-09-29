package org.lpl.rabbitmqdemo.example.api.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.save";

        String msgBody = "Hello RabbitMQ DLX Message";

        for(int i = 0;i < 1; i++){

            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .expiration("10000")    //设置过期时间为10s,10没有被消费就会进入死信队列中
                    .build();

            channel.basicPublish(exchangeName, routingKey, true, properties, msgBody.getBytes());
        }
    }
}
