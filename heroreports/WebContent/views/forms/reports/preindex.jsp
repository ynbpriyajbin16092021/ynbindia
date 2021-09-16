<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<script type="text/javascript">
function redirectForm()
{
	
	var path = window.location.href;
	var newpath = path.substr(0, path.lastIndexOf("/"));
	window.open(newpath+"/reports","bfs","width="+screen.width,"height="+screen.height);	
	

	}
</script>
<title>HERO</title>

</head>
<body onload="redirectForm()">

</body>
</html>