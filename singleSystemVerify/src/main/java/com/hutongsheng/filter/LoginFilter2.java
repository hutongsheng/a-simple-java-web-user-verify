package com.hutongsheng.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hutongsheng.conststring.ConstString;

public class LoginFilter2 implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println(" login filter2 ");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request ;
		HttpServletResponse resp = (HttpServletResponse)response ;
		Object obj = req.getSession().getAttribute("isvalid");
		if((obj instanceof Boolean )&& obj.equals(Boolean.TRUE)) {
			String uri = req.getRequestURI();
			String lastPathName = uri.substring(uri.lastIndexOf("/")+1, uri.length());
			req.getRequestDispatcher(lastPathName).forward(request, response);
		}else {
			resp.sendRedirect(ConstString.LOGIN_URI2);
		}
	}

	@Override
	public void destroy() {
		
	}

}
