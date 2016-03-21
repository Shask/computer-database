<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>



	<section id="main">

		<c:if test="${not empty param.error}">Invalid username and password.</c:if>
		<c:if test="${not empty param.logout}">You have been logged out.</c:if>
		<div class="col-md-4 col-md-offset-4" style="text-align: center">
			<br /> <br /> <i class="fa fa-user fa-5x"></i> <br /> <br />
			<form name='loginForm'
				action="<c:url value='/j_spring_security_check' />" method='POST'>
				<div class="form-group">
					<input class="form-control" placeholder="User" type="text"
						name="username" /> <br /> 
						<input class="form-control" placeholder="Password" type="password" name="password" /> <br />

					<button type="submit" class="btn btn-success" name="submit">Sign in</button>
				</div>
					<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			</form>
		</div>
	</section>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>