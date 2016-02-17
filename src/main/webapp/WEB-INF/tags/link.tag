
<%@ tag language="java" pageEncoding="UTF-8" display-name="link"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="classe" required="false" type="java.lang.String" description="what is the class of the object for css purposes"%>
<%@ attribute name="id" required="false" type="java.lang.String" description="what is the id of the object for css purposes"%>
<%@ attribute name="target" required="true" type="java.lang.String" description="Where we want to go"%>
<%@ attribute name="pagesize" required="false" type="java.lang.String" description="Number of item to display in a page"%>
<%@ attribute name="currentpage" required="false" type="java.lang.String" description="Current page we're at"%>
<%@ attribute name="onclick" required="false" type="java.lang.String" description="onclic for html"%>
<c:if test="${empty currentpage}" var="1" />
<c:if test="${empty pagesize}" var="50" />
<c:choose>

	<c:when test="${target != '#'}">
		<a class="${classe}" href="${target}?page=${currentpage}&nbElements=${pagesize}" id="${id}" onclick="${onclick}"> <jsp:doBody /></a>
	</c:when>
	<c:when test="${target == '#'}">
	<a class="${classe}" id="${id}" onclick="${onclick}"> <jsp:doBody /></a>
	</c:when>
</c:choose>


