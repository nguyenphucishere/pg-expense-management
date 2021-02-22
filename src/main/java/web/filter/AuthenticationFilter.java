package web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.lib.Config;
import web.pojos.UserSession;

@WebFilter(urlPatterns = {
		"/expense/*",
		"/expense/"
})
public class AuthenticationFilter extends HttpFilter{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        UserSession session = (UserSession)req.getSession().getAttribute(Config.LOGIN_SESSION);
        
        if (session == null) {
            req.setAttribute("LOGIN_MESSAGE", "You need to login to access that page");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/login");
            dispatcher.forward(req, res);
        } else {
            chain.doFilter(req, res);
        }
        
    }
}
