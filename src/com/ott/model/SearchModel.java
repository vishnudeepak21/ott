package com.ott.model;

public class SearchModel {
	
	private String genre = "";
	private String language = "";
	private String tags = "";
	private boolean favorites;
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public boolean isFavorites() {
		return favorites;
	}
	public void setFavorites(boolean favorites) {
		this.favorites = favorites;
	}
	
	

}
