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
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">MAS-Partyplanner</a>
            </div>
            <ul class="nav navbar-nav">
                <li class="active">
                    <a href="index.jsp">Home</a>
                </li>
                <li>
                    <a href="feedback.jsp">Feedback Page</a>
                </li>
            </ul>
        </div>
    </nav>
    <div class="jumbotron text-center">
        <h1>
            Welcome to the <b>MAS-Partyplanner!</b>
        </h1>
        <p>Want to organize a party, but don't know how to do it? Benefit from other's experiences!</p>
    </div>
    <form role="form" action="QueryServlet" method="post">
        <div id="metaInformation">
            <h2>To use our services, we need some initial meta information for your planned party!</h2>
            <div class="col-md-3">
                <label for="monthMeta">Month:</label>
                <select id="monthMeta" name="monthMeta" class="form-control">
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
            </div>
            <div class="col-md-3">
                <label for="yearMeta">Year:</label>
                <input id="yearMeta" name="yearMeta" type="number" value="2021" />
            </div>
            <div class="col-md-3">
                <label for="guestCountMeta">Expected Guests</label>
                <input id="guestCountMeta" name="guestCountMeta" type="number" />
            </div>
            <div class="col-md-3">
                <label for="occasionMeta">Party Occasion:</label>
                <select id="occasionMeta" name="occasionMeta" class="form-control">
                    <option value="anniversary">anniversary</option>
                    <option value="birthday">birthday</option>
                    <option value="wedding">wedding</option>
                    <option value="regular">regular</option>
                </select>
            </div>
        </div>
        <div id="accordion">
            <div class="card">
                <div class="card-header" id="headingOne">
                    <h5 class="mb-0">
                        <button type="button" class="btn btn-primary collapsed" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true"
                            aria-controls="collapseOne">Budget Planner!</button>
                    </h5>
                </div>
                <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
                    <div class="card-body">
                        <h2>
                            Wanna know how much <b>money</b> you'll most likely need for your party? Provide some information and we will tell you!
                        </h2>
                        <button id="budgetButton" class="btn col-md-12 btn-success" name="submit-button" value="BudgetAgent" type="submit">Tell
                            me!</button>
                        <c:if test="${not empty budgetMessage}">
                            ${budgetMessage}
                            <label for="totalBudget">Total Budget:</label>
                            <input id="totalBudget" name="totalBudget" value="0.0" type="number" value="${totalBudget}" readonly />
                            <label for="locationBudget">Location Budget:</label>
                            <input id="locationBudget" name="locationBudget" value="${locationBudget}" type="number" onchange="calculateTotalBudget()" />
                            <label for="foodBudget">Food Budget:</label>
                            <input id="foodBudget" name="foodBudget" value="${foodBudget}" type="number" onchange="calculateTotalBudget()" />
                            <label for="drinksBudget">Drinks Budget:</label>
                            <input id="drinksBudget" name="drinksBudget" value="${drinksBudget}" type="number" onchange="calculateTotalBudget()" />
                            <input id="adaptBudgetValuesCheckBox" name="adaptBudgetValuesCheckBox" value="0.0" type="checkbox" />
                            <label for="adaptBudgetValuesCheckBox">Adapt for party plan?</label>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header" id="headingTwo">
                    <h5 class="mb-0">
                        <button type="button" class="btn btn-primary collapsed" data-toggle="collapse" data-target="#collapseTwo"
                            aria-expanded="false" aria-controls="collapseTwo">Food Planner!</button>
                    </h5>
                </div>
                <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
                    <div class="card-body">
                        <h2>
                            Wanna know how much <b>food</b> you'll most likely need for your party? Provide some information and we will tell you!
                        </h2>
                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header" id="headingThree">
                    <h5 class="mb-0">
                        <button type="button" class="btn collapsed" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false"
                            aria-controls="collapseThree">Drinks Planner!</button>
                    </h5>
                </div>
                <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordion">
                    <div class="card-body">
                        <h2>
                            Wanna know how much <b>drinks</b> you'll most likely need for your party? Provide some information and we will tell you!
                        </h2>
                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header" id="headingThree">
                    <h5 class="mb-0">
                        <button type="button" class="btn collapsed" data-toggle="collapse" data-target="#collapseFour" aria-expanded="false"
                            aria-controls="collapseThree">Party Planner!</button>
                    </h5>
                </div>
                <div id="collapseFour" class="collapse" aria-labelledby="headingThree" data-parent="#accordion">
                    <div class="card-body">
                        <h2>
                            Wanna <b>create a wholesome</b> party plan? Provide some information and we will help you!
                        </h2>
                        <label for="guestCount">Guest Count:</label>
                        <input id="guestCount" name="guestCount" type="number" />
                        <label for="totalBudget">Total Budget:</label>
                        <input id="totalBudget" name="totalBudget" type="number" readonly />
                        <label for="locationBudget">Location Budget:</label>
                        <input id="locationBudget" name="locationBudget" type="number" onchange="calculateTotalBudget()" />
                        <label for="foodBudget">Food Budget:</label>
                        <input id="foodBudget" name="foodBudget" type="number" onchange="calculateTotalBudget()" />
                        <label for="drinksBudget">Drinks Budget:</label>
                        <input id="drinksBudget" name="drinksBudget" type="number" onchange="calculateTotalBudget()" />
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
                        <label for="year">Year:</label>
                        <input id="year" name="year" type="number" />
                        <label for="locationType">Location Type:</label>
                        <select id="locationType" name="locationType" class="form-control">
                            <option value="private">private</option>
                            <option value="thirdparty">thirdparty</option>
                        </select>
                        <label for="occasion">Party Occasion:</label>
                        <select id="occasion" name="occasion" class="form-control">
                            <option value="anniversary">anniversary</option>
                            <option value="birthday">birthday</option>
                            <option value="wedding">wedding</option>
                            <option value="regular">regular</option>
                        </select>
                        <label for="beerAmount">Beer Amount:</label>
                        <input id="beerAmount" name="beerAmount" type="number" />
                        <label for="wineAmount">Wine Amount:</label>
                        <input id="wineAmount" name="wineAmount" type="number" />
                        <label for="spiritsAmount">Spirits Amount:</label>
                        <input id="spiritsAmount" name="spiritsAmount" type="number" />
                        <label for="softsAmount">Softs Amount:</label>
                        <input id="softsAmount" name="softsAmount" type="number" />
                        <label for="meatAmount">Meat Amount:</label>
                        <input id="meatAmount" name="meatAmount" type="number" />
                        <label for="garnishAmount">Garnish Amount:</label>
                        <input id="garnishAmount" name="garnishAmount" type="number" />
                        <label for="snacksAmount">Snacks Amount:</label>
                        <input id="snacksAmount" name="snacksAmount" type="number" />
                        <button class="btn col-md-12 btn-success" type="submit">Start Query!</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</body>
</html>