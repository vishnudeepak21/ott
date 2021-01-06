<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Meta Data</title>
</head>
<body>
<%String pathWebcontent=request.getContextPath();%> 
<div class="btn-group">
  		<button><a href="<%= pathWebcontent+"/SearchController"%>">back</a></button>
</div>
<form action="MetaDataController" method="post" enctype="multipart/form-data">
<pre>
<tr>
<td>Title                 </td><td><input type="text" name="Title" value="" /></td>
</tr><br>
<tr>
<td>Description           </td><td><input type="text" name="Description" value="" /></td>
</tr><br>
<tr>
<td>Language              </td><td><input type="text" name="Language" value="" /></td>
</tr><br>
<tr>
<td>Genre                 </td><td><input type="text" name="Genre" value="" /></td>
</tr><br>
<tr>
<td>Tags                  </td><td><input type="text" name="Tags" value="" /></td>
</tr><br>
<tr>
<td>Select image         </td><td><input type="file" id="img" name="img" accept="image/*"></td>
</tr><br>
<tr>
<td>View                 </td><td><input type="text" name="View" value="" /></td>
</tr><br>
<tr>
<td><input type="submit" value="Submit" /></td>
</tr><br>
</pre>
</form>
</body>
</html>