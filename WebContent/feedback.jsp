<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>MAS-Partyplanner</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="jumbotron text-center">
            <h2>
                Do your <b>Feedback!</b>
            </h2>
            <form role="form" action="FeedbackServlet" method="post">
                <label for="id">Party ID:</label>
                <input id="id" name ="id" type="text"/>
                <button class="btn col-md-12 btn-success" type="submit">Get Case!</button>
            </form>
            
            <c:if test="${not empty caseId}">
                <form role="form" action="QueryServlet" method="post">
                    <label for="id">Party ID:</label>
                    <input id="id" name ="id" type="text" value="${caseId}" readonly />
                    <button class="btn col-md-12 btn-success" type="submit">Get Case!</button>
                </form>
            </c:if>

        </div>
    </body>
</html>