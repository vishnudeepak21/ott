package com.ott.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ott.model.RegisterModel;

public class RegisterController extends HttpServlet{

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RegisterModel rModel = new RegisterModel();
		rModel.setFirstName(request.getParameter("fname"));
		rModel.setLastName(request.getParameter("lname"));
		rModel.setPassword(request.getParameter("password"));
		rModel.setEmail(request.getParameter("email"));
		rModel.setSecurityQuestion(request.getParameter("securtiyquestion"));
		rModel.setSecurityAnswer(request.getParameter("sanswer"));
		if (rModel.getSecurityQuestion() != null && rModel.getSecurityAnswer() != null) {
			if (rModel.getFirstName().isEmpty() || rModel.getLastName().isEmpty() || rModel.getPassword().isEmpty()
					|| rModel.getEmail().isEmpty() || rModel.getSecurityQuestion().isEmpty()
					|| rModel.getSecurityAnswer().isEmpty()) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("Register.jsp");
				requestDispatcher.forward(request, response);
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("udetails", rModel);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("Login.jsp");
				requestDispatcher.forward(request, response);
			}
		}
	}
}
