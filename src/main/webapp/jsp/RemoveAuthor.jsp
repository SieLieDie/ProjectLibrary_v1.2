<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/Style.css" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <fmt:setLocale value="${langLocal}"/>
    <fmt:setBundle basename="InterfaceValue" var="local"/>
    <title>Remove Author</title>
</head>
<body>
<div class="dialogForm">
    <h2><fmt:message key="key.removeAuthor" bundle="${local}"/></h2>
    <form action="/do/removeAuthor" method="post">
        <div class="form-group" style="line-height: 1.5">
            <label for="authorCoreID"><fmt:message key="key.authorCore" bundle="${local}"/>:</label>
            <select id="authorCoreID" name="authorCoreID">
                <c:forEach items="${authorList}" var="author">
                    <option value="${author.coreID}">${author.name}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <button type="submit" class="btn btn-primary">
                <fmt:message key="key.remove" bundle="${local}"/></button>
            <a href="/do/goToMainPage" style="padding-left: 20%"><fmt:message key="key.cancel" bundle="${local}"/></a>
        </div>
    </form>
</div>
</body>
</html>
