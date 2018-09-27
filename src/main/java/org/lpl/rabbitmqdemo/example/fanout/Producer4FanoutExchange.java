package org.lpl.rabbitmqdemo.example.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by lpl on 2018/9/26.
 */
public class Producer4FanoutExchange {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.74.184.193");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_fanout_exchange";
        String rountingKey = "";
        for(int i = 0; i < 10; i++){
            String msg = "Hello Word RabbitMQ 4 Fanout Exchange Message....";
            channel.basicPublish(exchangeName,rountingKey,null,msg.getBytes());
        }

        channel.close();
        connection.close();
    }
}
