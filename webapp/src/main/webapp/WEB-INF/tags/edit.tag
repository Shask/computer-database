
<%@ tag language="java" pageEncoding="UTF-8" display-name="link"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="idcomputer" required="false" type="java.lang.String" description="what is the id of the object for css purposes"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_TRUSTED')">
<a href="editcomputer?id=${idcomputer}" onclick=""><jsp:doBody /></a>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_NEW')">
<jsp:doBody />
</sec:authorize>
