package com.hutongsheng.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hutongsheng.conststring.ConstString;
import com.hutongsheng.serviceI.UserServiceI;
import com.hutongsheng.serviceImpl.UserServiceImpl;

public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println(" init servlet ");
		super.init(config);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(this+"," +Thread.currentThread().getName());
		String username = req.getParameter(ConstString.USERNAME);
		String password = req.getParameter(ConstString.PASSWORD);
		username = (username ==null ?( (String)req.getAttribute(ConstString.USERNAME)):username);
		password = password ==null ? ( (String)req.getAttribute(ConstString.PASSWORD)):password;
		System.out.println(username +","+password);
		UserServiceI userService = new UserServiceImpl();
		boolean isValid = userService.checkUserIsValid(username, password);
		if(isValid) {
			req.getSession().setAttribute("ceshi", "ceshi");
//			req.getSession().setAttribute(ConstString.USERNAME, username);
//			req.getSession().setAttribute(ConstString.PASSWORD, password);
			Cookie cookieOfUsername = new Cookie(ConstString.USERNAME, username);
			Cookie cookieOfPassword = new Cookie(ConstString.PASSWORD, password);
			cookieOfUsername.setMaxAge(120);
			cookieOfPassword.setMaxAge(120);
			cookieOfUsername.setPath("/");
			cookieOfPassword.setPath("/");
			System.out.println(cookieOfPassword.getPath());
			System.out.println(cookieOfUsername.getMaxAge());
			resp.addCookie(cookieOfUsername);
			resp.addCookie(cookieOfPassword);
//			Cookie cookie = new Cookie("JSESESSIONID", req.getSession().getId());
//			resp.addCookie(cookie);
			String referrer = req.getHeader("Referer");
//			if(!referrer.contains("login")) {
//				req.
//				System.out.println(referrer);
//				req.getRequestDispatcher(referrer).forward(req, resp);
//			}
			req.getRequestDispatcher("../"+ConstString.INDEX_URI).forward(req, resp);
		}else {
			System.out.println(req.getContextPath());
			resp.sendRedirect(req.getContextPath()+"/"+ConstString.LOGIN_URI);
		}
	}
	
}
