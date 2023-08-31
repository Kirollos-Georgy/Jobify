<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Edit admin profile</title>
    <link rel="stylesheet" type="text/css" href="../../../target/UserAccount-1.0-SNAPSHOT/CreatingEmployerProfile.css">

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

<head>


    <head>
        <title>Edit admin profile</title>

        <link rel="stylesheet" type="text/css" href="CreatingEmployerProfile.css">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;600&display=swap" rel="stylesheet">
        <!--Stylesheet-->


    </head>


<body>
<div class="background">


</div>
<form method="post" action="editAdminProfileServlet">
    <h3 style="color:black;">Edit Admin Profile</h3>
    <p style="color:#FF8C00;">Please fill out the form below</p>
    <div class="container">
        <br/>
        <form method="post" action="editAdminProfileServlet">
            <div class="form-group">
                <label for="first-name">First Name:</label>
                <input type="text" id="first-name" name="first-name" value="${adminInformation.getFirstName()}"
                       required>
            </div>
            <div class="form-group">
                <label for="last-name">Last Name:</label>
                <input type="text" id="last-name" name="last-name" value="${adminInformation.getLastName()}" required>
            </div>
            <div class="form-group">
                <label for="admin-role">Select Administrator Role:</label>
                <select id="admin-role" name="admin-role" va required>
                    <option value="" selected disabled>Select Administrator Role</option>
                    <option value="Administrator" ${adminInformation.getRole().equals("Administrator") ? "selected" : ""}>
                        Administrator
                    </option>
                </select>
            </div>
            <button type="submit" class="submit-btn">Submit</button>

    </div>
    <script src="script.js"></script>
</form>

</div>

</body>
</html>
