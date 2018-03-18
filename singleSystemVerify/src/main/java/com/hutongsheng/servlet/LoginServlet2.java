package com.hutongsheng.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hutongsheng.conststring.ConstString;
import com.hutongsheng.serviceI.UserServiceI;
import com.hutongsheng.serviceImpl.UserServiceImpl;

/**
 * Servlet implementation class LoginServlet2
 */
@WebServlet("/LoginServlet2")
public class LoginServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet2() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserServiceI userService = new UserServiceImpl() ;
		boolean isvalid = userService.checkUserIsValid(username, password);
		
		if(isvalid) {
			
			request.getSession().setAttribute("isvalid", true );
			request.getSession().setMaxInactiveInterval(300);// 5min 
			
			request.getRequestDispatcher(ConstString.INDEX_URI).forward(request, response);
		}
		
	}

}
