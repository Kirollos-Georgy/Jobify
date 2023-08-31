<!DOCTYPE html>
<html lang="en">
<head>
    <title> Profile page </title>
    <link rel="stylesheet" type="text/css" href="EmployerHomePage.css">
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
    <title> Profile page </title>
    <!-- add the white screen in the middle  -->
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
<form method="get" action="viewAdminProfileServlet">

    <h3 style="color:#f09819;">Welcome </h3>
    <link rel="stylesheet" type="text/css" href="index.css">

    <div class="form-group">

        <label style="color:black;">First Name: ${adminInformation.getFirstName()}</label>
        <label style="color:black;">Last Name: ${adminInformation.getLastName()}</label>
        <label style="color:black;">Email: ${adminInformation.getEmail()}</label>
        <label style="color:black;">Role: ${adminInformation.getRole()}</label>
        <label style="color:black;">Permissions: </label>
        <pre><label style="color:black;">${adminInformation.permissionsToString()}</label></pre>
        <label><a style="color:blue;" href="editAdminProfileServlet">Edit Profile Information<br></a></label>
    </div>
</form>
</body>
</html>
