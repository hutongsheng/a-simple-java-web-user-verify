package com.hutongsheng.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hutongsheng.conststring.ConstString;
import com.hutongsheng.serviceI.UserServiceI;
import com.hutongsheng.serviceImpl.UserServiceImpl;

public class LoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println(" init filter ");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println(this+"," +Thread.currentThread().getName());
		HttpServletRequest req = (HttpServletRequest)request ;
		HttpServletResponse resp = (HttpServletResponse)response ;
		
		UserServiceI userService = new UserServiceImpl();
		String username = null ;
		String password = null ;
		String uri = req.getRequestURI();
		String lastPathName = uri.substring(uri.lastIndexOf("/")+1, uri.length());
		Cookie[] cookies = req.getCookies();
		if(cookies!=null) {
			for(int i = 0 ; i < cookies.length;i++) {
				String key = (cookies[i]).getName();
				String value = (cookies[i]).getValue();
				if(key.equals(ConstString.USERNAME))
					username = value ;
				if(key.equals(ConstString.PASSWORD))
					password = value ;
			}
			request.setAttribute(ConstString.USERNAME, username);
			request.setAttribute(ConstString.PASSWORD, password);
			boolean isvalid = userService.checkUserIsValid(username, password);
			if(isvalid) {
				req.getRequestDispatcher(lastPathName).forward(request, response);
			}else {
				request.getRequestDispatcher(ConstString.LOGINSERVLET).forward(request, response);
			}
		}else {
			System.out.println("============"+lastPathName);
			if(!lastPathName.equals("login"))
				resp.sendRedirect(ConstString.LOGIN_URI);
			else {
//				String scheme = req.getScheme() ;
//				String servername = req.getServerName() ;
//				req.getRequestDispatcher("/"+ConstString.INDEX_URI).forward(request, response);
				doFilter(request, response, chain);
			}
		}
	} 

	@Override
	public void destroy() {
		
	}

}
