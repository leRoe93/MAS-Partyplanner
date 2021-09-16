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
                    <label for="totalBudget">Total Budget:</label>
                    <input id="totalBudget" name ="totalBudget" type="number" readonly />
                    <label for="locationBudget">Location Budget:</label>
                    <input id="locationBudget" name ="locationBudget" type="number" onchange="calculateTotalBudget()" />
                    <label for="foodBudget">Food Budget:</label>
                    <input id="foodBudget" name ="foodBudget" type="number" onchange="calculateTotalBudget()" />
                    <label for="drinksBudget">Drinks Budget:</label>
                    <input id="drinksBudget" name ="drinksBudget" type="number" onchange="calculateTotalBudget()"/>
                    
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
                    <input id="year" name ="year" type="number" />
                    
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
                    <input id="beerAmount" name ="beerAmount" type="number" />
                    <label for="wineAmount">Wine Amount:</label>
                    <input id="wineAmount" name ="wineAmount" type="number" />
                    
                    <label for="spiritsAmount">Spirits Amount:</label>
                    <input id="spiritsAmount" name ="spiritsAmount" type="number" />
                    
                    <label for="softsAmount">Softs Amount:</label>
                    <input id="softsAmount" name ="softsAmount" type="number" />
                    
                    <label for="meatAmount">Meat Amount:</label>
                    <input id="meatAmount" name ="meatAmount" type="number" />
                    <label for="garnishAmount">Garnish Amount:</label>
                    <input id="garnishAmount" name ="garnishAmount" type="number" />
                    <label for="snacksAmount">Snacks Amount:</label>
                    <input id="snacksAmount" name ="snacksAmount" type="number" />
                   
                    
                    <button class="btn col-md-12 btn-success" type="submit">Start Query!</button>
                </form>
        </div>
    </body>
</html>