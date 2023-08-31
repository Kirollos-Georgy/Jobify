<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>About us</title>
</head>
<body>
<header>
    <!-- Include the AboutPage.css file for styling -->
    <link rel="stylesheet" type="text/css" href="AboutPage.css">
    <div class="container">
        <h1>Jobify</h1>
        <nav>
            <ul>
                <% String account = request.getParameter("account");

                    /*Check if the user is a student or employer and display appropriate links */

                    if (account != null && account.equals("student")) {
                %>
                <li><a href="viewJobPostingsServlet">Home</a></li>
                <li><a href="viewUserProfileServlet">Profile</a></li>
                <li><a href="viewApplicationsServlet">Applications</a></li>
                <li><a href="viewInterviewsServlet">Interviews</a></li>
                <li><a href="AboutPage.jsp?account=student">About</a></li>
                <%} else if (account != null && account.equals("employer")) {%>
                <li><a href="viewCreatedJobPostingsServlet?interview=false">Home</a></li>
                <li><a href="viewEmployerProfileServlet">Profile</a></li>
                <li><a href="../resources/templates/AddJobPosting.html">Add Jobs </a></li>
                <li><a href="viewCreatedJobPostingsServlet?interview=true">Interviews</a></li>
                <li><a href="AboutPage.jsp?account=employer">About</a></li>
                <%} else {%>

                <li><a href="index.jsp">Home</a></li>
                <li><a href="login.jsp">Login</a></li>
                <li><a href="register.jsp">Register</a></li>
                <li><a href="AboutPage.jsp">About</a></li>
                <%}%>
            </ul>
        </nav>
    </div>
</header>

</body>

<!-- add the white screen in the middle that will have all the information for the about page as feedback and the star	rating  -->
<head>
    <title>About us</title>
    <link rel="stylesheet" type="text/css" href="EmployerHomePage.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;600&display=swap" rel="stylesheet">
    <!--Stylesheet-->
    <style media="screen">

    </style>


</head>


<body>
<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>

<form method="post" action="addFeedbackServlet?userType=<%=request.getParameter("account")%>">
    <h3 style="color:#f09819;">About </h3>
    <link rel="stylesheet" type="text/css" href="AboutPage.css">


    <div class="form-group">

        <!-- add the text area to let the user enter their feedback -->

        <h2> Feedback </h2>
        <textarea id="subject" name="subject" placeholder="Write your feedback" style="height:200px"></textarea>


        <!-- add 5 starts for rating -->
        <div class="rate">
            <h2> Star Rating </h2>
            <input type="radio" id="star5" name="rate" value="5"/>
            <label for="star5" title="text">5 stars</label>
            <input type="radio" id="star4" name="rate" value="4"/>
            <label for="star4" title="text">4 stars</label>
            <input type="radio" id="star3" name="rate" value="3"/>
            <label for="star3" title="text">3 stars</label>
            <input type="radio" id="star2" name="rate" value="2"/>
            <label for="star2" title="text">2 stars</label>
            <input type="radio" id="star1" name="rate" value="1"/>
            <label for="star1" title="text">1 star</label>

            <div class="form-group">
                <button type="submit" class="submit-btn">Submit</button>
            </div>

        </div>
</form>
</body>
</html>

