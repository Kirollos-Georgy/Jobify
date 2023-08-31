<%@ page import="com.example.useraccount.StudentInformation" %>
<%@ page import="com.example.useraccount.EmployerInformation" %>
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
        <h2>Current Users</h2>
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
    ArrayList<StudentInformation> std = (ArrayList<StudentInformation>) request.getAttribute("students");
    for (StudentInformation students : std) {%>
<div class="job-box">
    <p><strong>Student</strong></p><br/>
    <p><strong>Name:</strong> <%=students.getFirstName()%> <%=students.getLastName()%>
    </p>
    <p><strong>Field of study:</strong> <%=students.getFieldOfStudy()%>
    </p>
    <p><strong>Email:</strong> <%=students.getEmail()%>
    </p>
    <br>
    <br>
    <label><a href="viewAStudentUserServlet?studentEmail=<%=students.getEmail()%>">View Profile<br></a></label><br>
</div>
<%}%>

<%
    ArrayList<EmployerInformation> std1 = (ArrayList<EmployerInformation>) request.getAttribute("employers");
    for (EmployerInformation employers : std1) {%>
<div class="job-box">
    <p><strong>Employer</strong></p><br/>
    <p><strong>Name:</strong> <%=employers.getFirstName()%> <%=employers.getLastName()%>
    </p>
    <p><strong>Company:</strong> <%=employers.getCompany()%>
    </p>
    <p><strong>Email:</strong> <%=employers.getEmail()%>
    </p>
    <br>
    <label><a href="viewAnEmployerUserServlet?employerEmail=<%=employers.getEmail()%>">View Profile<br></a></label><br>
</div>
</div>
<%}%>
</body>
</html>