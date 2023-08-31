<%@ page import="com.example.useraccount.JobPostings" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title> Job Postings </title>
    <link rel="stylesheet" type="text/css" href="EmployerHomePage.css">
</head>
<body>
<header>
    <div class="container">
        <h1>Jobify</h1>
        <nav>
            <ul>
                <li><a href="viewCreatedJobPostingsServlet?interview=false">Home</a></li>
                <li><a href="viewEmployerProfileServlet">Profile</a></li>
                <li><a href="../resources/templates/AddJobPosting.html">Add Jobs </a></li>
                <li><a href="viewCreatedJobPostingsServlet?interview=true">Interviews</a></li>
                <li><a href="AboutPage.jsp?account=employer">About</a></li>
            </ul>
        </nav>
    </div>
</header>

<head>
    <title> Job Postings </title>
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
<form method="get" action="viewCreatedJobPostingsServlet">

    <h3 style="color:#f09819;">Job Postings </h3>
    <link rel="stylesheet" type="text/css" href="index.css">


    <table>
        <tr>

        </tr>

        <%
            ArrayList<JobPostings> std = (ArrayList<JobPostings>) request.getAttribute("jobPostings");
            for (JobPostings jobPosting : std) {%>
        <tr>
            <td><label><a
                    href="viewAJobPostingServlet?id=<%=jobPosting.getId()%>&interview=${interview}&userType=Employer"><%=jobPosting.getTitle()%>
            </a></label>
            </td>
        </tr>
        <%}%>
    </table>
</form>
</body>
</html>

















