package web.lib;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Config{
	
	public static final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	
	
	public static final String LOGIN_SESSION = "LOGIN_SESSION";
	public static final String VERIFY_SESSION = "VER_CODE";
	public static final String SERCURITY_SESSION = "SERCURITY_SESSION";
	
	public static final String REGISTER_MESSAGE = "REGISTER_MESSAGE";
	public static final String LOGIN_MESSAGE = "LOGIN_MESSAGE";
	public static final String FORGOT_P_MESSAGE = "FORGOT_P_MESSAGE";
	public static final String CONTACT_MESSAGE = "CONTACT_MESSAGE";
	
	public static final String EMAIL_HOST = context.getInitParameter("host");
	public static final String EMAIL_PORT = context.getInitParameter("port");
	public static final String EMAIL_USERNAME = context.getInitParameter("user");
	public static final String EMAIL_PASSWORD = context.getInitParameter("pass");
	
}