package org.lpl.rabbitmqdemo.example.api.limit;

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
        System.out.println("consumerTag: " + consumerTag);
        System.out.println("envelope: " + envelope);
        System.out.println("properties: " + properties);
        System.out.println("body: " + new String(body));

        //主动回复broker 应答ack
        channel.basicAck(envelope.getDeliveryTag(),false);

    }
}
