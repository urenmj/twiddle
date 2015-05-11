package au.id.michaeluren.ass3.rs;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import au.id.michaeluren.ass3.data.Pair;
import au.id.michaeluren.ass3.ejb.NumberStorageService;
import au.id.michaeluren.ass3.util.SystemException;

@Path("/rationals")
@RequestScoped
public class RationalRestService {

	private static final Logger logger = Logger
			.getLogger(RationalRestService.class.getName());

	@EJB
	private NumberStorageService storage;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Integer> list() throws SystemException {
		List<Integer> numberList = new ArrayList<Integer>();
		List<Pair> allPairs = storage.findAllFromDb();
		for (Pair p : allPairs) {
			numberList.add(p.getFirst());
			numberList.add(p.getSecond());
		}
		logger.log(Level.INFO, "Here is the list: " + numberList.toString());
		return numberList;
	}

	/**
	 * Given the restrictions imposed by the assignment's interface, these are
	 * individual form params (as compared to allowing embedding an object in
	 * the incoming message, converted from JSON or XML)
	 * 
	 * @param i1
	 * @param i2
	 * @return the same value as the HTTP status code in the response (as per
	 *         assignment spec)
	 * @throws SystemException
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String push(@FormParam("numerator") int i1,
			@FormParam("denominator") int i2) throws SystemException {
		logger.log(Level.INFO, "i1:  " + i1 + ", i2: " + i2);
		try {
			storage.insertMessage(new Pair(i1, i2));
			return Integer.toString(Response.Status.OK.getStatusCode());
		}
		catch (SystemException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e; // dealt with up the chain by exception mapper
		}
	}
}


