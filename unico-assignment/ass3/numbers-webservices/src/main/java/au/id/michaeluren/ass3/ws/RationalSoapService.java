package au.id.michaeluren.ass3.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import au.id.michaeluren.ass3.data.Pair;
import au.id.michaeluren.ass3.ejb.NumberStorageService;
import au.id.michaeluren.ass3.util.Operation;
import au.id.michaeluren.ass3.util.SystemException;

@WebService(targetNamespace="http://www.camelcase.com.au/", serviceName="RationalSoapService")
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class RationalSoapService {

	private static final Logger logger = Logger
			.getLogger(RationalSoapService.class.getName());

	@EJB
	private NumberStorageService storage;

	@WebMethod
	@WebResult(name="result")
	public int gcd() throws SystemException {
		try {
			Pair pair = storage.removeMessageFromQueue();
			int gcdValue = gcd(pair);
			logger.log(Level.INFO,
					"gcd(" + pair.getFirst() + ", " + pair.getSecond() + ") = "
							+ gcdValue);
			return gcdValue;
		} 
		catch (SystemException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e; // dealt with up the chain by exception mapper
		}
	}

	@WebMethod
	@WebResult(name="result")
	public List<Integer> gcdList() throws SystemException {
		List<Integer> gcdList = new ArrayList<Integer>();
		List<Pair> allPairs = storage.findAllFromDb();
		gcdList = allPairs.stream().map(RationalSoapService::gcd).collect(Collectors.toList());
		return gcdList;
	}

	@WebMethod
	@WebResult(name="result")
	public int gcdSum() throws SystemException {
		return gcdList().stream().reduce(0, Integer::sum);
	}

	
	public static int gcd(Pair p) {
		return Operation.gcd(p.getFirst(), p.getSecond());
	}
}
