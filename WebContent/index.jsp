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
    <nav id="nav" class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <ul class="nav navbar-nav">
                <li><a href="index.jsp">Home</a></li>
                <li><a href="feedback.jsp">Party Feedback</a></li>
            </ul>
        </div>
    </nav>
    <body>
        <div class="jumbotron text-center">
            <h1>
                Welcome to the <b>MAS-Partyplanner!</b>
            </h1>
            <p>Want to organize a party, but don't know how to organize your buyings? Benefit from other's experiences!</p>
            
            <h2>
                Do your <b>Query!</b>
            </h2>
            <form role="form" action="QueryServlet" method="post">
                <label for="guestCount">Guest Count:</label>
                <input id="guestCount" name ="guestCount" type="number"/>
                <label for="month">Month:</label>
                <select id="month" name="month" class="form-control">
                    <option value="January">January</option>
                    <option value="February">February</option>
                    <option value="March">March</option>
                    <option value="April">April</option>
                    <option value="June">June</option>
                    <option value="July">July</option>
                    <option value="August">August</option>
                    <option value="September">September</option>
                    <option value="October">October</option>
                    <option value="November">November</option>
                    <option value="December">December</option>
                </select>
                <button class="btn col-md-12 btn-success" type="submit">Start Query!</button>
            </form>
        </div>
    </body>
</html>