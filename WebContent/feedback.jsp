<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MAS-Partyplanner</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<link rel="stylesheet" href="custom.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
            function calculateTotalBudget(val) {
            	var totalSum = 0;
            	totalSum += parseFloat(document.getElementById('foodBudget').value)
            	totalSum += parseFloat(document.getElementById('drinksBudget').value)
            	totalSum += parseFloat(document.getElementById('locationBudget').value)
                document.getElementById('totalBudget').value = totalSum;
            }
        </script>
</head>
<body class="body">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
         <div class=" navbar-header"> 
            <a class="navbar-brand" href="#">
            	<img src="img/MAS_Logo.png" class="d-inline-block align-top" alt="">
                </a>
            </div> 
            <ul class="nav navbar-nav">
                <li><a href="index.jsp">Home</a></li>
                <li class="active"><a href="feedback.jsp">Feedback Page</a></li>
            </ul>
        </div>
    </nav>
    <div class="jumbotron jumbotron-fluid text-center">
        <div class="page-header row">
            <div class="col">
                <h1 class="display-4">
                    Do your <b>Feedback!</b>
                </h1>
            </div>
        </div>
        <div class="container text-center align-items-center">
            <form role="form" action="FeedbackServlet" method="post">
                <p>To return perfectly applicable cases, we need your feedback. Were the values predicted by the MAS party planner correct or have you had to adjust the values a bit? - Let us
                    know!</p>
                <p>
                    Enter your <b>Party ID</b> here :
                </p>
                <input id="id" name="id" class="id1" type="text" />
                <p>
                    <button id="buttonFeedback" class="btn" type="submit">Get Case!</button>
                </p>
            </form>
        </div>
    </div>
    <div class="container">
        <c:if test="${not empty id}">
            <form role="form" action="AlterCaseServlet" method="post">
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Party ID</th>
                            <th scope="col">Verified</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input id="id" name="id" type="text" value="${id}" readonly /></td>
                            <td><input id="verified" name="verified" type="text" value="${verified}" readonly /></td>
                        </tr>
                    </tbody>
                </table>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Month</th>
                            <th scope="col">Year</th>
                            <th scope="col">Guest Count</th>
                            <th scope="col">Occasion</th>
                            <th scope="col">Location Type</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><select id="month" name="month" class="form-control">
                                    <option ${month=="January"?"selected":""} value="January">January</option>
                                    <option ${month=="February"?"selected":""} value="February">February</option>
                                    <option ${month=="March"?"selected":""} value="March">March</option>
                                    <option ${month=="April"?"selected":""} value="April">April</option>
                                    <option ${month=="May"?"selected":""} value="May">May</option>
                                    <option ${month=="June"?"selected":""} value="June">June</option>
                                    <option ${month=="July"?"selected":""} value="July">July</option>
                                    <option ${month=="August"?"selected":""} value="August">August</option>
                                    <option ${month=="September"?"selected":""} value="September">September</option>
                                    <option ${month=="October"?"selected":""} value="October">October</option>
                                    <option ${month=="November"?"selected":""} value="November">November</option>
                                    <option ${month=="December"?"selected":""} value="December">December</option>
                            </select></td>
                            <td><select id="year" name="year" class="form-control">
                                    <c:if test="${not empty year}">
                                        <option value="${year }" selected style="display: none">${year }</option>
                                    </c:if>
                                    <%
                                                    for (int i = 2000; i <= 2050; i++) {
                                                %>
                                    <option value="<%=i%>"><%=i%></option>
                                    <%
                                                    }
                                                %>
                            </select></td>
                            <td><select id="guestCount" name="guestCount" class="form-control">
                                    <c:if test="${not empty guestCount}">
                                        <option value="${guestCount }" selected style="display: none">${guestCount }</option>
                                    </c:if>
                                    <%
                                                    for (int i = 1; i <= 1000; i++) {
                                                %>
                                    <option value="<%=i%>"><%=i%></option>
                                    <%
                                                    }
                                                %>
                            </select></td>
                            <td><select id="occasion" name="occasion" class="form-control">
                                        <option ${occasion=="anniversary"?"selected":""} value="anniversary">anniversary</option>
                                        <option ${occasion=="birthday"?"selected":""} value="birthday">birthday</option>
                                        <option ${occasion=="wedding"?"selected":""} value="wedding">wedding</option>
                                        <option ${occasion=="regular"?"selected":""} value="regular">regular</option>
                            </select></td>
                            <td><select id="locationType" name="locationType" class="form-control">
                                        <option ${locationType=="private"?"selected":""} value="private">private</option>
                                        <option ${locationType=="thirdparty"?"selected":""} value="thirdparty">third party</option>
                            </select></td>
                        </tr>
                    </tbody>
                </table>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Total Budget</th>
                            <th scope="col">Location Budget</th>
                            <th scope="col">Food Budget</th>
                            <th scope="col">Drinks Budget</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input id="totalBudget" name="totalBudget" type="number" value="${totalBudget}" readonly required /></td>
                            <td><input id="locationBudget" name="locationBudget" value="${locationBudget}" type="number" onchange="calculateTotalBudget()" required /></td>
                            <td><input id="foodBudget" name="foodBudget" value="${foodBudget}" type="number" onchange="calculateTotalBudget()" required /></td>
                            <td><input id="drinksBudget" name="drinksBudget" value="${drinksBudget}" type="number" onchange="calculateTotalBudget()" required /></td>
                        </tr>
                    </tbody>
                </table>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Meat Amount</th>
                            <th scope="col">Garnish Amount</th>
                            <th scope="col">Snacks Amount</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input id="meatAmount" name="meatAmount" type="number" value="${meatAmount}" required /></td>
                            <td><input id="garnishAmount" name="garnishAmount" value="${garnishAmount}" type="number" required /></td>
                            <td><input id="snacksAmount" name="snacksAmount" value="${snacksAmount}" type="number" required /></td>
                        </tr>
                    </tbody>
                </table>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Beer Amount</th>
                            <th scope="col">Wine Amount</th>
                            <th scope="col">Spirits Amount</th>
                            <th scope="col">Softs Amount</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input id="beerAmount" name="beerAmount" type="number" value="${beerAmount}" /></td>
                            <td><input id="wineAmount" name="wineAmount" value="${wineAmount}" type="number" /></td>
                            <td><input id="spiritsAmount" name="spiritsAmount" value="${spiritsAmount}" type="number" /></td>
                            <td><input id="softsAmount" name="softsAmount" value="${softsAmount}" type="number" /></td>
                        </tr>
                    </tbody>
                </table>
                <button class="btn col-md-12 btn-success" type="submit">Verify Party Information!</button>
            </form>
        </c:if>
        <p>
            <c:if test="${not empty message}">
                    ${message}
                </c:if>
        </p>
    </div>
</body>
</html>