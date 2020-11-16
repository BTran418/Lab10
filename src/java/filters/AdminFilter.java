/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

/**
 *
 * @author BTran
 */
public class AdminFilter implements Filter {

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest)request;
	HttpSession session = httpRequest.getSession();
	String email = (String)session.getAttribute("email");
        
        if (email == null){
                HttpServletResponse httpResponse = (HttpServletResponse)response;
                httpResponse.sendRedirect("login");
                return;
            }
        
        UserDB userDB = new UserDB();
	User user = userDB.get(email);
        
        if (user.getRole().getRoleId() != 1) {
	    HttpServletResponse httpResponse = (HttpServletResponse)response;
	    httpResponse.sendRedirect("notes");
	    return;
	}
        
        chain.doFilter(request, response); // execute the servlet
    }

    public void destroy() {
        
    }

    public void init(FilterConfig filterConfig) {        

    }
  
}
