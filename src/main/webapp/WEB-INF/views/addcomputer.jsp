<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.js"></script>
<script type="text/javascript" src="js/additional-methods.js"></script>
<title>Computer Database</title>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<mylib:link classe="navbar-brand" target="dashboard" currentpage="${currentpage}" >Application - Computer Database</mylib:link>

		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="addcomputer" method="POST" id="addcomputer">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									name="computerName" type="text" class="form-control"
									id="computerName" placeholder="Computer name" required
									data-rule-minlength="2" data-msg-minlength="name is too short"
									value="${fn:escapeXml(param.computerName)}">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									name="introduced" type="date" class="form-control"
									id="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									name="discontinued" type="date" class="form-control"
									id="discontinued" placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="company">
									<c:forEach items="${ companies }" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary"
								id="submit"> or <mylib:link target="dashboard"
								classe="btn btn-default">Cancel</mylib:link>
						</div>
					</form>

					<script>
					$.validator.addMethod("alphanumeric", function(value, element) {
					    return !jQuery.validator.methods.required(value, element) || /^[a-zA-Z0-9_]+$/i.test(value);
					}
					, "Letters, numbers or underscores only please"); 
						$('#addcomputer').validate({
							rules : {
								computerName : {
									alphanumeric : true,
									required : true,
									minlength : 3
								},
								introduced : {
									dateISO : true
								},
								discontinued : {
									dateISO : true
								}
							},
							messages : {
								introduced : "Enter a valid date",
								discontinued : "Enter a valid date"
							},
							error : function(label) {
								$(this).addClass("div");
							}
						});
						
					</script>
				</div>
			</div>
		</div>
	</section>

</body>
</html>