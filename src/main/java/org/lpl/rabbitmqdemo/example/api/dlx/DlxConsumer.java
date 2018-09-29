package org.lpl.rabbitmqdemo.example.api.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 监听死信队列
 * Created by liupenglei on 2018/9/29.
 */
public class DlxConsumer {

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

        String exchangeName = "dlx.queue";
        String rountingKey = "#";
        String queueName = "dlx.queue";

        //4.声明
        channel.exchangeDeclare(exchangeName,"topic",true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,rountingKey);

        channel.basicConsume(queueName,new MyConsumer(channel));
    }
}
