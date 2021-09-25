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
<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
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
	function showGif() {
		document.getElementById("loadingGif").style.display = "block";
	}
</script>
</head>
<body class="body">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class=" navbar-header">
                <a class="navbar-brand" href="#"> <img id="logo" src="img/MAS_Logo.png" class="d-inline-block align-top" alt="">
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
                <input id="id" name="id" class="form-control id1" type="text" />
                <p>
                    <button id="buttonFeedback" onclick="showGif()" class="btn" type="submit">Get Case!</button>
                </p>
            </form>
        </div>
    </div>
    <div class="container">
        <div id="loadingGif" class="text-center">
            <img class="loadingGifImage" src="img/loading.gif"></img>
            <p>Searching in database ...</p>
        </div>
        <c:if test="${not empty message}">
            <div class="microserviceTexts">${message}</div>
        </c:if>
        <c:if test="${not empty id}">
            <form role="form" action="AlterCaseServlet" method="post">
                <div class="row">
                    <div class="col-md-3">
                        <div class="thumbnail">
                            <img src="img/metadataBig.jpg" alt="...">
                            <div class="caption">
                                <h3>Metadata</h3>
                            </div>
                            <table class="table">
                                <tr>
                                    <th>Month</th>
                                    <th>Year</th>
                                </tr>
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
                                </tr>
                                <tr>
                                    <th colspan="2">Guest Count</th>
                                </tr>
                                <tr>
                                    <td colspan="2"><select id="guestCount" name="guestCount" class="form-control">
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
                                </tr>
                                <tr>
                                    <th colspan="2">Occasion</th>
                                </tr>
                                <tr>
                                    <td colspan="2"><select id="occasion" name="occasion" class="form-control">
                                            <option ${occasion=="anniversary"?"selected":""} value="anniversary">anniversary</option>
                                            <option ${occasion=="birthday"?"selected":""} value="birthday">birthday</option>
                                            <option ${occasion=="wedding"?"selected":""} value="wedding">wedding</option>
                                            <option ${occasion=="regular"?"selected":""} value="regular">regular</option>
                                    </select></td>
                                </tr>
                                <tr>
                                    <th colspan="2">Location Type</th>
                                </tr>
                                <tr>
                                    <td colspan="2"><select id="locationType" name="locationType" class="form-control">
                                            <option ${locationType=="private"?"selected":""} value="private">private</option>
                                            <option ${locationType=="thirdparty"?"selected":""} value="thirdparty">third party</option>
                                    </select></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="thumbnail">
                            <img src="img/budgetBig.jpg" alt="...">
                            <div class="caption">
                                <h3>Budgets</h3>
                            </div>
                            <table class="table">
                                <tr>
                                    <th>Total</th>
                                </tr>
                                <tr>
                                    <td><div class="input-group">
                                            <input class="form-control" id="totalBudget" name="totalBudget" type="number" step=".01" min="0" max="100000" value="${totalBudget}" readonly required /><span
                                                class="input-group-addon"
                                            >&#128;</span>
                                        </div></td>
                                </tr>
                                <tr>
                                    <th>Location</th>
                                </tr>
                                <tr>
                                    <td><div class="input-group">
                                            <input class="form-control" id="locationBudget" name="locationBudget" step=".01" min="0" max="20000" value="${locationBudget}" type="number"
                                                onchange="calculateTotalBudget()" required
                                            /><span class="input-group-addon">&#128;</span>
                                        </div></td>
                                </tr>
                                <tr>
                                    <th>Food</th>
                                </tr>
                                <tr>
                                    <td><div class="input-group">
                                            <input class="form-control" id="foodBudget" name="foodBudget" step=".01" min="0" max="30000" value="${foodBudget}" type="number" onchange="calculateTotalBudget()"
                                                required
                                            /><span class="input-group-addon">&#128;</span>
                                        </div></td>
                                </tr>
                                <tr>
                                    <th>Drinks</th>
                                </tr>
                                <tr>
                                    <td><div class="input-group">
                                            <input class="form-control" id="drinksBudget" name="drinksBudget" step=".01" min="0" max="50000" value="${drinksBudget}" type="number"
                                                onchange="calculateTotalBudget()" required
                                            /><span class="input-group-addon">&#128;</span>
                                        </div></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="thumbnail">
                            <img src="img/foodBig.jpg" alt="...">
                            <div class="caption">
                                <h3>Food</h3>
                            </div>
                            <table class="table">
                                <tr>
                                    <th>Meat Amount</th>
                                </tr>
                                <tr>
                                    <td><div class="input-group">
                                            <input class="form-control" id="meatAmount" name="meatAmount" type="number" step=".01" min="0" max="500" value="${meatAmount}" required /><span
                                                class="input-group-addon"
                                            >KG</span>
                                        </div></td>
                                </tr>
                                <tr>
                                    <th>Garnish Amount</th>
                                </tr>
                                <tr>
                                    <td><div class="input-group">
                                            <input class="form-control" id="garnishAmount" name="garnishAmount" step=".01" min="0" max="1000" value="${garnishAmount}" type="number" required /><span
                                                class="input-group-addon"
                                            >KG</span>
                                        </div></td>
                                </tr>
                                <tr>
                                    <th>Snacks Amount</th>
                                </tr>
                                <tr>
                                    <td><div class="input-group">
                                            <input class="form-control" id="snacksAmount" name="snacksAmount" step=".01" min="0" max="200" value="${snacksAmount}" type="number" required /><span
                                                class="input-group-addon"
                                            >KG</span>
                                        </div></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="thumbnail">
                            <img src="img/drinksBig.jpg" alt="...">
                            <div class="caption">
                                <h3>Drinks</h3>
                            </div>
                            <table class="table">
                                <tr>
                                    <th>Beer Amount</th>
                                </tr>
                                <tr>
                                    <td><div class="input-group">
                                            <input class="form-control" id="beerAmount" name="beerAmount" step=".01" min="0" max="1000" type="number" value="${beerAmount }" /><span class="input-group-addon">L</span>
                                        </div>
                                </tr>
                                <tr>
                                    <th>Wine Amount</th>
                                </tr>
                                <tr>
                                    <td><div class="input-group">
                                            <input class="form-control" id="wineAmount" name="wineAmount" step=".01" min="0" max="1000" value="${wineAmount}" type="number" /><span class="input-group-addon">L</span>
                                        </div></td>
                                </tr>
                                <tr>
                                    <th>Spirits Amount</th>
                                </tr>
                                <tr>
                                    <td><div class="input-group">
                                            <input class="form-control" id="spiritsAmount" name="spiritsAmount" step=".01" min="0" max="500" value="${spiritsAmount}" type="number" /><span
                                                class="input-group-addon"
                                            >L</span>
                                        </div></td>
                                </tr>
                                <tr>
                                    <th>Softs Amount</th>
                                </tr>
                                <tr>
                                    <td><div class="input-group">
                                            <input class="form-control" id="softsAmount" name="softsAmount" step=".01" min="0" max="5000" value="${softsAmount}" type="number" /><span class="input-group-addon">L</span>
                                        </div></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <button id="verifyPartyButton" class="btn btn-block" type="submit">Verify Party Information!</button>
            </form>
        </c:if>
    </div>
    <footer class="text-center text-white navbar-fixed-bottom">
        <span>Visit MAS-Partyplanner on GitHub!</span> <a class="btn btn-outline-light btn-floating m-1" href="https://github.com/leRoe93/MAS-PartyPlanner" role="button"><i
            class="fa fa-github fa-2x"
        ></i></a> © 2021 <a href="https://www.uni-hildesheim.de/">University of Hildesheim</a>
    </footer>
</body>
</html>