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
    <title>Profile page</title>
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
<form method="get" action="viewEmployerProfileServlet">

    <h3 style="color:#f09819;">Welcome </h3>
    <link rel="stylesheet" type="text/css" href="index.css">


    <div class="form-group">
        <label style="color:black;">First Name: ${employerInformation.getFirstName()}</label>
        <label style="color:black;">Last Name: ${employerInformation.getLastName()}</label>
        <label style="color:black;">Email: ${employerInformation.getEmail()}</label>
        <label style="color:black;">Company: ${employerInformation.getCompany()}</label>
        <label><a style="color:blue;" href="editEmployerProfileServlet">Edit Profile Information<br></a></label>
        <%--<label><a style="color:blue;" href="AddJobPosting.html">Add a job posting<br></a></label>--%>
        <%--<label><a style="color:blue;" href="viewCreatedJobPostingsServlet?interview=false">View job postings created<br></a></label>
        <label><a style="color:blue;" href="viewCreatedJobPostingsServlet?interview=true">View selected candidates<br></a></label>--%>
    </div>
</form>

</body>
</html>
