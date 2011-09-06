<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>

<html>
<head>
<style type="text/css">
<!--
td {
	font-size: 10pt;
}
-->
</style>
<title>Media Vault</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<link href="style.css" rel="Stylesheet" type="text/css" />
<script type="text/javascript">
	//Copyright 2006-2007 javascript-array.com

	var timeout = 500;
	var closetimer = 0;
	var ddmenuitem = 0;

	// open hidden layer
	function mopen(id) {
		// cancel close timer
		mcancelclosetime();

		// close old layer
		if (ddmenuitem)
			ddmenuitem.style.visibility = 'hidden';

		// get new layer and show it
		ddmenuitem = document.getElementById(id);
		ddmenuitem.style.visibility = 'visible';

	}
	// close showed layer
	function mclose() {
		if (ddmenuitem)
			ddmenuitem.style.visibility = 'hidden';
	}

	// go close timer
	function mclosetime() {
		closetimer = window.setTimeout(mclose, timeout);
	}

	// cancel close timer
	function mcancelclosetime() {
		if (closetimer) {
			window.clearTimeout(closetimer);
			closetimer = null;
		}
	}

	// close layer when click-out
	document.onclick = mclose;
</script>
</head>
<body
	style="background-color: #999999; margin-top: 3px; margin-left: 3px;">
	<table
		style="width: 100%; height: 100%; border: 0; text-align: center; padding: 0; background-color: #669900; border-spacing: 1px;">
		<tr bgcolor="#99CC66">
			<td height="50" align="center" bgcolor="#99CC66">
				<p
					style="color: #333333; font-size: 14pt; font-weight: bold; font-family: Courier New">
					☆ Media Vault ☆</p>
			</td>
		</tr>
		<tr>
			<td height="25">
				<%
					UserService userService = UserServiceFactory.getUserService();
					User user = userService.getCurrentUser();
					if (user != null) {
				%>
				<table
					style="width: 100%; border: 0; padding: 0; border-spacing: 0;">
					<tr align="center" bgcolor="#eeeeee">
						<td height="25" align="right">&nbsp;</td>
						<td align="center" bgcolor="#eeeeee">
							<table style="padding: 3px; border-spacing: 0;">
							<form id="search" action="search.jsp" method="post">
								<tr style="text-align: center;">
									<td><font style="font-family: Courier New;">《
											Welcome <span style="color: #0066CC;"><%=user.getNickname()%></span>
											》</font></td>

									<td>
										<ul id="sddm">
											<li><a href="/">Home</a></li>
											<li>
												<a href="fileupload.jsp" target="main">File Upload</a>
												
											</li>
											<li><a href="#" onmouseover="mopen('m2')"
												onmouseout="mclosetime()">File Listing</a>
												<div id="m2" onmouseover="mcancelclosetime()"
													onmouseout="mclosetime()">
													<a href="listallfiles.jsp" target="main">All Files</a>
												</div>
											</li>
											<li><a
												href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign
													out</a></li>
										</ul>
										<div style="clear: both"></div>
									</td>
									<td>
										<input type="text" name="searchName">
										<input type="submit" value="Search">
									</td>
								</tr>
								</form>
							</table>
						</td>
					</tr>
				</table> <%
 	} else {
 %> Please <a
				href="<%=userService.createLoginURL(request.getRequestURI())%>">Sign
					in</a> here. <%
 	}
 %>
			</td>
		</tr>
		<tr>
			<td style="text-align: center; background-color: #FFFFFF;">
				<table
					style="width: 100%; height: 100%; padding: 0; border-spacing: 0; border: 0;">
					<tr>
						<td>
							<%
								if (user != null) {
							%> <iframe width="100%" height="100%" style="border: 0;"
								src="listallfiles.jsp" name="main"> Your web browser
								doesn't support "iframe", please update.</iframe> <%
 	}
 %>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<td
			style="height: 20px; text-align: center; background-color: #99CC66;"><font
			style="color: #999966; font-family: Courier New">Copyright&copy;
				2011 Awesome team, QUT.</font>
		</td>
		</tr>
	</table>

</body>
</html>