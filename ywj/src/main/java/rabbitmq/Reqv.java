package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;

public class Reqv {
	private final static String QUEUE_NAME = Send.QUEUE_NAME;
	private static final String EXCHANGE_NAME = "logs";
	
	static ConnectionFactory factory = new ConnectionFactory();
	
	static{
		factory.setHost("180.76.175.24");
	}

	public static void main(String[] argv) throws Exception {
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		if(QUEUE_NAME.equals(QueueType.hello_multi.toString())){
        	multi(channel, QueueType.hello_multi.toString());
        }else if(QUEUE_NAME.equals(QueueType.hello_boardcast.toString())){
        	boardcast(channel, QueueType.hello_boardcast.toString());
        }else if(QUEUE_NAME.equals(QueueType.hello_routing.toString())){
        	boardcast(channel, QueueType.hello_routing.toString());
        }else if(QUEUE_NAME.equals("hello_multi")){
        	
        }else{
        	single(channel,QueueType.hello.name());
        }
	}
	
	public static void single(Channel channel,String queuename) throws Exception{
		channel.queueDeclare(queuename, false, false, false, null);
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queuename, true, consumer);
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
		}
	}
	public static void multi(Channel channel,String queuename) throws Exception{
		channel.queueDeclare(queuename, true, false, false, null);
	    channel.basicQos(2);
	    QueueingConsumer consumer = new QueueingConsumer(channel);
	    channel.basicConsume(queuename, false, consumer);
	    while (true) {
	      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	      String message = new String(delivery.getBody());
	      System.out.println(" [x] Received '" + message + "'");
	      //doWork(message);
	      System.out.println(" [x] Done");
	      channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	    }     
    }
    public static void boardcast(Channel channel,String queuename) throws Exception{
    	channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        while (true) {
          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
          String message = new String(delivery.getBody());
          System.out.println(" [x] Received '" + message + "'");   
        }
    }
}
