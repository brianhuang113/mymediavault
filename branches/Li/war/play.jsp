<html>
<head>
<title>File List</title>
<link href="liststyle.css" rel="Stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<OBJECT ID="MediaPlayer1" CLASSID="CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95" 
CODEBASE="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab# Version=5,1,52,701"
 STANDBY="Loading Microsoft Windows® Media Player components..." TYPE="application/x-oleobject" width="640" height="360">
<param name="fileName" value="/serve?blob_key=<%=request.getParameter("blob_key")%>">
<param name="animationatStart" value="true">
<param name="transparentatStart" value="true">
<param name="BufferingTime" value="2">
<param name="ShowStatusBar" value="true">
<param name="autoStart" value="true">
<param name="showControls" value="true">
<param name="Volume" value="-300">
<embed type="application/x-mplayer2" pluginspage="http://www.microsoft.com/Windows/MediaPlayer/" src="/serve?blob_key=<%=request.getParameter("blob_key")%>"
 name="MediaPlayer1" width=640 height=360 autostart=1 showcontrols=1 volume=-300>
</OBJECT>
</body>
</html>
