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
	window.onload = function() {
		fillInDefaults();
		checkStorage();
	}

	function fillInDefaults() {
		var itemIds = [ "foodBudget", "drinksBudget", "locationBudget",
				"totalBudget", "meatAmount", "garnishAmount", "snacksAmount",
				"foodBudget_Specific", "beerAmount", "wineAmount",
				"spiritsAmount", "softsAmount", "drinksBudget_Specific" ];
		for (var i = 0; i < itemIds.length; i++) {
			if (document.getElementById(itemIds[i]).value == null
					|| document.getElementById(itemIds[i]).value == "") {
				document.getElementById(itemIds[i]).value = "0.00"
			}
		}
	}

	function calculateTotalBudget() {

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

	function syncLocationType() {
		document.getElementById('locationTypePlanner').value = document
				.getElementById('locationType').value
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
			document.getElementById('foodBudgetPlanner').value = document
					.getElementById('foodBudget_Specific').value
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
			document.getElementById('drinksBudgetPlanner').value = document
					.getElementById('drinksBudget_Specific').value
		}
	}

	function toggleEmailInput() {

		var checkBox = document.getElementById('sendMailCheckBox')
		if (checkBox.checked == true) {
			document.getElementById('email').style.visibility = "visible";
		} else {
			document.getElementById('email').style.visibility = "hidden";
		}
	}
	function checkStorage() {
		if (localStorage.getItem("#collapseOne") != null) {
			$("#collapseOne").toggle();//to show panel 
		}
		if (localStorage.getItem("#collapseTwo") != null) {
			$("#collapseTwo").toggle();//to show panel 
		}
		if (localStorage.getItem("#collapseThree") != null) {
			$("#collapseThree").toggle();//to show panel 
		}
		if (localStorage.getItem("#collapseFour") != null) {
			$("#collapseFour").toggle();//to show panel 
		}
	    localStorage.clear();

	}
	function save(el) {
		var target = el.getAttribute("data-target")
		localStorage.setItem(target, target)
	}
</script>
</head>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">
                	<img src="img/MAS_Logo.png" class="d-inline-block align-top" alt="">
                </a>
            </div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="index.jsp">Home</a></li>
                <li><a href="feedback.jsp">Feedback Page</a></li>
            </ul>
        </div>
    </nav>
    <div class="jumbotron jumbotron-fluid text-center">
        <div class="page-header row">
           <div class="col">
              	<h1 class="display-4">
          			 Welcome to the <b>MAS-Partyplanner!</b>
        		</h1>
        	</div>
   		</div>
   		<div class="container text-center align-items-center">
       			 <p>Want to organize a party, but don't know how to do it? Benefit from other's experiences!</p>
    	</div>
    </div>
    
    
    
    
    <div class="container">
        <div class="accordion" id="agentAccordion">
            <form role="form" action="QueryServlet" method="post">
                <div id="metaInformation">
                    <h2>To use our services, we need some initial meta information for your planned party!</h2>
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">Month</th>
                                <th scope="col">Year</th>
                                <th scope="col">Expected Guests</th>
                                <th scope="col">Occasion</th>
                                <th scope="col">Location Type</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><select id="month" name="month" class="form-control" onchange="syncMonth()">
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
                                <td><select id="year" name="year" class="form-control" onchange="syncYear()">
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
                                <td><select id="guestCount" name="guestCount" class="form-control" onchange="syncGuestCount()">
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
                                <td><select id="occasion" name="occasion" onchange="syncOccasion()" class="form-control">
                                        <option ${occasion=="anniversary"?"selected":""} value="anniversary">anniversary</option>
                                        <option ${occasion=="birthday"?"selected":""} value="birthday">birthday</option>
                                        <option ${occasion=="wedding"?"selected":""} value="wedding">wedding</option>
                                        <option ${occasion=="regular"?"selected":""} value="regular">regular</option>
                                </select></td>
                                <td><select id="locationType" name="locationType" class="form-control" onchange="syncLocationType()">
                                        <option ${locationType=="private"?"selected":""} value="private">private</option>
                                        <option ${locationType=="thirdparty"?"selected":""} value="thirdparty">third party</option>
                                </select></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="card">
                    <div class="card-header" id="headingOne">
                        <h2 class="mb-0">
                            <button id="buttonBudget" class="btn btn-block collapsed" onClick="save(this)" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true"
                                aria-controls="collapseOne"
                            >Budget Planner</button>
                        </h2>
                    </div>
                    <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#agentAccordion">
                        <div class="card-body">
                            <c:if test="${not empty messageBudgetAgent}">
                                ${messageBudgetAgent}
                            </c:if>
                            <c:if test="${not empty normalizedBudgetAgent}">
                                ${normalizedBudgetAgent}
                            </c:if>
                            <c:if test="${not empty detailsBudgetAgent}">
                                ${detailsBudgetAgent}
                            </c:if>
                            <h2>
                                Wanna know how much <b>money</b> you'll most likely need for your party? Provide some information and we will tell you!
                            </h2>
                            <button id="budgetButton" class="btn col-md-4 btn-success" name="submit-button" value="BudgetAgent" type="submit">Tell me!</button>
                            <input id="adaptBudgetValuesCheckBox" name="adaptBudgetValuesCheckBox" value="0.0" type="checkbox" onchange="adaptBudgetsToPartyPlan()" /> <label
                                for="adaptBudgetValuesCheckBox"
                            >Adapt for party plan?</label>
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
                                        <td><input id="totalBudget" name="totalBudget" type="number" value="${totalBudget}" readonly /></td>
                                        <td><input id="locationBudget" name="locationBudget" value="${locationBudget}" type="number" onchange="calculateTotalBudget()" /></td>
                                        <td><input id="foodBudget" name="foodBudget" value="${foodBudget}" type="number" onchange="calculateTotalBudget()" /></td>
                                        <td><input id="drinksBudget" name="drinksBudget" value="${drinksBudget}" type="number" onchange="calculateTotalBudget()" /></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="card">
                    <div class="card-header" id="headingTwo">
                        <h2 class="mb-0">
                            <button id="buttonFood" class="btn btn-block collapsed" type="button" onClick="save(this)" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">Food
                                Planner</button>
                        </h2>
                    </div>
                    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#agentAccordion">
                        <div class="card-body">
                            <c:if test="${not empty messageFoodAgent}">
                                ${messageFoodAgent}
                            </c:if>
                            <c:if test="${not empty normalizedFoodAgent}">
                                ${normalizedFoodAgent}
                            </c:if>
                            <c:if test="${not empty detailsFoodAgent}">
                                ${detailsFoodAgent}
                            </c:if>
                            <h2>
                                Wanna know how much <b>food</b> you'll most likely need for your party? Provide some information and we will tell you!
                            </h2>
                            <button id="foodButton" class="btn col-md-4 btn-success" name="submit-button" value="FoodAgent" type="submit">Tell me!</button>
                            <input id="adaptFoodValuesCheckBox" name="adaptFoodValuesCheckBox" value="0.0" type="checkbox" onchange="adaptFoodToPartyPlan()" /> <label for="adaptFoodValuesCheckBox">Adapt
                                for party plan?</label>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">Meat Amount</th>
                                        <th scope="col">Garnish Amount</th>
                                        <th scope="col">Snacks Amount</th>
                                        <th scope="col">Food Budget</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td><input id="meatAmount" name="meatAmount" type="number" value="${meatAmount}" /></td>
                                        <td><input id="garnishAmount" name="garnishAmount" value="${garnishAmount}" type="number" /></td>
                                        <td><input id="snacksAmount" name="snacksAmount" value="${snacksAmount}" type="number" /></td>
                                        <td><input id="foodBudget_Specific" name="foodBudget_Specific" value="${foodBudget_Specific}" type="number" /></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="card">
                    <div class="card-header" id="headingThree">
                        <h2 class="mb-0">
                            <button id="buttonDrinks" class="btn btn-block collapsed" type="button" onClick="save(this)" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">Drinks
                                Planner</button>
                        </h2>
                    </div>
                    <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#agentAccordion">
                        <div class="card-body">
                            <c:if test="${not empty messageDrinksAgent}">
                                ${messageDrinksAgent}
                            </c:if>
                            <c:if test="${not empty normalizedDrinksAgent}">
                                ${normalizedDrinksAgent}
                            </c:if>
                            <c:if test="${not empty detailsDrinksAgent}">
                                ${detailsDrinksAgent}
                            </c:if>
                            <h2>
                                Wanna know how much <b>drinks</b> you'll most likely need for your party? Provide some information and we will tell you!
                            </h2>
                            <button id="drinksButton" class="btn col-md-4 btn-success" name="submit-button" value="DrinksAgent" type="submit">Tell me!</button>
                            <input id="adaptDrinksValuesCheckBox" name="adaptDrinksValuesCheckBox" value="0.0" type="checkbox" onchange="adaptDrinksToPartyPlan()" /> <label
                                for="adaptDrinksValuesCheckBox"
                            >Adapt for party plan?</label>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">Beer Amount</th>
                                        <th scope="col">Wine Amount</th>
                                        <th scope="col">Spirits Amount</th>
                                        <th scope="col">Softs Amount</th>
                                        <th scope="col">Drinks Budget</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td><input id="beerAmount" name="beerAmount" type="number" value="${beerAmount}" /></td>
                                        <td><input id="wineAmount" name="wineAmount" value="${wineAmount}" type="number" /></td>
                                        <td><input id="spiritsAmount" name="spiritsAmount" value="${spiritsAmount}" type="number" /></td>
                                        <td><input id="softsAmount" name="softsAmount" value="${softsAmount}" type="number" /></td>
                                        <td><input id="drinksBudget_Specific" name="foodBudget_Specific" value="${drinksBudget_Specific}" type="number" /></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </form>
            <div class="card">
                <div class="card-header" id="headingFour">
                    <h2 class="mb-0">
                        <button id="buttonParty" class="btn btn-block collapsed" type="button" onClick="save(this)" data-toggle="collapse" data-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">Party
                            Planner</button>
                    </h2>
                </div>
                <div id="collapseFour" class="collapse" aria-labelledby="headingFour" data-parent="#agentAccordion">
                    <div class="card-body">
                        <c:if test="${not empty caseCreationMessage}">
                                 ${caseCreationMessage}
                                 <c:if test="${not empty caseId}">
                            </c:if>
                        </c:if>
                        <form role="form" action="CreateCaseServlet" method="post">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">Month</th>
                                        <th scope="col">Year</th>
                                        <th scope="col">Expected Guests</th>
                                        <th scope="col">Occasion</th>
                                        <th scope="col">Location Type</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td><select id="monthPlanner" name="monthPlanner" class="form-control">
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
                                        </select></td>
                                        <td><select id="yearPlanner" name="yearPlanner" class="form-control">
                                                <%
                                                    for (int i = 2000; i <= 2050; i++) {
                                                %>
                                                <option value="<%=i%>"><%=i%></option>
                                                <%
                                                    }
                                                %>
                                        </select></td>
                                        <td><select id="guestCountPlanner" name="guestCountPlanner" class="form-control">
                                                <%
                                                    for (int i = 1; i <= 1000; i++) {
                                                %>
                                                <option value="<%=i%>"><%=i%></option>
                                                <%
                                                    }
                                                %>
                                        </select></td>
                                        <td><select id="occasionPlanner" name="occasionPlanner" class="form-control">
                                                <option value="anniversary">anniversary</option>
                                                <option value="birthday">birthday</option>
                                                <option value="wedding">wedding</option>
                                                <option value="regular">regular</option>
                                        </select></td>
                                        <td><select id="locationTypePlanner" name="locationTypePlanner" class="form-control">
                                                <option value="private">private</option>
                                                <option value="thirdparty">third party</option>
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
                                        <td><input id="totalBudgetPlanner" name="totalBudgetPlanner" type="number" value="0.0" readonly required /></td>
                                        <td><input id="locationBudgetPlanner" name="locationBudgetPlanner" value="0.0" type="number" onchange="calculateTotalBudget()" required /></td>
                                        <td><input id="foodBudgetPlanner" name="foodBudgetPlanner" value="0.0" type="number" onchange="calculateTotalBudget()" required /></td>
                                        <td><input id="drinksBudgetPlanner" name="drinksBudgetPlanner" value="0.0" type="number" onchange="calculateTotalBudget()" required /></td>
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
                                        <td><input id="meatAmountPlanner" name="meatAmountPlanner" type="number" value="0.0" required /></td>
                                        <td><input id="garnishAmountPlanner" name="garnishAmountPlanner" value="0.0" type="number" required /></td>
                                        <td><input id="snacksAmountPlanner" name="snacksAmountPlanner" value="0.0" type="number" required /></td>
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
                                        <td><input id="beerAmountPlanner" name="beerAmountPlanner" type="number" value="0.0" /></td>
                                        <td><input id="wineAmountPlanner" name="wineAmountPlanner" value="0.0" type="number" /></td>
                                        <td><input id="spiritsAmountPlanner" name="spiritsAmountPlanner" value="0.0" type="number" /></td>
                                        <td><input id="softsAmountPlanner" name="softsAmountPlanner" value="0.0" type="number" /></td>
                                    </tr>
                                </tbody>
                            </table>
                            <button class="btn col-md-4 btn-success" type="submit">Save Party to Database!</button>
                            <label for="sendMailCheckBox">Shall we drop you an eMail with your party details?</label> <input id="sendMailCheckBox" name="sendMailCheckBox" type="checkbox"
                                onchange="toggleEmailInput()"
                            /> <input id="email" name="email" type="email" placeholder="your.mail@someMail.com" style="visibility: hidden;" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>