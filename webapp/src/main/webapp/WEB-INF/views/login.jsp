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


<!-- Setting up messages for i18m -->
<spring:message code="username" var="username" />
<spring:message code="password" var="password" />
<spring:message code="signin" var="signin" />


</head>
<body>
	<jsp:include page="header.jsp" />

	<section id="main">


		<div class="col-md-4 col-md-offset-4" style="text-align: center">
		
		<!-- Message when loggout -->
			<c:if test="${not empty logout}">
				<div class="alert alert-success"><spring:message code="${logout}" text="Logout successful" /></div>
			</c:if>

			<br /> <br /> <i class="fa fa-user fa-5x"></i> <br /> <br />
			
			<!-- Loggin Form -->
			<form name='loginForm'
				action="<c:url value='/j_spring_security_check' />" method='POST'>
				<div class="form-group">
					<input class="form-control" placeholder="${username}" type="text"
						name="username" /> <br /> <input class="form-control"
						placeholder="${password}" type="password" name="password" /> <br />
					
					<!-- Message when Wrong credentials -->
					<c:if test="${not empty error}">
						<div class="alert alert-danger">
							<spring:message code="${error}" text="Error login" />
						</div>
					</c:if>
					<button type="submit" class="btn btn-success" name="submit">${signin}</button>
				</div>
				
				<!-- csrf -->
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