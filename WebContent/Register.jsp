<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "javax.servlet.*" %>
<%@ page import = "javax.servlet.http.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
<form action="RegisterController" method="post">
<pre>
<tr>
<td>First Name            </td><td><input type="text" name="fname" value="" /></td>
</tr><br>
<tr>
<td>Last Name             </td><td><input type="text" name="lname" value="" /></td>
</tr><br>
<tr>
<td>Email                 </td><td><input type="text" name="email" value="" /></td>
</tr><br>
<tr>
<td>Password              </td><td><input type="password" name="password" value="" /></td>
</tr><br>
<tr>
<td>Confirm Password      </td><td><input type="password" name="cpass" value="" /></td>
</tr><br>
<tr>
<td>Securtiy Question     <select name="securtiyquestion"> 
						  <option value="Childhood Friend">Childhood Friend</option> 
						  <option value="Favorite Food">Favorite Food</option>
						  <option value="Favourite Subject">Favourite Subject</option>
						  </select></td>
</tr><br>
<tr>
<td>security answer       </td><td><input type="text" name="sanswer" value="" /></td>
</tr><br>
<tr>
<td><input type="submit" value="Submit" /></td>
</tr><br>
<tr>
<td colspan="2">Already registered!! <a href="Login.jsp">Login Here</a></td>
</tr>
</pre>
</form>
</body>
</html>