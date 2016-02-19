<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">

</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<mylib:link classe="navbar-brand" target="dashboard" currentpage="${currentpage}" >Application - Computer Database</mylib:link>

		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle"><c:out value="${ nbComputer }" /> Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" /> <input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<mylib:link classe="btn btn-success" id="addComputer" target="addcomputer">Add</mylib:link>
					<mylib:link classe="btn btn-default" id="editComputer" target="#" onclick="$.fn.toggleEditMode();" >Edit</mylib:link>


				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-?page=1&nbElements=50#striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table heaComputerder for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;">
						<input type="checkbox" id="selectall" /> <span style="vertical-align: top;"> - <mylib:link target="#" id="deleteSelected" onclick="$.fn.deleteSelected();"> <i class="fa fa-trash-o fa-lg"></i>
							</mylib:link>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">

					<c:forEach items="${ computers }" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb" class="cb" value="${ computer.id }"></td>
							<td><mylib:edit idcomputer="${ computer.id }"><c:out value="${ computer.name }" /></mylib:edit></td>
							<td><c:out value="${ computer.introduced }" /> <!-- Introd date --></td>
							<td><c:out value="${ computer.discontinued }" /> <!-- disco date --></td>
							<td><c:out value="${ computer.company.name }" /> <!-- Company --></td>

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
	<script src="js/dashboard.js"></script>

</body>
</html>