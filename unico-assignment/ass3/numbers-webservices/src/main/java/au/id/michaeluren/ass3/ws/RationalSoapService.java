package au.id.michaeluren.ass3.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import au.id.michaeluren.ass3.data.Pair;
import au.id.michaeluren.ass3.ejb.NumberStorageService;
import au.id.michaeluren.ass3.util.GcdFunction;
import au.id.michaeluren.ass3.util.SumFunction;
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
			int gcd = new GcdFunction().execute(pair.getFirst(), pair.getSecond());
			logger.log(Level.INFO,
					"gcd(" + pair.getFirst() + ", " + pair.getSecond() + ") = "
							+ gcd);
			return gcd;
		} 
		catch (SystemException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e; // dealt with up the chain by exception mapper
		}
	}

	@WebMethod
	@WebResult(name="result")
	public List<Integer> gcdList() throws SystemException {
		int gcd;
		List<Integer> gcdList = new ArrayList<Integer>();
		List<Pair> allPairs = storage.findAllFromDb();
		for (Pair pair : allPairs) {
			// if we were ensured Java 8, then we could use a function ((int x, int y) => int) to apply to all the pairs instead
			gcd = new GcdFunction().execute(pair.getFirst(), pair.getSecond()); 
			logger.log(Level.INFO,
				"gcd(" + pair.getFirst() + ", " + pair.getSecond() + ") = "
						+ gcd);
			gcdList.add(gcd);
		}
		return gcdList;
	}

	@WebMethod
	@WebResult(name="result")
	public int gcdSum() throws SystemException {
		return new SumFunction().execute(gcdList());
	}

}
