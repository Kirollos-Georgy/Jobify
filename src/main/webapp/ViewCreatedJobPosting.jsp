<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title> Job Posting </title>
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

<head>
    <title> Job Posting </title>
    <link rel="stylesheet" type="text/css" href="StudentHomePage.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;600&display=swap" rel="stylesheet">
    <!--Stylesheet-->
    <style media="screen">

    </style>

</head>
<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>
<form>

    <body>

    <div class="job-box">
        <!-- display information of the job that has been added -->
        <h3>${jobPosting.getTitle()}</h3>
        <h3></h3>
        <p><strong style="color: blue;">
            Employer:</strong> ${jobPosting.getEmployerFirstName()} ${jobPosting.getEmployerLastName()}</p>
        <p><strong style="color: blue;"> Company:</strong> ${jobPosting.getCompany()}</p>
        <p><strong style="color: blue;"> Location:</strong> ${jobPosting.getJobLocation()} </p>
        <p><strong style="color: blue;"> Salary:</strong> ${jobPosting.getSalary()} </p>
        <p><strong style="color: blue;"> Deadline to apply:</strong> ${jobPosting.getDeadline()} </p>
        <p><strong style="color: blue;"> Description: </strong> ${jobPosting.getDescription()} </p>
    </div>
    <!-- button for View student applications  -->

    <button><a style="color:white;" href="viewStudentApplicationsServlet?jobPostingID=${id}&interview=${interview}">View
        student applications<br></a></button>

    <!-- button for editing job posting  -->


    <button><a style="color:white;" href="editJobPostingServlet?jobPostingID=${id}&interview=${interview}">Edit Job
        Posting<br></a></button>

    <!-- button for removing job posting  -->

    <button><a style="color:white;" href="removeJobPostingServlet?jobPostingID=${id}&userType=employer">Remove Job
        Posting<br></a></button>


    </body>
</form>
</body>
</html>