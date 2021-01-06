<%@page import="java.util.HashSet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ott.model.MovieInfo"%>
<%@ page import="com.ott.model.SearchModel"%>
<%@ page import= "java.util.HashSet"%>

<% ArrayList<MovieInfo> eList = (ArrayList)session.getAttribute("mList");%>
<html>
<head>
<style>
.left-col {
  width: 30%;
  min-height: 100px;
  border-right: 1px solid black;
  float: left;
  box-sizing: border-box;
}

.right-col {
  width: 70%;
  min-height: 100px;
  float: right;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body>
	<%String pathWebcontent=request.getContextPath();%> 
	<% HttpSession Actsession=request.getSession(false);
	String uname = (String) session.getAttribute("uname");
	boolean adminIc = (boolean) session.getAttribute("isAdmin");
	SearchModel sm = (SearchModel) session.getAttribute("searchcriteria");%>
	<pre><h1>Welcome <%= uname %></h1></pre>	
	<div class="btn-group">
	<% if(adminIc){%>
  		<button><a href="MetaData.jsp">Meta data</a></button>
  	<%} %>
  		<button><a href="<%= pathWebcontent+"/FavoriteController"%>">Favorites</a></button>
  		<button><a href="<%= pathWebcontent+"/WishListController"%>">WatchList</a></button>
	</div>
	<div class='page'>
	<div class='left-col'>
  	<form action="SearchController" method="post">
	<pre>
	<tr>
	<td>Genre		      <select name="genre">   
	<% for(String genre : (HashSet<String>) request.getAttribute("gl")){ 
			if(genre.equals(sm.getGenre())){%>	
						  <option value="<%= genre%>" selected><%= genre%></option> 
			<%} else {%>
						  <option value="<%= genre%>" ><%= genre%></option>
			<%} %>
	<%}%>
	</select></td>
	</tr><br>
	<tr>
	<td>Language              <select name="language">   
	<% for(String language : (HashSet<String>) request.getAttribute("ll")){ 
			if(language.equals(sm.getLanguage())){%>	
						  <option value="<%= language%>" selected><%= language%></option> 
			<%} else {%>
						  <option value="<%= language%>" ><%= language%></option>
			<%} %>
	<%}%>
	</select></td>
	</tr><br>
	<tr>
	<td>Tags		      <select name="tags">   
	<% for(String tags : (HashSet<String>) request.getAttribute("tl")){ 
			if(tags.equals(sm.getTags())){%>	
						  <option value="<%= tags%>" selected><%= tags%></option> 
			<%} else {%>
						  <option value="<%= tags%>" ><%= tags%></option>
			<%} %>
	<%}%>
	</select></td>
	</tr><br>
	<tr>
	<td>favorites            </td><td><input type="checkbox" name="favorites" value="<%= sm.isFavorites() %>" /></td>
	</tr><br>
	<tr>
			   <td><input type="submit"  name="search" value="search" /></td>
	</tr><br>
	</pre>
  	</form>
  	</div>
  	
  	<div class='right-col'>
     	<h3>Movie List</h3>
		<hr size="3" color="gray"/>
		<table style="width:100%">
		<tr>
			<th rowspan="2"></th>
			<th>Title</th>
			<th>Description</th>
			<th>Favourite List Status</th>	
		</tr>
		<tr>
			<th>Genre</th>
			<th>Language</th>
			<th>Watch List Status</th>					
		</tr>
		<tr>
    	 <td colspan="6"><hr size="4" color="gray"></td>
    	</tr>
		<%
		ArrayList<MovieInfo> movieList = (ArrayList<MovieInfo>) session.getAttribute("dupMList");
		for(int i=0;i<movieList.size();i++){
			MovieInfo movieInfo = movieList.get(i);
		%>
		<form action="FavWishController" method="post">
		<input type="hidden" name="id" value="<% out.print(movieInfo.getId()); %>"/>
    	<tr>
    		<% String path = "data:image/jpeg;base64,"+movieInfo.getLogo();%>
    		<td rowspan="2"><img src=<%= path %> width="100" height=""/></td>
    		<td align="center" name="title"><% out.print(movieInfo.getTitle()); %></td>
    		<td align="center" name="description"><% out.print(movieInfo.getDescription()); %></td>
    		<% if(!(movieInfo.isFavorites())) {%>
	  			<td align="center"><input type="submit" name="ActFavouite" value="Add to Favourite" readonly/></td>
	  			<%} else{ %>
	  			<td align="center"><input type="submit" name="removeFavouite" value="Remove from Favourite" readonly/></td>
	  		<%} %>
    	</tr>
    	<tr>
    		<td align="center" name="genre"><% out.print(movieInfo.getGenre()); %></td>
    		<td align="center" name="Language"><% out.print(movieInfo.getLanguage()); %></td>
    		<% if(!(movieInfo.isWatchList())) {%>
  			    <td align="center"><input type="submit" name="ActWatchlist" value="Add to WatchList" readonly/></td>
  				<%} else { %>
  				<td align="center"><input type="submit" name="removeWatchList" value="Remove from WatchList" readonly/></td>
  				<%}%>
    	</tr> 		
    	</form>
        <tr>
    	 <td colspan="6"><hr size="4" color="gray"/></td>
    	</tr>
		<%} %>
		</table>
  	</div>
  	
</div>
</body>
</html>