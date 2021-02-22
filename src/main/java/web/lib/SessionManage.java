package web.lib;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class SessionManage{
	
	public static Object getSession(String name) {
        FacesContext context = FacesContext.getCurrentInstance();

        return context.getExternalContext().getSessionMap().get(name);
    }

    public static void setSession(String name, Object value) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put(name, value);
    }
	
    
    public static String getParameter(String name) {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getExternalContext().getRequestParameterMap().get(name);
    }

    public static void invalidateSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
    }
    
    public static void removeSession(String name) {
    	FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove(name);
    }
    
    public static void setSessionAge(int min) {
    	FacesContext context = FacesContext.getCurrentInstance();
    	
    	context.getExternalContext().setSessionMaxInactiveInterval(min * 60);
    }

}