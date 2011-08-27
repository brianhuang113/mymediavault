<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<html>
<head>
<style type="text/css">
<!--
a {
	text-decoration:none;
	color: #666666;
	padding: 3px;
}
a:hover {
	background-color: #CCD6EC;
	color: #333333;
	padding: 2px;
	border: 1px solid #336699;
}
td {
	font-size:10pt;
}
-->
</style>
<title>Media Vault</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8"/>
</head>
  <body  bgcolor="#999999" leftmargin="3" topmargin="3" marginwidth="3" marginheight="3">
	<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#669900"> 
  <tr bgcolor="#99CC66"> 
    <td height="50" align="center" bgcolor="#99CC66">
      <p style="color:#333333 ;font-size:14pt; font-weight:bold; font-family:Courier New">
☆ Media Vault ☆
      </p>
    </td> 
  </tr> 
  <tr>
    <td height="25">
                <%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
%>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr align="center" bgcolor="#eeeeee">
        <td height="25" align="right">&nbsp;</td>
        <td align="center" bgcolor="#eeeeee">
          <table cellpadding="3" cellspacing="0">
            <tr align="center"> 
              <td><font face="Courier New">《 Welcome <font color="#0066CC"><%= user.getNickname() %></font> 》</font></td>
              
              <td><a href="/upload.jsp" target="main">:: File upload</a></td>
              
              <td><a href="/file_list.jsp" target="main">:: File listing</a></td>
              
              <td><a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <%
    } else {
%>
Please <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
 here.
 <%
    }
%>
    </td>
  </tr>
    <tr> 
    <td align="center" bgcolor="#FFFFFF">
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0"> 
        <tr>
          <td>
          <% if (user != null) { %>
            <iframe width="100%" height="100%" frameborder="0" src="/file_list.jsp" name="main">
            Your web browser doesn't support "iframe", please update.</iframe>
            <% } %>
          </td>
        </tr> 
      </table>
    </td> 
  </tr>
    <td height="20" align="center" bgcolor="#99CC66">
      <font style="color:#999966; font-family:Courier New">Copyright&copy; 2011 Awesome team, QUT.</font>
    </td> 
  </tr> 
</table> 
 
  </body>
</html>