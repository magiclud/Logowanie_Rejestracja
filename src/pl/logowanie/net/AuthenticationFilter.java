package pl.logowanie.net;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter{

	public void init(FilterConfig fConfig) throws ServletException {
      //  logger.info("AuthenticationFilter initialized");
    }
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
 
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
         
        String uri = req.getRequestURI();
      //  logger.info("Requested Resource::"+uri);
         
        HttpSession session = req.getSession(false);
         
        if(session == null && !(uri.endsWith("html") || uri.endsWith("Login") || uri.endsWith("Register"))){
           // logger.error("Unauthorized access request");
            res.sendRedirect("login.html");
        }else{
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
         
         
    }
 
    public void destroy() {
        //close any resources here
    }

}
