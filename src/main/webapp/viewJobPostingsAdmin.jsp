<%@ page import="com.example.useraccount.JobPostings" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Jobify</title>
    <link rel="stylesheet" type="text/css" href="viewJobPostings.css">
</head>
<body>
<header>
    <div class="container">
        <h1>Jobify</h1>
        <nav>
            <ul>
                <li><a href="viewAdminProfileServlet">Profile</a></li>
                <li><a href="viewCurrentUsersServlet">Current Users </a></li>
                <li><a href="viewUserFeedbackServlet">User Feedback</a></li>
                <li><a href="viewJobPostingsAdminServlet">Job Postings</a></li>
            </ul>
        </nav>
    </div>
</header>

<section id="search">
    <div class="container">
        <form method="post" action="viewJobPostingsAdminServlet">
            <input type="text" placeholder="Search by job title" name="search">
            <button type="submit">Search</button>
        </form>
    </div>
</section>

<section id="jobs">
    <div class="container">
        <h2>Job Postings</h2>

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
<body>

<%
    ArrayList<JobPostings> std = (ArrayList<JobPostings>) request.getAttribute("jobPostings");
    for (JobPostings jobPosting : std) {%>
<div class="job-box">
    <h2><a href="viewAJobPostingServlet?id=<%=jobPosting.getId()%>&userType=Admin"><%=jobPosting.getTitle()%>
    </a></h2>
    <p><strong>Company:</strong> <%=jobPosting.getCompany()%>
    </p>
    <p><strong>Status:</strong> <%=jobPosting.getStatus()%>
    </p>
    <p><strong>Location:</strong> <%=jobPosting.getJobLocation()%>
    </p>
    <p><strong>Salary:</strong> <%=jobPosting.getSalary()%>
    </p>
    <p><strong>Deadline to apply:</strong> <%=jobPosting.getDeadline()%>
    </p>
    <p><%=jobPosting.getDescription()%>
    </p>
</div>
<%}%>
</body>
</html>

</body>
</html>

