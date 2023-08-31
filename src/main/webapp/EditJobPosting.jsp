<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Edit job posting</title>
    <link rel="stylesheet" type="text/css" href="AddJobPosting.css">
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
    <title>Edit job posting</title>
    <link rel="stylesheet" type="text/css" href="AddJobPosting.css">
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
<form method="post" action="editJobPostingServlet">
    <table>
        <h3 style="color:black"> Edit Job Posting </h3>
        <tr>
            <td>
                <div class="form-group">
                    <label for="positionTitle">Position title:</label>
                    <input name="positionTitle" id="positionTitle" value="${jobPosting.getTitle()}" required/>
                </div>
            </td>
        </tr>
        <tr>
        <tr>
            <td>
                <div class="form-group">
                    <label for="jobLocation">Job location:</label>
                    <textarea rows="5" cols="60" name="jobLocation" id="jobLocation"
                              required>${jobPosting.getJobLocation()}</textarea>
                </div>
            </td>
        </tr>
        <tr>
        <tr>
            <td>
                <div class="form-group">
                    <label for="salary">Salary:</label>
                    <input name="salary" id="salary" value="${jobPosting.getSalary()}" required/>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="form-group">
                    <label for="deadline">Deadline to apply:</label>
                    <input name="deadline" id="deadline" value="${jobPosting.getDeadline()}" required/>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="form-group">
                    <label for="description">Job posting details:</label>
                    <textarea rows="5" cols="60" name="description" id="description"
                              required>${jobPosting.getDescription()}</textarea>
                </div>
            </td>
        </tr>
        <td>
            <button type="submit" class="submit-btn">Save</button>
        </td>
        </tr>
    </table>
</form>
</body>
</html>
