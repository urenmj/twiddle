package au.id.michaeluren.ass3.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PairRepository {
	@PersistenceContext
	EntityManager em;
	
	public List<Pair> findAllOrderedByInsertSequence() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pair> criteria = cb.createQuery(Pair.class);
        Root<Pair> pair = criteria.from(Pair.class);
        criteria.select(pair).orderBy(cb.asc(pair.get("id")));
        return em.createQuery(criteria).getResultList();        
	}

	public void insert(Pair pair) {
		pair.setId(null);
		em.persist(pair);		
	}
}
