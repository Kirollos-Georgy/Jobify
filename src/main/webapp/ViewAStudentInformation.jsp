<%@ page import="com.example.useraccount.JobPostings" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>


    <!--the title of the website and the css for styling-->

    <title>Jobify</title>
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
                <li><a href="../resources/templates/AddJobPosting.html">Add Jobs </a></li>
                <li><a href="viewCreatedJobPostingsServlet?interview=true">Interviews</a></li>
                <li><a href="AboutPage.jsp?account=employer">About</a></li>
            </ul>
        </nav>
    </div>
</header>


<!--In job section there is applicant information which includes the name,field,email that is added and store in the backend
 Also with submit button when the student is selected for an interview-->

<section id="jobs">
    <div class="container">
        <h2>Applicant Information</h2>
        <div class="job-box">
            <h2>${jobTitle}</h2>
            <p><strong>Name:</strong> ${studentInformation.getFirstName()} ${studentInformation.getLastName()}</p>
            <p><strong>Field of study:</strong> ${studentInformation.getFieldOfStudy()}</p>
            <p><strong>Email:</strong> ${studentInformation.getEmail()}</p>
            <a href="data:application/pdf;base64,${studentInformation.getResumeBase64()}" target="_blank">Resume</a><br>
            <a href="data:application/pdf;base64,${studentInformation.getCoverLetterBase64()}" target="_blank">Cover
                Letter</a><br>
            <a href="data:application/pdf;base64,${studentInformation.getTranscriptBase64()}"
               target="_blank">Transcript</a><br>
            <form action="selectStudentForInterviewServlet?jobPostingID=${jobPostingID}&studentEmail=${studentInformation.getEmail()}"
                  method="post">
                <input type="submit" value="Select for an interview">
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