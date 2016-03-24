<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../css/main.css" rel="stylesheet" media="screen">


<!-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! SPRING MESSAGES  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->

<spring:message code="edit" var="edit" />
<spring:message code="computer.name" var="computername" />
<spring:message code="introduced.date" var="introduceddate" />
<spring:message code="discontinued.date" var="discontinueddate" />

</head>
<body>
	<jsp:include page="header.jsp"/>
	
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">
						id:
						<c:out value="${id}" />
					</div>
					<h1>
						<spring:message code="edit.computer" text="Edit Computer" />
					</h1>

					<springForm:form action="editcomputer" commandName="computerdto" modelAttribute="computerdto" method="POST" id="editcomputer" name="editcomputer">
						<input type="hidden" value="0" />
						<fieldset>
							<div class="form-group">
								<label for="name">${computername}</label>
								<springForm:input path="name" type="text" class="form-control" id="name" name="name" placeholder="${computername}" value="${computer.name}" />
								<springForm:errors path="name" cssClass="alert-danger" />
							</div>
							<div class="form-group">
								<label for="introduced">${introduceddate}</label>
								<springForm:input path="introduced" type="date" class="form-control" id="introduced" name="introduced" placeholder="${introduceddate}" value="${computer.introduced}" />
								<springForm:errors path="introduced" cssClass="alert-danger" />
							</div>
							<div class="form-group">
								<label for="discontinued">${discontinueddate}</label>
								<springForm:input path="discontinued" type="date" class="form-control" id="discontinued" name="discontinued" placeholder="${discontinueddate}" value="${computer.discontinued}" />
								<springForm:errors path="discontinued" cssClass="alert-danger" />
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="company" text="Company" /></label>
								<springForm:select path="companyId" class="form-control" id="companyId" name="company">
									<springForm:option value="${computer.companyId}">${computer.companyName}</springForm:option>
									<springForm:option value="-1">--</springForm:option>
									<c:forEach items="${companies}" var="company">
										<c:if test="${company.id != computer.companyId}">
											<springForm:option value="${company.id}">${company.name}</springForm:option>
										</c:if>
									</c:forEach>
								</springForm:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="${edit}" class="btn btn-primary">
							<spring:message code="or" text="or" />
							<a href="dashboard" class="btn btn-default"><spring:message code="cancel" text="Cancel" /></a>
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
					</script>
					<script type="text/javascript" src="js/jqueryValidator.js"></script>
				</div>
			</div>
		</div>
	</section>
</body>

</html>