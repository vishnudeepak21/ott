package com.ott.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.ott.sql.sqlQueries;
@SuppressWarnings("serial")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class MetaDataController extends HttpServlet{

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int maxCount = 0;
		if (request.getParameter("Description").isEmpty() || request.getParameter("Genre").isEmpty()
				|| request.getParameter("Language").isEmpty() || request.getParameter("Tags").isEmpty()
				|| request.getParameter("Title").isEmpty() || request.getParameter("View").isEmpty()) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("Meta.jsp");
			requestDispatcher.forward(request, response);
		} else {
			try {
				InputStream inputStream = null;
				Class.forName(sqlQueries.jdbcDriver);
				Connection connection = DriverManager.getConnection(sqlQueries.dbConnection, sqlQueries.dbUsername, sqlQueries.dbPassWord);
				PreparedStatement preparedStatement = connection.prepareStatement(sqlQueries.getMovieCount);
		        System.out.println(preparedStatement);
	            ResultSet rs = preparedStatement.executeQuery();
	            if(rs.next()) {
	            	maxCount = Integer.parseInt(rs.getString(1));
	            	
	            }
	            rs.close();
	            preparedStatement = connection.prepareStatement(sqlQueries.addNewContent);
	            preparedStatement.setString(1, String.valueOf(maxCount+1));
		        preparedStatement.setString(2, request.getParameter("Title"));
		        preparedStatement.setString(3, request.getParameter("Description"));
		        preparedStatement.setString(4, request.getParameter("Language"));
		        preparedStatement.setString(5, request.getParameter("Genre"));
		        preparedStatement.setString(6, request.getParameter("Tags"));
		        preparedStatement.setString(8, request.getParameter("View"));
		        preparedStatement.setString(9, new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy").parse(new Date().toString()).toString());
		        Part filePart = request.getPart("img");
		        if (filePart != null) {
		        	inputStream = filePart.getInputStream();
		        }
		        if (inputStream != null) {
		        	preparedStatement.setBlob(7, inputStream);
	            }
	            System.out.println(preparedStatement);
	            int row = preparedStatement.executeUpdate();
	            if (row > 0) {
	                System.out.println("updated");
	            }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("SearchController");
		requestDispatcher.forward(request, response);
	}
}		
