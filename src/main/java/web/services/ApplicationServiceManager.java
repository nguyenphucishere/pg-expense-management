package web.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ApplicationServiceManager {
	@PersistenceContext(unitName = "pgExpensePersistenceUnit")
	protected EntityManager entityManager;
	 
}
