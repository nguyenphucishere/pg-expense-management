package web.services;

import java.io.Serializable;
import java.util.List;
import java.util.Locale.Category;

import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import web.entity.Expense;
import web.lib.Config;
import web.lib.SessionManage;
import web.pojos.UserSession;

@Default
public class ExpenseService extends ApplicationServiceManager implements Serializable{
	
	private final String whereClauseWithUserID = " e.user.id = " + ((UserSession)SessionManage.getSession(Config.LOGIN_SESSION)).getId();
	
	
	public List<Expense> getAllExpenses() {
		String queryStr = "SELECT e FROM Expense e WHERE" + whereClauseWithUserID;
		TypedQuery<Expense> query = 
				entityManager.createQuery(queryStr, Expense.class);

		return query.getResultList();
	}
	
	
	public List<Expense> getAllExpensesInMonth() {
		String queryStr = "SELECT e FROM Expense e "
				+ "WHERE FUNC('MONTH', e.created_date) = FUNC('MONTH', CURRENT_DATE) AND" + whereClauseWithUserID
				+ " AND FUNC('YEAR', e.created_date) = FUNC('YEAR', CURRENT_DATE)";

		TypedQuery<Expense> query = 
				entityManager.createQuery(queryStr, Expense.class);

		return query.getResultList();
	}
	
	public Expense getExpenseById(int id) {
		String queryStr = "SELECT e FROM Expense e WHERE e.id = :id AND" + whereClauseWithUserID;
		TypedQuery<Expense> query = 
				entityManager.createQuery(queryStr, Expense.class);
		query.setParameter("id", id);

		return query.getSingleResult();
	}
	
	@Transactional
	public void insertExpense(Expense expense) {
		entityManager.persist(expense);
	}
	
	@Transactional
	public void deleteExpense(Expense expense) {
		Expense deletingEx = entityManager.find(Expense.class, expense.getId());
		entityManager.remove(deletingEx);
	}
	
	@Transactional
	public void editExpense(Expense expense) {
		Expense editingEx = entityManager.find(Expense.class, expense.getId());
		
		editingEx.setValue(expense.getValue());
		editingEx.setCreated_date(expense.getCreated_date());
		editingEx.setNote(expense.getNote());
		editingEx.setCategory(expense.getCategory());
		editingEx.setUser(expense.getUser());
		
		entityManager.persist(editingEx);
	}
	
	public List<Object[]> getSumOfExpenseMoneyEachNameInMonth(){
		
		String queryStr = "SELECT e.category.categoryName, FUNC('SUM' , e.value) FROM Expense e "
				+ "WHERE FUNC('YEAR', e.created_date) = FUNC('YEAR', CURRENT_DATE) AND FUNC('MONTH', e.created_date) = FUNC('MONTH', CURRENT_DATE) AND" + whereClauseWithUserID
				+ " GROUP BY e.category.id ORDER BY e.category.isTheEnd";
		TypedQuery<Object[]> query = 
				entityManager.createQuery(queryStr, Object[].class);
		
		return query.getResultList();
	}

	public List<Object[]> getSpendMoneyEachMonthInYear(){
		String queryStr = "SELECT FUNC('MONTH', e.created_date), FUNC('SUM', e.value) FROM Expense e "
				+ "WHERE FUNC('YEAR', e.created_date) = FUNC('YEAR', CURRENT_DATE) AND" + whereClauseWithUserID
				+ " GROUP BY FUNC('MONTH', e.created_date) ORDER BY FUNC('MONTH', e.created_date)";
		TypedQuery<Object[]> query = 
				entityManager.createQuery(queryStr, Object[].class);
		
		return query.getResultList();
	}
	
	public List<Object[]> getSumOfExpenseMoneyInEachCategory(){
		String queryStr = "SELECT e.category.categoryName, FUNC('SUM' , e.value) FROM Expense e "
				+ "WHERE " + whereClauseWithUserID
				+ " GROUP BY e.category.id ORDER BY e.category.isTheEnd";
		TypedQuery<Object[]> query = 
				entityManager.createQuery(queryStr, Object[].class);
		
		return query.getResultList();
	}
	
	public double getTotalMoneyInMonth() {
		String queryStr = "SELECT FUNC('SUM', e.value) FROM Expense e "
				+ "WHERE FUNC('MONTH', e.created_date) = FUNC('MONTH', CURRENT_DATE) AND FUNC('YEAR', e.created_date) = FUNC('YEAR', CURRENT_DATE) AND" + whereClauseWithUserID
				+ " GROUP BY FUNC('MONTH', e.created_date)";
		TypedQuery<Double> query = 
				entityManager.createQuery(queryStr, Double.class);
		
		try {
			return query.getSingleResult();
		}catch(Exception e) {
			return 0F;
		}
	}
	
}
