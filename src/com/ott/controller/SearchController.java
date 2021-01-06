package com.ott.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ott.model.MovieInfo;
import com.ott.model.SearchModel;
import com.ott.sql.sqlQueries;

@SuppressWarnings("serial")
public class SearchController extends HttpServlet {
	ArrayList<MovieInfo> dupMovieList;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		searchAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		searchAction(request, response);
	}

	public void searchAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HashMap<Integer, String> queryBuild = new HashMap<Integer, String>();
		int initialindicator = 0;
		String sqlQuery = sqlQueries.getMovieList;
		HttpSession Actsession = request.getSession(false);
		SearchModel Ssm = (SearchModel) Actsession.getAttribute("searchcriteria");
		if (Ssm == null) {
			Ssm = new SearchModel();
		} else if (request.getParameter("search") != null) {
			Ssm = new SearchModel();
			Ssm.setGenre(request.getParameter("genre"));
			Ssm.setLanguage(request.getParameter("language"));
			Ssm.setTags(request.getParameter("tags"));
			if (request.getParameter("favorites") != null) {
				Ssm.setFavorites(true);
			}
		}

		if (Ssm.getGenre() != null) {
			if (!(Ssm.getGenre().isEmpty()) && Ssm.getGenre().length() > 0) {
				queryBuild.put(++initialindicator, Ssm.getGenre());
				sqlQuery += "where genre=?";
			}
		}
		if (Ssm.getLanguage() != null) {
			if (!(Ssm.getLanguage().isEmpty()) && Ssm.getLanguage().length() > 0) {
				queryBuild.put(++initialindicator, Ssm.getLanguage());
				if (initialindicator > 1) {
					sqlQuery += " and language=?";
				} else {
					sqlQuery += "where language=?";
				}
			}
		}
		if (Ssm.getTags() != null) {
			if (!(Ssm.getTags().isEmpty()) && Ssm.getTags().length() > 0) {
				queryBuild.put(++initialindicator, Ssm.getTags());
				if (initialindicator > 1) {
					sqlQuery += " and tags=?";
				} else {
					sqlQuery += "where tags=?";
				}
			}
		}
		ArrayList<MovieInfo> movieList = new ArrayList<>();
		try {
			Class.forName(sqlQueries.jdbcDriver);
			Connection connection = DriverManager.getConnection(sqlQueries.dbConnection, sqlQueries.dbUsername,
					sqlQueries.dbPassWord);
			System.out.println(sqlQuery);
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			for (int i : queryBuild.keySet()) {
				preparedStatement.setString(i, queryBuild.get(i));
			}
			System.out.println(sqlQuery);
			ResultSet rs = preparedStatement.executeQuery();
			MovieInfo mi;
			while (rs.next()) {
				mi = new MovieInfo();
				mi.setId(rs.getString(1));
				mi.setTitle(rs.getString(2));
				mi.setDescription(rs.getString(3));
				mi.setLanguage(rs.getString(4));
				mi.setGenre(rs.getString(5));
				mi.setTags(rs.getString(6));
				mi.setView(rs.getString(8));
				Blob blob = rs.getBlob(7);
				InputStream inputStream = blob.getBinaryStream();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				int bytesRead = -1;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				byte[] imageBytes = outputStream.toByteArray();
				String base64Image = Base64.getEncoder().encodeToString(imageBytes);
				inputStream.close();
				outputStream.close();
				mi.setLogo(base64Image);
				movieList.add(mi);
			}
			rs.close();
			preparedStatement.close();
			preparedStatement = connection.prepareStatement(sqlQueries.getUniqueGenre);
			rs = preparedStatement.executeQuery();
			Set<String> hashlist = new HashSet<String>();
			while (rs.next()) {
				hashlist.add(rs.getString(1));
			}
			hashlist.add("");
			request.setAttribute("gl", hashlist);
			rs.close();
			preparedStatement.close();
			preparedStatement = connection.prepareStatement(sqlQueries.getUniqueLanguage);
			rs = preparedStatement.executeQuery();
			hashlist = new HashSet<String>();
			while (rs.next()) {
				hashlist.add(rs.getString(1));
			}
			hashlist.add("");
			request.setAttribute("ll", hashlist);
			rs.close();
			preparedStatement.close();
			preparedStatement = connection.prepareStatement(sqlQueries.getUniqueTags);
			rs = preparedStatement.executeQuery();
			hashlist = new HashSet<String>();
			while (rs.next()) {
				hashlist.add(rs.getString(1));
			}
			hashlist.add("");
			request.setAttribute("tl", hashlist);
			rs.close();
			preparedStatement.close();
			preparedStatement = connection.prepareStatement(sqlQueries.getfavwatchlist);
			preparedStatement.setString(1, Actsession.getAttribute("userid").toString());
			rs = preparedStatement.executeQuery();
			ArrayList<MovieInfo> dupMovieList = movieList;
			if (Ssm.isFavorites()) {
				movieList = new ArrayList<>();
			}
			while (rs.next()) {				
				for (MovieInfo mv : dupMovieList) {
					if (mv.getId().equalsIgnoreCase(rs.getString(1))) {
						mv.setFavorites(rs.getString(2).equalsIgnoreCase("1"));
						mv.setWatchList(rs.getString(3).equalsIgnoreCase("1"));
						if (Ssm.isFavorites()) {
							movieList.add(mv);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Actsession.setAttribute("dupMList", movieList);
		Actsession.setAttribute("searchcriteria", Ssm);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("Home.jsp");
		requestDispatcher.forward(request, response);
	}

}
