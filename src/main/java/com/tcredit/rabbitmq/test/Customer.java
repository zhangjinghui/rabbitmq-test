package com.tcredit.rabbitmq.test;


import com.rabbitmq.client.*;
import com.tcredit.rabbitmq.util.SettingUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Customer
{
    private final static String QUEUE_NAME = "test";
 
    public static void main(String[] args) throws IOException, TimeoutException
    {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
 
        //设置RabbitMQ地址
        factory.setHost(SettingUtil.getValue("rabbitmq.host"));
		factory.setVirtualHost(SettingUtil.getValue("rabbitmq.virtualHost"));
		factory.setUsername(SettingUtil.getValue("rabbitmq.username"));
		factory.setPassword(SettingUtil.getValue("rabbitmq.password"));
		factory.setPort(Integer.parseInt(SettingUtil.getValue("rabbitmq.port")));
 
        //创建一个新的连接
        Connection connection = factory.newConnection();
 
        //创建一个通道
        Channel channel = connection.createChannel();
 
        //声明要关注的队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Customer Waiting Received messages");
 
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                String message = new String(body, "UTF-8");
                System.out.println("Customer Received '" + message + "'");
            }
        };
        //自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
