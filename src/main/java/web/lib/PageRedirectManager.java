package web.lib;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageRedirectManager {
	
	public static void redirectTo(String path) {
	    try {
	    	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getRequestContextPath() + path);
			
		} catch (IOException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	public static void forwardTo(String path) {
		
	    try {
	    	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    	HttpServletRequest req = (HttpServletRequest)ec.getRequest();
	    	HttpServletResponse res = (HttpServletResponse)ec.getResponse();
	 
	    	
	    	RequestDispatcher dispatcher = req.getRequestDispatcher(path);
			dispatcher.forward(req, res);
            
			//(ec.getRequestContextPath() + path);
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
}
