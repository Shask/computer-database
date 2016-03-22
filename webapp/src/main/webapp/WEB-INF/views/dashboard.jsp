<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"
	charset="utf-8" />

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">

<!-- Setting up messages for i18m -->
<spring:message code="filter.name" var="filternames" />
<spring:message code="search.name" var="searchname" />


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
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${ nbComputer }" />
				<spring:message code="computers.found" text="Computers found?" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="${searchname}" /> <input
							type="submit" value="${filternames}" id="searchsubmit"
							class="btn btn-primary"></input>
					</form>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_TRUSTED')">
						<mylib:link classe="btn btn-success" id="addComputer"
							target="addcomputer">
							<spring:message code="add" text="Add" />

						</mylib:link>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<a class="btn btn-default" id="editComputer"
							onclick="$.fn.toggleEditMode();"><spring:message code="edit"
								text="Edit" /></a>
					</sec:authorize>

				</div>
			</div>
		</div>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<form id="deleteForm" action="#" method="POST">
				<input type="hidden" name="selection" value="">
			</form>
		</sec:authorize>
		<!--  Order by arrow setting -->



		<div class="container" style="margin-top: 10px;">
			<table
				class="table table-?page=1&nbElements=50#striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table heaComputerder for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <mylib:link target="#"
									id="deleteSelected" onclick="$.fn.deleteSelected();">
									<i class="fa fa-trash-o fa-lg"></i>
								</mylib:link>
						</span></th>
						<th><mylib:link target="#" order="name">
								<spring:message code="computer.name" text="Computer name" />
							</mylib:link></th>
						<th><mylib:link target="#" order="introduced">
								<spring:message code="introduced.date" text="Introduced date" />
							</mylib:link></th>
						<!-- Table header for Discontinued Date -->
						<th><mylib:link target="#" order="discontinued">
								<spring:message code="discontinued.date"
									text="Discontinued date" />
							</mylib:link></th>
						<!-- Table header for Company -->
						<th><mylib:link target="#" order="company_name">
								<spring:message code="company" text="Company" />
							</mylib:link></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">

					<c:forEach items="${ computers }" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${ computer.id }"></td>
							<td><mylib:edit idcomputer="${ computer.id }">
									<c:out value="${ computer.name }" />
								</mylib:edit></td>
							<td><c:out value="${ computer.introduced }" /> <!-- Introd date --></td>
							<td><c:out value="${ computer.discontinued }" /> <!-- disco date --></td>
							<td><c:out value="${ computer.companyName }" /> <!-- Company --></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
	<footer class="navbar-fixed-bottom">

		<mylib:page currentpage="${currentpage}" />

	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>

	<script type="text/javascript">
		var strings = new Array();
		strings['edit'] = "<spring:message code='edit' javaScriptEscape='true' />";
		strings['view'] = "<spring:message code='view' javaScriptEscape='true' />";
		strings['deleteMessage'] = "<spring:message code='delete.message' javaScriptEscape='true' />";
	</script>
	<script type="text/javascript" src="js/dashboard.js"></script>


</body>
</html>