<%@ page import="com.example.useraccount.JobPostings" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <!--  the title of the website and the css for styling -->
    <title>Jobify</title>
    <link rel="stylesheet" type="text/css" href="viewJobPostings.css">
</head>
<body>
<header>

    <!--  contains the website's name, home,profile,add jobs,interviews and About lists-->
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

<!-- In job section there is applicant information which includes the name,field,email that is added and store in the backend -->
<!--  Also with delete button to delete the profile -->
<section id="jobs">
    <div class="container">
        <h2>Student Information</h2>
        <div class="job-box">
            <p><strong>Name:</strong> ${studentInformation.getFirstName()} ${studentInformation.getLastName()}</p>
            <p><strong>Field of study:</strong> ${studentInformation.getFieldOfStudy()}</p>
            <p><strong>Email:</strong> ${studentInformation.getEmail()}</p>
            <form action="deleteUserServlet?studentEmail=${studentInformation.getEmail()}" method="post">
                <input type="submit" value="Delete User">
            </form>

        </div>
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


<%--String status = (String) request.getAttribute("status");
if (status.equals("Applied to")) {--%>

</body>
</html>

</body>
</html>
