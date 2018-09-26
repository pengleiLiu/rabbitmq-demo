package org.lpl.rabbitmqdemo.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by liupenglei on 2018/9/26.
 */
public class Consumer4DirectExchange {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //1 创建ConnectionFactory
        ConnectionFactory connectionFactory =  new ConnectionFactory();
        connectionFactory.setHost("112.74.184.193");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //是否支持重连，设置每3s重连一次
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //声明
        String exchangeName = "test_direct_exchange";
        String exchangeType = "direct";
        String queueName = "test_direct_queue";
        String routingKey = "test.direct1";

        //1.表示声明交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        //2.声明队列
        channel.queueDeclare(queueName,false,false,false,null);
        //3.建立一个绑定关系
        channel.queueBind(queueName,exchangeName,routingKey);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.basicConsume(queueName,true,queueingConsumer);
        
        while(true){
            //获取消息
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("收到消息："+ msg);
        }

    }
}
