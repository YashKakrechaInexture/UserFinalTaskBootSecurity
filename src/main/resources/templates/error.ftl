<#import "spring.ftl" as spring />
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<title>Error Page</title>
	
	<!-- jquery cdn -->
	<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
	
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

	<!-- Bootstrap JavaScript -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	
	<!-- custom css -->
	<link href="<@spring.url 'lib/css/login.css' />"
		  rel="stylesheet" >
	
</head>
<body>
	
	<span>Sorry, Something went wrong!</span>
	<br>
	<a id="home-link" href="homepage">Go homepage</a>
	
	<#include "footer.ftl">
	
</body>
</html>