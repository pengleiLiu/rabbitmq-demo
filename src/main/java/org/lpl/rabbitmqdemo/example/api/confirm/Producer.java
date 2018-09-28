package org.lpl.rabbitmqdemo.example.api.confirm;

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

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";

        String msgBody = "Hello RabbitMq Send Confirm message";

        channel.basicPublish(exchangeName,routingKey,null,msgBody.getBytes());

        //6.添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            //deliveryTag 消息唯一的标签,根据他进行确认
            //
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("------ack------");
            }

            @Override
            public void handleNack(long deliverTag, boolean multiple) throws IOException {
                System.err.println("--------no ack--------");
            }
        });

        //channel.close();
        //connection.close();
    }
}
