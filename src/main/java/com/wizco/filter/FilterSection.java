package com.wizco.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizco.pojo.UserPojo;

/**
* Servlet Filter implementation class FilterSection
*/
@WebFilter(urlPatterns = {"/home","/project/private/*"})
public class FilterSection implements Filter{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String context = request.getServletContext().getContextPath();
		System.out.println(context         + " teste contexto");
		try {
			
			HttpSession session = ((HttpServletRequest) request).getSession();
			UserPojo user = null;
			Map<String, String> msg = new HashMap<String, String>();

			if (session != null ) {
				user = (UserPojo) session.getAttribute("sectionuser");
			}
			/*if ( user != null && context.equals("/control-expenses") ) {
				 criar uma validação para verificar permissao ?
				((HttpServletResponse) response).sendRedirect(context+"/project/private/home.html#!/");
			} */
			if ( user == null ) {
				
				session.invalidate();// invalid session
				msg.put("msg", "Voce não está logado no sistema");
				((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);
				String json = new ObjectMapper().writeValueAsString(msg);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				
				((HttpServletResponse) response).sendRedirect(context+"/#!/login");

			}else {
				chain.doFilter(request, response);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
    * Default constructor. 
    */

	/**
	* @see Filter#destroy()
	*/
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	* @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	*/
	
	/**
	* @see Filter#init(FilterConfig)
	*/
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
