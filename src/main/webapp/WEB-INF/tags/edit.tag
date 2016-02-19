
<%@ tag language="java" pageEncoding="UTF-8" display-name="link"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="idcomputer" required="false" type="java.lang.String" description="what is the id of the object for css purposes"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<a href="editcomputer?id=${idcomputer}" onclick=""><jsp:doBody /></a>



