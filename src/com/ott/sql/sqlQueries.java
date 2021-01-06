package com.ott.sql;

public class sqlQueries {
	
	public static final String jdbcDriver = "com.mysql.jdbc.Driver";
	
	public static final String dbConnection = "jdbc:mysql://localhost:3306/ott?characterEncoding=latin1&useConfigs=maxPerformance";
	
	
	public static final String dbUsername  = "root";
	
	public static final String dbPassWord  = "root";
	
	public static final String getLoginCredentials = "select * from userdetails where email = ? and password = ?";
	
	public static final String getMovieList = "select * from movielist ";
	
	public static final String addNewContent = "insert into movielist(id,title,description,language,genre,tags,image,views,uploadeddate) "
			+ "values (?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE title = VALUES(title),description=VALUES(description),"
			+ "language=VALUES(language),genre=VALUES(genre),tags=VALUES(tags),image=VALUES(image),views=VALUES(views), uploadeddate=VALUES(uploadeddate)";
	
	public static final String getMovieCount = "select count(*) from movielist";
	
	public static final String getUniqueGenre = "select distinct(genre) from movielist";
	
	public static final String getUniqueTags = "select distinct(tags) from movielist";
	
	public static final String getUniqueLanguage = "select distinct(language) from movielist";
	
	public static final String getfavwatchlist = "select movieid,favoriteic,watchlistic from userfavoriteswatchlist where userid = ?";
	
	public static final String setFavList = "insert into userfavoriteswatchlist(movieid,userid,favoriteic) values (?,?,'1') ON DUPLICATE KEY UPDATE favoriteic = '1'";
	
	public static final String removeFavList = "insert into userfavoriteswatchlist(movieid,userid,favoriteic) values (?,?,'0') ON DUPLICATE KEY UPDATE favoriteic = '0' ";
	
	public static final String setWatchList = "insert into userfavoriteswatchlist(movieid,userid,watchlistic) values (?,?,'1') ON DUPLICATE KEY UPDATE watchlistic = '1' ";
	
	public static final String removeWatchList = "insert into userfavoriteswatchlist(movieid,userid,watchlistic) values (?,?,'0') ON DUPLICATE KEY UPDATE watchlistic = '0' ";
	
	
}
