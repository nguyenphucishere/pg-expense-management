package web.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import web.entity.UserIssue;
import web.lib.Config;
import web.lib.SessionManage;
import web.pojos.UserSession;
import web.services.UserIssueService;

@Named
@ViewScoped
public class UserIssueBean implements Serializable{
	
	@Inject
	private UserIssueService issueService;
	
	private UserIssue issue;
	
	@PostConstruct
	public void init() {
		issue = new UserIssue();
	}
	
	public void addIssue() {
		String msg = "Thank you!";
		
		try {
			
			UserSession userSession = (UserSession)SessionManage.getSession(Config.LOGIN_SESSION);
			
			if(userSession != null) {
				issue.setUserID(userSession.getId());
			}
			
			issueService.addIssue(issue);
			
		}catch(Exception e) {
			msg = "hMmm,... may be some error is detected by our website. Please try again!";
		}
		
		setRequestAttribute(Config.CONTACT_MESSAGE, msg);
		issue = new UserIssue();
	}

	public UserIssue getIssue() {
		return issue;
	}

	public void setIssue(UserIssue issue) {
		this.issue = issue;
	}
	
	private void setRequestAttribute(String name, Object value) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.setAttribute(name, value);
    }
	
}