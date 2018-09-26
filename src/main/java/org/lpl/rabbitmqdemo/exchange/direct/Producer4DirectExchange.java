package org.lpl.rabbitmqdemo.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by liupenglei on 2018/9/26.
 */
public class Producer4DirectExchange {

    public static void main(String[] args) throws IOException, TimeoutException {
        //1 创建ConnectionFactory
        ConnectionFactory connectionFactory =  new ConnectionFactory();
        connectionFactory.setHost("112.74.184.193");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3.通过一个Connection创建一个Channel
        Channel channel = connection.createChannel();

        //4.声明
        String exchangeName = "test_direct_exchange";
        String routingKey = "test.direct1";

        //5.发送
        String msg = "Hello Word RabbitMQ 4 Direct Exchange Message 111..";
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());
    }

}
