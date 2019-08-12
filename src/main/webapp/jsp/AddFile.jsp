<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="css/Style.css" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<fmt:setLocale value="${langLocal}"/>
<fmt:setBundle basename="InterfaceValue" var="local"/>
<html>
<head>
    <title>Add File</title>
</head>
<body>
<div class="dialogForm">
    <h2><fmt:message key="key.addFile" bundle="${local}"/></h2>
    <form action="/do/upload" method="post" enctype="multipart/form-data">
        <div class="form-group" style="line-height: 0.5">
            <select id="book" name="book">
                <c:forEach items="${bookList}" var="book">
                    <option value="${book.name}">${book.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group" style="line-height: 1.5">
            <label for="file"><fmt:message key="key.chooseFile" bundle="${local}"/>:</label>
            <input type="file" class="form-control" id="file" name="file" required accept="/*">
        </div>
        <div>
            <button type="submit" class="btn btn-primary">
                <fmt:message key="key.enter" bundle="${local}"/></button>
            <a href="/do/goToMainPage" style="padding-left: 20%"><fmt:message key="key.cancel" bundle="${local}"/></a>
        </div>
    </form>
</div>
</body>
</html>
