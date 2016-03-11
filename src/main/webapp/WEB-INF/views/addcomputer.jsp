<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8" />
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">

<title>Computer Database</title>


<spring:message code="add" var="add" />
<spring:message code="computer.name" var="computername" />
<spring:message code="introduced.date" var="introduceddate" />
<spring:message code="discontinued.date" var="discontinueddate" />


</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<mylib:link classe="navbar-brand" target="dashboard" currentpage="${currentpage}">Application - Computer Database</mylib:link>
			<div class="navbar-right" style="margin-top: 0.15cm;">
				<a class="right" href="?lang=fr"><img border="10" alt="French" src="https://cdn4.iconfinder.com/data/icons/world-flags/180/flag_france-512.png" width="50" height="35"> </a> <a class="right" href="?lang=en"><img border="10" alt="English" src="https://cdn4.iconfinder.com/data/icons/world-flags/180/flag_australia-512.png" width="50" height="35"> </a>
			</div>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="add.computer" text="Add Computer" />
					</h1>
					<springForm:form action="addcomputer" commandName="computerdto" modelAttribute="computerdto" method="POST" id="addcomputer" name="addcomputer">
						<input type="hidden" value="0" />
						<fieldset>
							<div class="form-group">
								<label for="name">${computername}</label>
								<springForm:input path="name" type="text" class="form-control" id="computerName" name="name" placeholder="${computername}" />
								<springForm:errors path="name" cssClass="alert-danger" />
							</div>
							<div class="form-group">
								<label for="introduced">${introduceddate}</label>
								<springForm:input path="introduced" type="date" class="form-control" id="introduced" name="introduced" placeholder="${introduceddate}" />
								<springForm:errors path="introduced" cssClass="alert-danger" />
							</div>
							<div class="form-group">
								<label for="discontinued">${discontinueddate}</label>
								<springForm:input path="discontinued" type="date" class="form-control" id="discontinued" name="discontinued" placeholder="${discontinueddate}" />
								<springForm:errors path="discontinued" cssClass="alert-danger" />
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="company" text="Company" /></label>
								<springForm:select path="companyId" class="form-control" id="companyId" name="company">
									<c:forEach items="${companies}" var="company">
										<springForm:option value="${company.id}">${company.name}</springForm:option>
									</c:forEach>
								</springForm:select>
							</div>

						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="${add}" class="btn btn-primary" id="submit">
							<spring:message code="or" text="or" />
							<mylib:link target="dashboard" classe="btn btn-default">
								<spring:message code="cancel" text="Cancel" />
							</mylib:link>
						</div>
					</springForm:form>
					<script type="text/javascript" src="js/jquery.min.js"></script>
					<script type="text/javascript" src="js/jquery.validate.js"></script>
					<script type="text/javascript" src="js/additional-methods.js"></script>
					<script type="text/javascript">
						var strings = new Array();
						strings['name'] = <spring:message code="valid.name" />;
						strings['intro'] = <spring:message code="valid.date" />;
						strings['discon'] = <spring:message code="valid.date"/>;
						string['regex']= <spring:message code="regex"/>;
					</script>
					<script type="text/javascript" src="js/jqueryValidator.js"></script>
				</div>
			</div>
		</div>
	</section>

</body>

</html>
