<%@ page import="mediavault.Viewers.*" %>

<html>
<head>
<title>File List</title>
<link href="liststyle.css" rel="Stylesheet" type="text/css" />
<script type="text/javascript" src="jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="jquery.tools.min.js"></script>
<script type="text/javascript" src="flowplayer-3.2.6.min.js"></script>
<script type="text/javascript" src="flowplayer.playlist-3.0.8.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>

</style>
<script>


var playlist = [];
var playlist_clip = 0;
var playlist_shuffle = false;
var playlist_repeat = false;

$(function() {
	
	// setup player normally
	$f("player", "http://releases.flowplayer.org/swf/flowplayer-3.2.7.swf", {
		// show playlist buttons in controlbar
		clip: {
			scaling: 'fit'
		},
		
		plugins: {
			controls: {
				playlist: true
			}
		}
	});
	
	
});

function addEntry(url, title) {
	var newplay = {url: url,title: title};
	playlist.push(newplay);
	
	$f("player").setPlaylist(playlist);
	refresh_playlist();
}

function refresh_playlist()
{
    html = '';
    for(var i=0;i<playlist.length;i++){
        html = html
         + '<a href="javascript:delete_clip(' + i + ')">[X]</a> - '
         + '<a id="playlist_clip_' + i + '" href="javascript:play_clip(' + i + ')">' + playlist[i].title + '</a>'
         + '<br/>';
    }
    $("#playlist").html(html);
    $("#playlist_clip_" + playlist_clip).addClass('playlist_active');
}

function delete_clip(id)
{
    playlist.splice(id,1);
    if (playlist_clip == id) {
        playlist_clip = playlist_clip - 1;
    }
    refresh_playlist();
}

function play_clip(id)
{
    $("#playlist_clip_" + playlist_clip).removeClass('playlist_active');
    $f().play(playlist[id]);
    playlist_clip = id;
    $("#playlist_clip_" + playlist_clip).addClass('playlist_active');
}
</script>
</head>
<body>
<div id="player"></div>
<div id="playlist"></div>
<div style="float: left;">
<form action="/multidownload" method="post">
<br />
	<table id="mytable" style="border-spacing: 0">
		<tr>
			<th><input type="submit" value="download"></th>
			<th>File Name</th>
			<th>Creation Date</th>
			<th>Content Type</th>
			<th>Size</th>
			<th>Owner</th>
			<th>Description</th>
			<th>Download</th>
			<th>Delete</th>
			<th>Edit Information</th>
			<th>Add to Playlist</th>
		</tr>
		<%
			String listType = request.getParameter("listtype");
			out.print(Viewer.ListAllFiles(listType));
		%>
	</table>

		
	</form>
</div>
</body>
</html>