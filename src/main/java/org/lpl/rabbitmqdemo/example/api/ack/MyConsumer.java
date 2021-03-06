package org.lpl.rabbitmqdemo.example.api.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;


/**
 * Created by liupenglei on 2018/9/28.
 */
public class MyConsumer extends DefaultConsumer {

    private Channel channel;

    public MyConsumer(Channel channel){
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("-----------consume message----------");
        System.out.println("body: " + new String(body));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if((Integer)properties.getHeaders().get("num") == 0){
            //requeue是否重回队列 添加到尾部
            channel.basicNack(envelope.getDeliveryTag(),false,false);
        }else{
            channel.basicAck(envelope.getDeliveryTag(),false);
        }

    }
}
