package web.services;

import java.io.Serializable;

import javax.ejb.DuplicateKeyException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import web.entity.Category;
import web.entity.Expense;
import web.entity.User;
import web.exceptionClass.DuplicateUserException;
import web.lib.Config;
import web.lib.Hash;
import web.lib.SessionManage;
import web.pojos.UserSession;


public class UserService extends ApplicationServiceManager implements Serializable{

	public User getUserById(int id) {
		String queryStr = "SELECT u FROM User u WHERE u.id = :id";
		TypedQuery<User> query = 
				entityManager.createQuery(queryStr, User.class);
		query.setParameter("id", id);

		return query.getSingleResult();
	}
	
	public User getUserByUserNameAndPassword(User user) {
		String queryStr = "SELECT u FROM User u WHERE u.username = :username AND u.password = FUNC('MD5', :password)";
		TypedQuery<User> query = 
				entityManager.createQuery(queryStr, User.class);
		
		query.setParameter("username", user.getUsername());
		query.setParameter("password", user.getPassword());
		
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());

//		String queryStr = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password";
//		TypedQuery<User> query = 
//				entityManager.createQuery(queryStr, User.class);
//		
//		query.setParameter("username", user.getUsername());
//		query.setParameter("password", Hash.getMd5(user.getPassword()));
		
		try {
			return query.getSingleResult();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			
			return null;
		}
	}
	
	public boolean isValidUser(User user) {
		
//		String queryStr = "SELECT u FROM User u WHERE u.username = :username";
//		TypedQuery<User> query = 
//				entityManager.createQuery(queryStr, User.class);
//		
//		query.setParameter("username", user.getUsername());
//		
//		try {
//			
//			User fuser = query.getSingleResult();
//			System.out.println(fuser.getPassword());
//			System.out.println(Hash.getMd5(user.getPassword()));
//			return Hash.getMd5(user.getPassword()).equals(fuser.getPassword());
//			
//		}catch(Exception e) {
//			System.out.println(e.getMessage());
//			System.out.println("Hello ku");
//			return false;
//		}
		
		return getUserByUserNameAndPassword(user) != null;
	}
	
	@Transactional
	public void updateUserQuota(User user) {
		User editingUser = entityManager.find(User.class, user.getId());
		
		editingUser.setQuota(user.getQuota());
		
		entityManager.persist(editingUser);
	}
	
	@Transactional
	public void addUser(User user) throws DuplicateUserException {
		
		if(getUserByUsername(user.getUsername()) != null) {
			throw new DuplicateUserException("Your username is used: " + user.getUsername());
		}
		if(getUserByEmail(user.getEmail()) != null) {
			throw new DuplicateUserException("Your email is used: " + user.getEmail());
		}
		if(getUserByPhoneNumber(user.getPhoneNumber()) != null) {
			throw new DuplicateUserException("Your phone number is used: " + user.getPhoneNumber());
		}
		
		entityManager.persist(user);
	}
	
	public User getUserByUsername(String username) {
		String queryStr = "SELECT u FROM User u WHERE u.username = :username";
		TypedQuery<User> query = 
				entityManager.createQuery(queryStr, User.class);
		query.setParameter("username", username);
		
		try {
			return query.getSingleResult();
		}catch(Exception e) {
			return null;
		}
	}
	
	public User getUserByEmail(String email) {
		String queryStr = "SELECT u FROM User u WHERE u.email = :email";
		TypedQuery<User> query = 
				entityManager.createQuery(queryStr, User.class);
		query.setParameter("email", email);
		
		try {
			return query.getSingleResult();
		}catch(Exception e) {
			return null;
		}
	}
	
	public User getUserByPhoneNumber(String phoneNumber) {

		String queryStr = "SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber";
		TypedQuery<User> query = 
				entityManager.createQuery(queryStr, User.class);
		query.setParameter("phoneNumber", phoneNumber);
		
		try {
			return query.getSingleResult();
		}catch(Exception e) {
			return null;
		}

	}
	
	@Transactional
	public void editUser(User user) {
		User editingUser = entityManager.find(User.class, user.getId());
		
		editingUser.setEmail(user.getEmail());
		editingUser.setPassword(Hash.getMd5(user.getPassword()));
		editingUser.setPhoneNumber(user.getPhoneNumber());
		editingUser.setQuota(user.getQuota());
		editingUser.setUsername(user.getUsername());
		
		entityManager.persist(editingUser);
	}
	
}
