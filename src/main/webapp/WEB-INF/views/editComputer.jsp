<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>
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
         <mylib:link classe="navbar-brand" target="dashboard" currentpage="${currentpage}" >Application - Computer Database</mylib:link>
	</div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: <c:out  value="${computer.id}"/>
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="editcomputer" method="POST" id="editcomputer" name="editcomputer">
                        <input type="hidden" value="0"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name" value="${computer.name}">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced"  name="introduced" placeholder="Introduced date" value="${computer.introduced}">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" value="${computer.discontinued}">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" class="form-control" id="companyId" name="company" >
                               
                                <option value="${computer.company.id}">${computer.company.name}</option>
                                 <option value="null">--</option>
                                    <c:forEach items="${ companies }" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary"> 
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
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