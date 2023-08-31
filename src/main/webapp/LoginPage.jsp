<jsp:useBean id="loginInformation" scope="application" type="com.Jobify.JobifyApplication"/>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login page</title>
    <link href="Index.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;600&display=swap" rel="stylesheet">
    <!--Stylesheet-->
    <style media="screen">

    </style>
</head>
<!--background design colors and size -->
<body>
<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>
<form action="${pageContext.request.contextPath}/jobify/login" method="post" th:object="${loginInformation}">


    <h3 style="color: black;">Login</h3>


    <div class="container">
        <br/>
        <!--  post method for adding the email, password & functional login button -->
        <table>
            <tr>
                <div class="form-group">
                    <td>Email:</td>
                    <td><label>
                        <input th:field="*{email}"/>
                    </label></td>
                </div>
            </tr>
            <tr>
                <div class="form-group">
                    <td>Password:</td>
                    <td><label>
                        <input th:th:field="*{password}" type="password"/>
                    </label></td>
                </div>
            </tr>
            <tr>
                <td></td>
                <td><button class="submit-btn" type="submit">Login</button></td>
            </tr>
        </table>

    </div>
</form>
</body>
</html>
