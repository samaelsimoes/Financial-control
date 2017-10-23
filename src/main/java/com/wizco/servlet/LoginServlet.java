package com.wizco.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizco.pojo.LoginPojo;
import com.wizco.pojo.UserPojo;
import com.wizco.service.LoginService;

public class LoginServlet extends HttpServlet{
	
	@Inject
	private LoginService loginService;
	
	@Inject
	private LoginPojo loginpojo;
	
	private static final long serialVersionUID=1L;
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = request.getContextPath();
				
		try {
			
			if( !request.equals("") ) {		
				
				loginpojo.setLogin(request.getParameter("login"));
				loginpojo.setPassword(request.getParameter("password"));
				UserPojo usuario = loginService.searchLogin(loginpojo);		
				
				Map<String, String> msg = new HashMap<String, String>();
				HttpSession sectionUser = request.getSession();
												
				if( usuario != null ) {	
					
					sectionUser.setAttribute("sectionuser", usuario);//setAtribute I'm putting my UserPojo user object in session

					msg.put("msg", " Login realizado com sucesso ! ");
					msg.put("typeUser", usuario.getTypeUser());

					response.setStatus(HttpServletResponse.SC_OK);

					//SC_OK Status code (200) indicating the request succeeded normally.
				}else {
				
					sectionUser.invalidate();// invalid session
					msg.put("msg", "Login invalido");
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					//Status code (403) indicating the server understood the request but refused to fulfill it.
				}
				
				sectionUser.setMaxInactiveInterval(600);
				String json = new ObjectMapper().writeValueAsString(msg);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				
			}
		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		process(request, response);
	}
}
