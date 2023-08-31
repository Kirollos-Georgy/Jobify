<%@ page import="com.example.useraccount.StudentInformation" %>
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
            <ul>
                <li><a href="viewCreatedJobPostingsServlet?interview=false">Home</a></li>
                <li><a href="viewEmployerProfileServlet">Profile</a></li>
                <li><a href="../resources/templates/AddJobPosting.html">Add Jobs</a></li>
                <li><a href="viewCreatedJobPostingsServlet?interview=true">Interviews</a></li>
                <li><a href="AboutPage.jsp?account=employer">About</a></li>
            </ul>
        </nav>
    </div>
</header>

<section id="search">
    <div class="container">
        <h2>Find your dream job today</h2>

    </div>
</section>

<section id="jobs">
    <div class="container">
        <h2>Applicants List</h2>
    </div>
</section>

<title>Job Postings</title>
<style>
    .job-box {
        border: 1px solid black;
        padding: 10px;
        margin-bottom: 10px;
    }
</style>
</head>
<!--  -->
<body>
<%
    ArrayList<StudentInformation> std = (ArrayList<StudentInformation>) request.getAttribute("studentInformation");
    for (StudentInformation studentInformation : std) {%>
<div class="job-box">
    <p><strong>Name:</strong> <%=studentInformation.getFirstName()%> <%=studentInformation.getLastName()%>
    </p>
    <p><strong>Field of study:</strong> <%=studentInformation.getFieldOfStudy()%>
    </p>
    <p><strong>Email:</strong> <%=studentInformation.getEmail()%>
    </p>
    <br>
    <label><a
            href="viewAStudentInformationServlet?studentEmail=<%=studentInformation.getEmail()%>&jobPostingID=${jobPostingID}">View
        student full information<br></a></label><br>
</div>
<%}%>
</body>
</html>

</body>
</html>
