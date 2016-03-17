
<%@ tag language="java" pageEncoding="UTF-8" display-name="order"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="order" required="true" type="java.lang.String" description="sort field"%>
<%@ attribute name ="by" required="false" type="java.lang.String" description ="Ascendant or descendant orderby  "%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<a href="dashboard?order=${idcomputer}" onclick=""><jsp:doBody /></a>



