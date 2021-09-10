package web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import web.entity.Expense;
import web.entity.User;
import web.exceptionClass.DuplicateUserException;
import web.lib.Config;
import web.lib.EmailUtility;
import web.lib.Hash;
import web.lib.PageRedirectManager;
import web.lib.RandomString;
import web.lib.SessionManage;
import web.pojos.UserSession;
import web.services.UserService;

@Named
@ViewScoped
public class UserBean implements Serializable{
	
	private User user;
	private User currentUser;
	private String verificaion_code;

	public String getVerificaion_code() {
		return verificaion_code;
	}

	public void setVerificaion_code(String verificaion_code) {
		this.verificaion_code = verificaion_code;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Inject
	private UserService userService;
	
	@PostConstruct
	public void init() {
		user = new User();
	}
	
	public String loginSubmit() {
		
		if(!userService.isValidUser(user)) {
			
			setRequestAttribute(Config.LOGIN_MESSAGE, "Login fail! Please check your username or password");
			return null;
		}
		
		User resUser = userService.getUserByUserNameAndPassword(user);
		UserSession userSession = new UserSession();
		
		userSession.setId(resUser.getId());
		userSession.setAdmin(false);
		
		final int MINS_IN_DAY = 24 * 60;
		
		SessionManage.setSessionAge(MINS_IN_DAY);
		SessionManage.setSession(Config.LOGIN_SESSION, userSession);
		SessionManage.setSessionAge(MINS_IN_DAY);
		
		System.out.println("huh");
		
		return "pretty:expenseDashboard";
	}
	
	public String registerSubmit() {
		
		if(
			user.getUsername().trim().equals("") || 
			user.getPassword().trim().equals("") || 
			user.getPhoneNumber().trim().equals("") ||
			user.getEmail().trim().equals("")
		) {
			setRequestAttribute(Config.REGISTER_MESSAGE, "Please fill all infomation the register form");
			return null;
		}
		
		if(user.getUsername().length() > 15) {
			setRequestAttribute(Config.REGISTER_MESSAGE, "The maximum character of the username is 15");
			return null;
		}
		
		if(user.getPhoneNumber().length() > 11) {
			setRequestAttribute(Config.REGISTER_MESSAGE, "The maximum character of the the phone number is 11");
			return null;
		}
		
		try {
			
			user.setQuota(0);
			user.setCreated_date(new Date());
			user.setPassword(Hash.getMd5(user.getPassword()));
			userService.addUser(user);
			
		}catch(DuplicateUserException e) {
			
			setRequestAttribute(Config.REGISTER_MESSAGE, e.getMessage());
			return null;
			
		}
		
		return "pretty:login";
	}
	
	public void logOut() {
		SessionManage.invalidateSession();
		PageRedirectManager.redirectTo("/");
	}
	
	private void setRequestAttribute(String name, Object value) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.setAttribute(name, value);
    }
	
	public String sendVerificationCode() {
		
		if(userService.getUserByEmail(user.getEmail()) == null) {
			setRequestAttribute(Config.FORGOT_P_MESSAGE, "Can't find your email in any account!");
			return null;
		}
		
		final String randomString = new RandomString(6).nextString();
			
		try {
			EmailUtility.sendEmail(
					Config.EMAIL_HOST, Config.EMAIL_PORT, 
					Config.EMAIL_USERNAME, Config.EMAIL_PASSWORD, 
					user.getEmail(), "Change password", "Hello. It's look like you forgot your password\nHere is your verification code: " + randomString + "\nIt will expired in 10 minutes");
		} catch (AddressException e) {

			e.printStackTrace();
		} catch (MessagingException e) {

			e.printStackTrace();
		}
		
		SessionManage.setSessionAge(10);
		SessionManage.setSession(Config.VERIFY_SESSION, randomString);
		SessionManage.setSession(Config.SERCURITY_SESSION, user.getEmail());
		SessionManage.setSessionAge(10);
		
		return "success";
	}
	
	public String verifyCode() {
		
		if(verificaion_code == null || verificaion_code.equals("")) {
			setRequestAttribute(Config.FORGOT_P_MESSAGE, "Please type your code !");
			return null;
		}
		
		String sessionVerifyCode = null;
		String userEmail = null;
		
		try {
			sessionVerifyCode = SessionManage.getSession(Config.VERIFY_SESSION).toString();
			userEmail = SessionManage.getSession(Config.SERCURITY_SESSION).toString();
			
			if(sessionVerifyCode == null || userEmail == null)
				return "denied";
			
		}catch(NullPointerException e) {
			return "denied";
		}
		
		if(!sessionVerifyCode.equals(verificaion_code)) {
			setRequestAttribute(Config.FORGOT_P_MESSAGE, "The code does not match !");
			return null;
		}
		
		SessionManage.removeSession(Config.SERCURITY_SESSION);
		SessionManage.removeSession(Config.VERIFY_SESSION);
		SessionManage.setSession(Config.SERCURITY_SESSION, userService.getUserByEmail(userEmail).getId());
		
		return "success";
	}
	
	public String changePassword() {

		if(SessionManage.getSession(Config.SERCURITY_SESSION) == null) {
			return "denied";
		}
		
		int userId = Integer.parseInt(SessionManage.getSession(Config.SERCURITY_SESSION).toString());
		User sessionUser = userService.getUserById(userId);
		sessionUser.setPassword(user.getPassword());
		
		userService.editUser(sessionUser);
		
		SessionManage.invalidateSession();
		
		return "success";
	}
}