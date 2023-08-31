<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Edit employer profile</title>
    <link rel="stylesheet" type="text/css" href="../../../target/UserAccount-1.0-SNAPSHOT/CreatingEmployerProfile.css">

</head>
<body>
<header>
    <div class="container">
        <h1>Jobify Website</h1>
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


    <head>
        <title>Edit employer profile</title>
        <link rel="stylesheet" type="text/css" href="CreatingEmployerProfile.css">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;600&display=swap" rel="stylesheet">
        <!--Stylesheet-->


    </head>


<body>
<div class="background">


</div>
<form method="post" action="editEmployerProfileServlet">
    <h3 style="color:black;">Edit Employer Profile</h3>
    <div class="container">
        <br/>
        <form method="post" action="editEmployerProfileServlet">
            <div class="form-group">
                <label for="first-name">First Name:</label>
                <input type="text" id="first-name" name="first-name" value="${employerInformation.getFirstName()}"
                       required>
            </div>
            <div class="form-group">
                <label for="last-name">Last Name:</label>
                <input type="text" id="last-name" name="last-name" value="${employerInformation.getLastName()}"
                       required>
            </div>
            <div class="form-group">
                <label for="last-name">Company:</label>
                <input type="text" id="company" name="company" value="${employerInformation.getCompany()}" required>
            </div>
            <button type="submit" class="submit-btn">Save</button>

    </div>
    <script src="script.js"></script>
</form>

</div>

</body>
</html>
