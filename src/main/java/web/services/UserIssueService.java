package web.services;

import java.io.Serializable;

import javax.enterprise.inject.Default;
import javax.transaction.Transactional;

import web.entity.UserIssue;

@Default
public class UserIssueService extends ApplicationServiceManager implements Serializable{
	
	@Transactional
	public void addIssue(UserIssue issue) {
		entityManager.persist(issue);
	}
	
}