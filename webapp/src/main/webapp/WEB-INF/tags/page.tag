<%@ tag language="java" pageEncoding="UTF-8" display-name="page"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<%@ attribute name="currentpage" required="false" type="java.lang.String" description="Current page we're at"%>
<%@ attribute name="PageSize" required="false" type="java.lang.String" description="Number of item to display in a page"%>

<form class="btn-group btn-group-sm pull-right" role="group">


	<mylib:link target="/computerdb/computer/list" classe="btn btn-default" currentpage="1" pagesize="10">10</mylib:link>
	<mylib:link target="/computerdb/computer/list" classe="btn btn-default" currentpage="1" pagesize="50">50</mylib:link>
	<mylib:link target="/computerdb/computer/list" classe="btn btn-default" currentpage="1" pagesize="100">100</mylib:link>


</form>
<div class="container text-center">
	<ul class="pagination">
		<li><a href="?page=${currentpage -1}" aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
		</a></li>
		<c:choose>

			<c:when test="${currentpage <= 2 }">
				<c:forEach var="i" begin="1" end="5" step="1">
					<li><a href="?page=${i}"><c:out value="${i}" /></a></li>
				</c:forEach>
			</c:when>

			<c:when test="${currentpage > 2 }">
				<c:forEach var="i" begin="${currentpage - 2}" end="${currentpage + 2}" step="1">
					<li><a href="?page=${i}"><c:out value="${i}" /></a></li>
				</c:forEach>
			</c:when>
		</c:choose>
		<li><a href="?page=${currentpage +1}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</div>