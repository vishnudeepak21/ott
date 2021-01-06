package com.ott.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ott.model.MovieInfo;

public class FavoriteController extends HttpServlet{		
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession Actsession = request.getSession(false);
		ArrayList<MovieInfo> movieList = (ArrayList<MovieInfo>) Actsession.getAttribute("mList");
		Actsession.setAttribute("dupMList", filterFavorites(movieList));
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("Favorites.jsp");
		requestDispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession Actsession = request.getSession(false);
		ArrayList<MovieInfo> movieList = (ArrayList<MovieInfo>) Actsession.getAttribute("mList");
		ArrayList<MovieInfo> dupMovieList = new ArrayList<>();
		String id = request.getParameter("id");
		if (id != null) {
			if (!(id.isEmpty())) {
				if (request.getParameter("ActRemoveFavouite") != null) {
					for (MovieInfo movieInfo : movieList) {
						if (movieInfo.getId().equals(id)) {
							movieInfo.setFavorites(false);
						}
					}
				}
			}
			Actsession.setAttribute("dupMList", filterFavorites(movieList));
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("Favorites.jsp");
			requestDispatcher.forward(request, response);
		}
	}
	
	private ArrayList<MovieInfo> filterFavorites(ArrayList<MovieInfo> minfo) {
		ArrayList<MovieInfo> dupMovieList = new ArrayList<>();
		for (MovieInfo movieInfo : minfo) {
			if (movieInfo.isFavorites()) {
				dupMovieList.add(movieInfo);
			}
		}
		return dupMovieList;
	}

}
