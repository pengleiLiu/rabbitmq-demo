package org.lpl.rabbitmqdemo.example.api.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 死信队列
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

        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.#";
        String queueName = "test_dlx_queue";

        channel.exchangeDeclare(exchangeName,"topic",true,false,null);

        Map<String,Object> arguments = new HashMap<String,Object>();
        arguments.put("x-dead-letter-exchange", "dlx.exchange");

        //这个arguments属性,要设置到声明队列上
        channel.queueDeclare(queueName,true,false,false,arguments);
        channel.queueBind(queueName,exchangeName,routingKey);

        /**
         * 进行死信队列的声明
         */
        channel.exchangeDeclare("dlx.exchange","topic",true,false,null);
        channel.queueDeclare("dlx.queue",true,false,false,null);
        channel.queueBind("dlx.queue","dlx.exchange","#");


        //1.限流方式 第一件事情是autoAck设置为false
        channel.basicConsume(queueName,true, new MyConsumer(channel));


    }
}
