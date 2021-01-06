package com.ott.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ott.sql.sqlQueries;

@SuppressWarnings("serial")
public class FavWishController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession Actsession = request.getSession(false);
		String id = request.getParameter("id");
		try {
			Class.forName(sqlQueries.jdbcDriver);
			Connection connection = DriverManager.getConnection(sqlQueries.dbConnection, sqlQueries.dbUsername,
					sqlQueries.dbPassWord);
			String sqlquery = request.getParameter("ActFavouite") != null ? sqlQueries.setFavList
					: request.getParameter("removeFavouite") != null ? sqlQueries.removeFavList
							: request.getParameter("ActWatchlist") != null ? sqlQueries.setWatchList
									: request.getParameter("removeWatchList") != null ? sqlQueries.removeWatchList : "";
			if (id != null) {
				if (!(id.isEmpty())) {
						PreparedStatement preparedStatement = connection.prepareStatement(sqlquery);
						preparedStatement.setString(1, id);
						preparedStatement.setString(2, Actsession.getAttribute("userid").toString());
						preparedStatement.executeUpdate();
				}
			}
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("SearchController");
			requestDispatcher.forward(request, response);
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
