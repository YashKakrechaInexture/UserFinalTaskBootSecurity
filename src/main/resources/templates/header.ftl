<#import "spring.ftl" as spring />
<!DOCTYPE html>
<html>
<head>
	<!-- custom css -->
	<link href="<@spring.url 'lib/css/header.css' />"
		  rel="stylesheet" >
	
</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#"><span class="brand-name">UserFinalTask</span></a>
			</div>
			<div class="nav navbar-nav navbar-right">
				<a href="logoutServlet" class="btn btn-danger" id="logout-btn">Logout</a>
			</div>
		</div>
	</nav>
</body>
</html>