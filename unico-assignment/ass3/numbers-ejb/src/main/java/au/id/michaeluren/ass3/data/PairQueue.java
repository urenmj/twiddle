package au.id.michaeluren.ass3.data;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import au.id.michaeluren.ass3.util.SystemException;

public class PairQueue {
	private static final Logger logger = Logger.getLogger(PairQueue.class.getName());
	private static final long JMS_RECV_TIMEOUT = 3000; 
	
    @Resource(mappedName = "java:jboss/jms/queue/numberQueue")
    private Queue numberQueue;
 
    @Resource(mappedName = "java:/JmsXA")
    private ConnectionFactory cf;
 
    public void insert(Pair pair) throws SystemException {
    	Connection connection = null;
        try {         
            connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(numberQueue);
 
            connection.start();
 
            BytesMessage message = session.createBytesMessage();
            message.writeInt(pair.getFirst());
            message.writeInt(pair.getSecond());
            publisher.send(message);
        }
        catch (Exception e) {
        	logger.log(Level.SEVERE, e.toString());
        	throw new SystemException(e);
        }
        finally {         
            if (connection != null)   {
                try {
                    connection.close();
                }
                catch (JMSException jmse) {                    
                	logger.log(Level.SEVERE, jmse.toString());
                	throw new SystemException(jmse);
                }
 
            }
        }
    }
    
    public Pair remove() throws SystemException {
    	Connection connection = null;
        try {         
            connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(numberQueue);
            connection.start();
            BytesMessage msg = (BytesMessage)consumer.receive(JMS_RECV_TIMEOUT);
            
            // no message before timeout? probably empty queue
            if (msg == null) {
            	throw new SystemException(null);
            }
            Pair p = new Pair(msg.readInt(), msg.readInt());
            return p;
        }
        catch (Exception e) {
        	logger.log(Level.SEVERE, e.toString());
        	throw new SystemException(e);
        }
        finally {         
            if (connection != null)   {
                try {
                    connection.close();
                }
                catch (JMSException jmse) {                    
                	logger.log(Level.SEVERE, jmse.toString());
                	throw new SystemException(jmse);
                }
 
            }
        }
    }

}
