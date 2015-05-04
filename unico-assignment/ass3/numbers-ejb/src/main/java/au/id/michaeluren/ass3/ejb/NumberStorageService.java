package au.id.michaeluren.ass3.ejb;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import au.id.michaeluren.ass3.data.Pair;
import au.id.michaeluren.ass3.data.PairQueue;
import au.id.michaeluren.ass3.data.PairRepository;
import au.id.michaeluren.ass3.util.SystemException;

@Stateless
public class NumberStorageService {
	
	private static final Logger logger = Logger.getLogger(NumberStorageService.class.getName());
	
	@Inject
	PairRepository pairRepository;
	
	@Inject
	PairQueue pairQueue;
	
    @Resource(mappedName = "java:jboss/jms/queue/numberQueue")
    private Queue numberQueue;
 
    @Resource(mappedName = "java:/JmsXA")
    private ConnectionFactory cf;
 
    public void insertMessage(Pair pair) throws SystemException {
    	// all scoped in the same transaction
    	pairRepository.insert(pair);
    	pairQueue.insert(pair);
    }   
    
    public Pair removeMessageFromQueue() throws SystemException {
    	return pairQueue.remove();
    }
    
    public List<Pair> findAllFromDb() throws SystemException {
    	return pairRepository.findAllOrderedByInsertSequence();
    }
}

