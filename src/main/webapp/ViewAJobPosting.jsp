<%@ page import="com.example.useraccount.JobPostings" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!-- View all the job postings in order to apply for jobs. -->
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Job Posting Website</title>
    <link rel="stylesheet" type="text/css" href="viewJobPostings.css">
</head>
<body>
<header>
    <div class="container">
        <h1>Jobify</h1>
        <nav>
            <ul>
                <li><a href="viewJobPostingsServlet">Home</a></li>
                <li><a href="viewUserProfileServlet">Profile</a></li>
                <li><a href="viewApplicationsServlet">Applications</a></li>
                <li><a href="viewInterviewsServlet">Interviews</a></li>
                <li><a href="AboutPage.jsp?account=student">About</a></li>
            </ul>
        </nav>
    </div>
</header>

<section id="search">
    <div class="container">
        <h2>Find your dream job today</h2>
        <form>
            <input type="text" placeholder="Search by keyword or location">
            <button type="submit">Search</button>
        </form>
    </div>
</section>

<section id="jobs">
    <div class="container">
        <h2>Featured Jobs</h2>

    </div>
</section>


<title>Job Postings</title>
<style>
    .job-box {
        border: 1px solid black;
        padding: 10px;
        margin-bottom: 10px;
    }

    input[type = submit] {
        background-color: #333;
        border: none;
        text-decoration: none;
        color: #fff;;
        padding: 10px 20px;
        margin: 20px 20px;
        cursor: pointer;
    }
</style>
</head>
<body>

<div class="job-box">
    <h2>${jobPosting.getTitle()}</h2>
    <p><strong>Employer:</strong> ${jobPosting.getEmployerFirstName()} ${jobPosting.getEmployerLastName()}</p>
    <p><strong>Company:</strong> ${jobPosting.getCompany()}</p>
    <p><strong>Status:</strong>${jobPosting.getStatus()}</p>
    <p><strong>Location:</strong> ${jobPosting.getJobLocation()} </p>
    <p><strong>Salary:</strong> ${jobPosting.getSalary()} </p>
    <p><strong>Deadline to apply:</strong> ${jobPosting.getDeadline()} </p>
    <p>${jobPosting.getDescription()} </p>
</div>

<form action="../resources/templates/ApplyForAJobPosting.html" method="post">
    <input type="submit" value="Apply">
</form>
</body>
</html>

</body>
</html>

