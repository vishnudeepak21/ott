package com.ott.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ott.model.MovieInfo;
import com.ott.sql.sqlQueries;

@SuppressWarnings("serial")
public class LoginController extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		if (username.isEmpty() || password.isEmpty()) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("Login.jsp");
			requestDispatcher.forward(request, response);
		}else {
			try {
				Class.forName(sqlQueries.jdbcDriver);
				Connection connection = DriverManager.getConnection(sqlQueries.dbConnection, sqlQueries.dbUsername, sqlQueries.dbPassWord);
				PreparedStatement preparedStatement = connection.prepareStatement(sqlQueries.getLoginCredentials);
				preparedStatement.setString(1, username);
		        preparedStatement.setString(2, password);
		        System.out.println(preparedStatement);
	            ResultSet rs = preparedStatement.executeQuery();
	            if(rs.next()) {
	            	session.setAttribute("uname", rs.getString(2));
	            	session.setAttribute("userid", rs.getString(7));
	            	if(rs.getString(8).equals("admin")) {
	            		session.setAttribute("isAdmin", true);
	            	}else {
	            		session.setAttribute("isAdmin", false);
	            	} 
	            	session.setAttribute("mList", getDefaultMovieList());
	            	RequestDispatcher requestDispatcher = request.getRequestDispatcher("SearchController");
					requestDispatcher.forward(request, response);
	            }
	            else {
	            	RequestDispatcher requestDispatcher = request.getRequestDispatcher("Login.jsp");
	    			requestDispatcher.forward(request, response);
	            }
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("SearchController");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("SearchController");
				requestDispatcher.forward(request, response);
			}
		}
	}
		
	
	public ArrayList<MovieInfo> getDefaultMovieList() {
		ArrayList<MovieInfo> mList = new ArrayList<>();
		MovieInfo mInfo;
		mInfo = new MovieInfo();
		mInfo.setId("1");
		mInfo.setDescription("GOT-SEASON1");
		mInfo.setGenre("Historic");
		mInfo.setLanguage("English");
		mInfo.setTags("Drama");
		mInfo.setTitle("GOT SERIES");
		mInfo.setView("GAME OF THRONES");
		try {
			mInfo.setUploadTime(new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy").parse(new Date().toString()));
		}
		catch(Exception e) {
			System.out.println(e);
		}
		mList.add(mInfo);
		mInfo = new MovieInfo();
		mInfo.setId("2");
		mInfo.setDescription("BILLA");
		mInfo.setGenre("ACTION");
		mInfo.setLanguage("Tamil");
		mInfo.setTags("Movie");
		mInfo.setTitle("BILLA 2");
		mInfo.setView("Commercial Movie");
		try {
			mInfo.setUploadTime(new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy").parse(new Date().toString()));
		}
		catch(Exception e) {
			System.out.println(e);
		}
		mList.add(mInfo);
		mInfo = new MovieInfo();
		mInfo.setId("3");
		mInfo.setDescription("Bangalore Days");
		mInfo.setGenre("ROMANTIC");
		mInfo.setLanguage("MALAYALAM");
		mInfo.setTags("Movie");
		mInfo.setTitle("Bangalore Days");
		mInfo.setView("Dulquer Salman Super Hit Movie");
		try {
			mInfo.setUploadTime(new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy").parse(new Date().toString()));
		}
		catch(Exception e) {
			System.out.println(e);
		}
		mList.add(mInfo);
	    return mList;
	}
 
}
