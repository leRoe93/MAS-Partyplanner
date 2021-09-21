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

		// For the budget planner section
		var totalSum = 0;
		totalSum += parseFloat(document.getElementById('foodBudget').value)
		totalSum += parseFloat(document.getElementById('drinksBudget').value)
		totalSum += parseFloat(document.getElementById('locationBudget').value)
		document.getElementById('totalBudget').value = totalSum;

		// For the party planner section
		var totalSumPlanner = 0;
		totalSumPlanner += parseFloat(document
				.getElementById('foodBudgetPlanner').value)
		totalSumPlanner += parseFloat(document
				.getElementById('drinksBudgetPlanner').value)
		totalSumPlanner += parseFloat(document
				.getElementById('locationBudgetPlanner').value)
		document.getElementById('totalBudgetPlanner').value = totalSumPlanner;
	}

	function syncYear() {
		document.getElementById('yearPlanner').value = document
        .getElementById('year').value
	}

	function syncMonth() {
        document.getElementById('monthPlanner').value = document
        .getElementById('month').value
	}

	function syncOccasion() {
        document.getElementById('occasionPlanner').value = document
        .getElementById('occasion').value
	}
	
	function syncGuestCount() {
        document.getElementById('guestCountPlanner').value = document
        .getElementById('guestCount').value
	}

	function adaptBudgetsToPartyPlan() {
		var checkBox = document.getElementById("adaptBudgetValuesCheckBox")
		if (checkBox.checked == true) {
			document.getElementById('totalBudgetPlanner').value = document
					.getElementById('totalBudget').value
			document.getElementById('foodBudgetPlanner').value = document
					.getElementById('foodBudget').value
			document.getElementById('drinksBudgetPlanner').value = document
					.getElementById('drinksBudget').value
			document.getElementById('locationBudgetPlanner').value = document
					.getElementById('locationBudget').value
		}
	}

	function adaptFoodToPartyPlan() {
		var checkBox = document.getElementById("adaptFoodValuesCheckBox")
		if (checkBox.checked == true) {
			document.getElementById('meatAmountPlanner').value = document
					.getElementById('meatAmount').value
			document.getElementById('garnishAmountPlanner').value = document
					.getElementById('garnishAmount').value
			document.getElementById('snacksAmountPlanner').value = document
					.getElementById('snacksAmount').value
		}
	}

	function adaptDrinksToPartyPlan() {
		var checkBox = document.getElementById("adaptDrinksValuesCheckBox")
		if (checkBox.checked == true) {
			document.getElementById('beerAmountPlanner').value = document
					.getElementById('beerAmount').value
			document.getElementById('wineAmountPlanner').value = document
					.getElementById('wineAmount').value
			document.getElementById('spiritsAmountPlanner').value = document
					.getElementById('spiritsAmount').value
			document.getElementById('softsAmountPlanner').value = document
					.getElementById('softsAmount').value
		}
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
                <li class="active"><a href="index.jsp">Home</a></li>
                <li><a href="feedback.jsp">Feedback Page</a></li>
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
                <label for="month">Month:</label> <select id="month" name="month" class="form-control" onchange="syncMonth()">
                    <option value="January">January</option>
                    <option value="February">February</option>
                    <option value="March">March</option>
                    <option value="April">April</option>
                    <option value="May">May</option>
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
                <label for="year">Year:</label> <input id="year" name="year" type="number" value="2021" onchange="syncYear()" />
            </div>
            <div class="col-md-3">
                <label for="guestCount">Expected Guests</label> <input id="guestCount" value="10" name="guestCount" type="number"
                    onchange="syncGuestCount()" />
            </div>
            <div class="col-md-3">
                <label for="occasion">Party Occasion:</label> <select id="occasion" name="occasion" onchange="syncOccasion()" class="form-control">
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
                        <button type="button" class="btn col-md-12 btn-primary collapsed" data-toggle="collapse" data-target="#collapseOne"
                            aria-expanded="true" aria-controls="collapseOne">Budget Planner!</button>
                    </h5>
                </div>
                <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
                    <div class="card-body">
                        <h2>
                            Wanna know how much <b>money</b> you'll most likely need for your party? Provide some information and we will tell you!
                        </h2>
                        <button id="budgetButton" class="btn col-md-4 btn-success" name="submit-button" value="BudgetAgent" type="submit">Tell
                            me!</button>
                        <c:if test="${not empty budgetMessage}">
                            ${budgetMessage}
                            <label for="totalBudget">Total Budget:</label>
                            <input id="totalBudget" name="totalBudget" type="number" value="${totalBudget}" readonly />
                            <label for="locationBudget">Location Budget:</label>
                            <input id="locationBudget" name="locationBudget" value="${locationBudget}" type="number" onchange="calculateTotalBudget()" />
                            <label for="foodBudget">Food Budget:</label>
                            <input id="foodBudget" name="foodBudget" value="${foodBudget}" type="number" onchange="calculateTotalBudget()" />
                            <label for="drinksBudget">Drinks Budget:</label>
                            <input id="drinksBudget" name="drinksBudget" value="${drinksBudget}" type="number" onchange="calculateTotalBudget()" />
                            <input id="adaptBudgetValuesCheckBox" name="adaptBudgetValuesCheckBox" value="0.0" type="checkbox"
                                onchange="adaptBudgetsToPartyPlan()" />
                            <label for="adaptBudgetValuesCheckBox">Adapt for party plan?</label>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header" id="headingTwo">
                    <h5 class="mb-0">
                        <button type="button" class="btn col-md-12 btn-primary collapsed" data-toggle="collapse" data-target="#collapseTwo"
                            aria-expanded="false" aria-controls="collapseTwo">Food Planner!</button>
                    </h5>
                </div>
                <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
                    <div class="card-body">
                        <h2>
                            Wanna know how much <b>food</b> you'll most likely need for your party? Provide some information and we will tell you!
                        </h2>
                        <button id="foodButton" class="btn col-md-4 btn-success" name="submit-button" value="FoodAgent" type="submit">Tell
                            me!</button>
                        <c:if test="${not empty foodMessage}">
                            ${budgetMessage}
                            <label for="meatAmount">Meat Amount:</label>
                            <input id="meatAmount" name="meatAmount" type="number" value="${meatAmount}" readonly />
                            <label for="garnishAmount">Garnish Amount:</label>
                            <input id="garnishAmount" name="garnishAmount" value="${garnishAmount}" type="number" />
                            <label for="snacksAmount">Food Budget:</label>
                            <input id="snacksAmount" name="snacksAmount" value="${snacksAmount}" type="number" />
                            <input id="adaptFoodValuesCheckBox" name="adaptFoodValuesCheckBox" value="0.0" type="checkbox"
                                onchange="adaptFoodToPartyPlan()" />
                            <label for="adaptFoodValuesCheckBox">Adapt for party plan?</label>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header" id="headingThree">
                <h5 class="mb-0">
                    <button type="button" class="btn col-md-12 btn-primary collapsed" data-toggle="collapse" data-target="#collapseThree"
                        aria-expanded="false" aria-controls="collapseThree">Drinks Planner!</button>
                </h5>
            </div>
            <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordion">
                <div class="card-body">
                    <h2>
                        Wanna know how much <b>drinks</b> you'll most likely need for your party? Provide some information and we will tell you!
                    </h2>
                    <button id="drinksButton" class="btn col-md-4 btn-success" name="submit-button" value="DrinksAgent" type="submit">Tell
                        me!</button>
                    <c:if test="${not empty drinksMessage}">
                            ${drinksMessage}
                        <label for="beerAmount">Beer Amount:</label>
                        <input id="beerAmount" name="beerAmount" type="number" value="${beerAmount}" readonly />
                        <label for="wineAmount">Wine Amount:</label>
                        <input id="wineAmount" name="wineAmount" value="${wineAmount}" type="number" />
                        <label for="spiritsAmount">Spirits Amount:</label>
                        <input id="spiritsAmount" name="spiritsAmount" value="${spiritsAmount}" type="number" />
                        <label for="softsAmount">Softs Amount:</label>
                        <input id="softsAmount" name="softsAmount" value="${softsAmount}" type="number" />
                        <input id="adaptDrinksValuesCheckBox" name="adaptDrinksValuesCheckBox" value="0.0" type="checkbox"
                            onchange="adaptDrinksToPartyPlan()" />
                        <label for="adaptDrinksValuesCheckBox">Adapt for party plan?</label>
                    </c:if>
                </div>
            </div>
        </div>
    </form>
    <div class="card">
        <div class="card-header" id="headingThree">
            <h5 class="mb-0">
                <button type="button" class="btn col-md-12 btn-primary collapsed" data-toggle="collapse" data-target="#collapseFour"
                    aria-expanded="false" aria-controls="collapseThree">Party Planner!</button>
            </h5>
        </div>
        <div id="collapseFour" class="collapse" aria-labelledby="headingThree" data-parent="#accordion">
            <div class="card-body">
                <h2>
                    Wanna <b>create a wholesome</b> party plan? Provide some information and we will help you!
                </h2>
                <form role="form" action="CreateCaseServlet" method="post">
                    <label for="guestCountPlanner">Guest Count:</label> <input required id="guestCountPlanner" name="guestCountPlanner" type="number" />
                    <label for="totalBudgetPlanner">Total Budget:</label> <input required id="totalBudgetPlanner" name="totalBudgetPlanner"
                        type="number" value="0.0" readonly /> <label for="locationBudgetPlanner">Location Budget:</label> <input required
                        id="locationBudgetPlanner" name="locationBudgetPlanner" type="number" value="0.0" onchange="calculateTotalBudget()" /> <label
                        for="foodBudgetPlanner">Food Budget:</label> <input required id="foodBudgetPlanner" value="0.0" name="foodBudgetPlanner"
                        type="number" onchange="calculateTotalBudget()" /> <label for="drinksBudgetPlanner">Drinks Budget:</label> <input required
                        id="drinksBudgetPlanner" name="drinksBudgetPlanner" type="number" value="0.0" onchange="calculateTotalBudget()" /> <label
                        for="monthPlanner">Month:</label> <select id="monthPlanner" name="monthPlanner" class="form-control">
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
                    </select> <label for="yearPlanner">Year:</label><select id="yearPlanner" name="yearPlanner" class="form-control col-md-2" size="1">
                        <% for( int i=2000; i<=2050; i++) { %>
                        <option value="<%=i %>"><%= i %></option>
                        <% } %>
                    </select><label for="locationTypePlanner">Location Type:</label> <select id="locationTypePlanner" name="locationTypePlanner"
                        class="form-control">
                        <option value="private">private</option>
                        <option value="thirdparty">thirdparty</option>
                    </select> <label for="occasionPlanner">Party Occasion:</label> <select id="occasionPlanner" name="occasionPlanner" class="form-control">
                        <option value="anniversary">anniversary</option>
                        <option value="birthday">birthday</option>
                        <option value="wedding">wedding</option>
                        <option value="regular">regular</option>
                    </select> <label for="beerAmountPlanner">Beer Amount:</label> <input required id="beerAmountPlanner" name="beerAmountPlanner" type="number" value="0.0"/> <label
                        for="wineAmountPlanner">Wine Amount:</label> <input required id="wineAmountPlanner" name="wineAmountPlanner" type="number" value="0.0" /> <label
                        for="spiritsAmountPlanner">Spirits Amount:</label> <input required id="spiritsAmountPlanner" name="spiritsAmountPlanner" type="number" value="0.0"/>
                    <label for="softsAmountPlanner">Softs Amount:</label> <input required id="softsAmountPlanner" name="softsAmountPlanner" type="number" value="0.0" /> <label
                        for="meatAmountPlanner">Meat Amount:</label> <input required id="meatAmountPlanner" name="meatAmountPlanner" type="number" value="0.0"/> <label
                        for="garnishAmountPlanner">Garnish Amount:</label> <input required id="garnishAmountPlanner" name="garnishAmountPlanner" type="number" value="0.0" />
                    <label for="snacksAmountPlanner">Snacks Amount:</label> <input required id="snacksAmountPlanner" name="snacksAmountPlanner" type="number" value="0.0" />
                    <button class="btn col-md-4 btn-success" type="submit">Save Party to Database!</button>
                </form>
            </div>
        </div>
        <c:if test="${not empty caseCreationMessage}">
                ${caseCreationMessage}
            </c:if>
    </div>
</body>
</html>