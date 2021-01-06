<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ott.model.MovieInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WishList List</title>
</head>
<body>
<%String pathWebcontent=request.getContextPath();%> 
<div class="btn-group">
  		<button><a href="<%= pathWebcontent+"/SearchController"%>">back</a></button>
</div>
<h3>Movie List</h3>
		<hr size="3" color="gray"/>
		<table style="width:100%">
		<tr>
			<th rowspan="2"></th>
			<th>Title</th>
			<th>Description</th>
		</tr>
		<tr>
			<th>Genre</th>
			<th>Language</th>
			<th>WishList Status</th>			
		</tr>
		<tr>
    	 <td colspan="6"><hr size="4" color="gray"></td>
    	</tr>
		<%
		ArrayList<MovieInfo> movieList = (ArrayList<MovieInfo>) session.getAttribute("dupMList");
		for(int i=0;i<movieList.size();i++){
			MovieInfo movieInfo = movieList.get(i);
		%>
		<form action="WishListController" method="post">
		<input type="hidden" name="id" value="<% out.print(movieInfo.getId()); %>"/>
    	<tr>
    		<td rowspan="2"><img src="images/billa.jpg" alt="dft img" width="100" height=""/></td>
    		<td align="center" name="title"><% out.print(movieInfo.getTitle()); %></td>
    		<td align="center" name="description"><% out.print(movieInfo.getDescription()); %></td>
  			<td align="center"><input type="submit" name="removeWatchList" value="removeWatchList" readonly/></td>	
    	</tr>
    	<tr>
    		<td align="center" name="genre"><% out.print(movieInfo.getGenre()); %></td>
    		<td align="center" name="Language"><% out.print(movieInfo.getLanguage()); %></td>
    	</tr> 		
    	</form>
        <tr>
    	 <td colspan="6"><hr size="4" color="gray"/></td>
    	</tr>
		<%} %>
		</table>

</body>
</html>