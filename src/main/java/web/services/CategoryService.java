package web.services;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import web.entity.Category;

public class CategoryService extends ApplicationServiceManager implements Serializable{
	
	public List<Category> getAllCategory() {
		String queryStr = "SELECT c FROM Category c ORDER BY c.isTheEnd, c.categoryName ASC";
		TypedQuery<Category> query = 
				entityManager.createQuery(queryStr, Category.class);
		
		return query.getResultList();
	}
	
	public Category getCategoryByID(int id) {
		String queryStr = "SELECT c FROM Category c WHERE c.id = :id";
		TypedQuery<Category> query = 
				entityManager.createQuery(queryStr, Category.class);
		query.setParameter("id", id);

		return query.getSingleResult();
	}
	
}
