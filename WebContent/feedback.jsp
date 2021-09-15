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
            
            <c:if test="${not empty id}">
                <form role="form" action="AlterCaseServlet" method="post">
                    <label for="id">Party ID:</label>
                    <input id="id" name ="id" type="text" value="${id}" readonly />
                    <label for="verified">Verified:</label>
                    <input id="verified" name ="verified" type="text" value="${verified}" readonly />
                    
                    <label for="guestCount">Guest Count:</label>
                    <input id="guestCount" name ="guestCount" type="number" value="${guestCount}" />
                    
                    <label for="totalBudget">Total Budget:</label>
                    <input id="totalBudget" name ="totalBudget" type="number" value="${totalBudget}" />
                    <label for="locationBudget">Location Budget:</label>
                    <input id="locationBudget" name ="locationBudget" type="number" value="${locationBudget}" />
                    <label for="foodBudget">Food Budget:</label>
                    <input id="foodBudget" name ="foodBudget" type="number" value="${foodBudget}" />
                    <label for="drinksBudget">Drinks Budget:</label>
                    <input id="drinksBudget" name ="drinksBudget" type="number" value="${drinksBudget}" />
                    
                    <label for="month">Month:</label>
                    <select id="month" name="month" class="form-control">
                        <option value="${month }" selected hidden>${month}</option>
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
                    <input id="year" name ="year" type="number" value="${year}" />
                    
                    <label for="locationType">Location Type:</label>
                    <select id="locationType" name="locationType" class="form-control">
                        <option value="${locationType }" selected hidden>${locationType}</option>
                        <option value="private">private</option>
                        <option value="thirdparty">thirdparty</option>
                    </select>
                    
                    <label for="beerAmount">Beer Amount:</label>
                    <input id="beerAmount" name ="beerAmount" type="number" value="${beerAmount}" />
                    <label for="wineAmount">Wine Amount:</label>
                    <input id="wineAmount" name ="wineAmount" type="number" value="${wineAmount}" />
                    
                    <label for="spiritsAmount">Spirits Amount:</label>
                    <input id="spiritsAmount" name ="spiritsAmount" type="number" value="${spiritsAmount}" />
                    
                    <label for="softsAmount">Softs Amount:</label>
                    <input id="softsAmount" name ="softsAmount" type="number" value="${softsAmount}" />
                    
                    <label for="meatAmount">Meat Amount:</label>
                    <input id="meatAmount" name ="meatAmount" type="number" value="${meatAmount}" />
                    <label for="garnishAmount">Garnish Amount:</label>
                    <input id="garnishAmount" name ="garnishAmount" type="number" value="${garnishAmount}" />
                    <label for="snacksAmount">Snacks Amount:</label>
                    <input id="snacksAmount" name ="snacksAmount" type="number" value="${snacksAmount}" />
                   
                    
                    <button class="btn col-md-12 btn-success" type="submit">Verify Party Information!</button>
                </form>
            </c:if>

        </div>
    </body>
</html>