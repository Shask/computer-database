<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<mylib:link classe="navbar-brand" target="dashboard"
				currentpage="${currentpage}">Application - Computer Database</mylib:link>

			<div class="navbar-right" style="margin-top: 0.15cm;">

				<a class="right" href="?lang=fr"><img border="10" alt="French"
					src="https://cdn4.iconfinder.com/data/icons/world-flags/180/flag_france-512.png"
					width="50" height="35"> </a> <a class="right" href="?lang=en"><img
					border="10" alt="English"
					src="https://cdn4.iconfinder.com/data/icons/world-flags/180/flag_australia-512.png"
					width="50" height="35"> </a>

			</div>

			<!-- <div class="pull-right"
				style="margin-top: 0.15cm; margin-right: 0.15cm;">
				<form
					action="<c:url value="/j_spring_security_logout" />">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<button type="submit" class="btn btn-danger" name="submit">LOGOUT</button>
				</form>
			</div> -->

			<form action="j_spring_security_logout" method="post" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>

			<script>
				function formSubmit() {
					document.getElementById("logoutForm").submit();
				}
			</script>

			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<div class="pull-right"
					style="margin-top: 0.15cm; margin-right: 0.15cm;">

					<a class="btn btn-danger" href="javascript:formSubmit()">
						<spring:message code="logout" text="logout" /></a>
				</div>
				<div class="pull-right"
					style="margin-top: 0.35cm; margin-right: 0.15cm;">
					<div style="color: white;">
					<spring:message code="welcome" text="welcome" /> :
						${pageContext.request.userPrincipal.name}</div>
				</div>
			</c:if>

		</div>
	</header>
</body>
</html>