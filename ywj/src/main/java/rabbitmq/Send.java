package rabbitmq;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Send {
	
	
	public final static String QUEUE_NAME = QueueType.hello_boardcast.toString();  
	private static final String EXCHANGE_NAME = "logs";
	
	static ConnectionFactory factory = new ConnectionFactory();  
	
	static{
		factory.setHost("180.76.175.24");
		factory.setUsername("guest");
		factory.setPassword("guest");
	}
	
    public static void main(String[] args) throws Exception {  
    	System.out.println(QueueType.hello.toString());
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
        channel.close();  
        connection.close();  
    }
    
    public static void single(Channel channel,String queuename) throws Exception{
    	channel.queueDeclare(queuename, false, false, false, null);
    	String message=getMessage();
    	channel.basicPublish("", queuename, null, message.getBytes());
    	System.out.println(" [x] Sent '" + message + "'");  
    }
    public static void multi(Channel channel,String queuename) throws Exception{
    	channel.queueDeclare(queuename, true, false, false, null);
        String message = getMessage();
        channel.basicPublish("", queuename,MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        System.out.println(" [x] Sent '" + message + "'"); 
    }
    public static void boardcast(Channel channel,String queuename) throws Exception{
    	channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    	String message = getMessage();
    	channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
    	System.out.println(" [x] Sent '" + message + "'"); 
    }
    public static void routing(Channel channel,String queuename) throws Exception{
    	channel.exchangeDeclare("direct_logs", "direct");
    	String message = getMessage();
    	channel.basicPublish("direct_logs", "routing", null, message.getBytes());
    	System.out.println(" [x] Sent '" + message + "'"); 
    }
    
    public static String getMessage(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return "Hello World!"+sdf.format(new Date());
    }
}
