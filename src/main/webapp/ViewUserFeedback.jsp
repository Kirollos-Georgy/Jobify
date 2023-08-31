<%@ page import="com.example.useraccount.FeedbackForm" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title> Jobify </title>
    <link rel="stylesheet" type="text/css" href="viewJobPostings.css">
</head>
<body>
<header>
    <div class="container">
        <h1>Jobify</h1>
        <nav>
            <ul> <!-- Change the pages -->
                <li><a href="viewAdminProfileServlet">Profile</a></li>
                <li><a href="viewCurrentUsersServlet">Current Users </a></li>
                <li><a href="viewUserFeedbackServlet">User Feedback</a></li>
                <li><a href="viewJobPostingsAdminServlet">Job Postings</a></li>
            </ul>
        </nav>
    </div>
</header>

<section id="jobs">
    <div class="container">
        <h2>User Feedback</h2>
    </div>
</section>


<style>
    .job-box {
        border: 1px solid black;
        padding: 10px;
        margin-bottom: 10px;
    }
</style>


</head>
<body>
<style>
    .job-box {
        border: 1px solid black;
        padding: 10px;
        margin-bottom: 10px;
    }
</style>
<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>

<%
    ArrayList<FeedbackForm> std = (ArrayList<FeedbackForm>) request.getAttribute("feedbackForms");
    for (FeedbackForm feedbackForms : std) {%>

<div class="job-box">
    <p><strong>Feedback</strong></p><br/>
    <p><%=feedbackForms.getSubject()%>
    </p><br/>
    <p><strong>Rating:</strong> <%=feedbackForms.getRating()%>/5</p>
    <p><strong>Email:</strong> <%=feedbackForms.getEmail()%>
    </p>
    <br>
</div>
<%}%>

</body>
</html>