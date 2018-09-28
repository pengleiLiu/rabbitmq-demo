package org.lpl.rabbitmqdemo.example.api.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by liupenglei on 2018/9/28.
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //1.创建一个连接Factory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.74.184.193");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2.创建一个connection
        Connection connection = connectionFactory.newConnection();
        //3.创建一个channel信道
        Channel channel = connection.createChannel();

        String exchangeName = "test_consumer_exchange";
        String routingKey = "consumer.#";
        String queueName = "test_consumer_queue";

        //4.声明交换机 队列 然后进行绑定设置，最后指定路由Key
        channel.exchangeDeclare(exchangeName,"topic",true);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        channel.basicConsume(queueName,true,new MyConsumer(channel));


    }
}
