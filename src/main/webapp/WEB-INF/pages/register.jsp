<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="style.css">
    <style>
        body{
            background:linear-gradient(to right,#9198e500, #9198e500), url("https://wallpaperaccess.com/full/2348499.png");
            background-size: cover;
        }
        .wrapper{
            display: flex;
            flex-wrap: wrap;
            margin: 0 auto;
            justify-content: center;
            margin-top: 20vh;

        }
        .content{
            padding: 10px;
            background:white;
            border-radius: 20px;
        }
        .content h1{
            display: flex;
            justify-content: center;
        }
        .label b{
            display: inline-block;
            min-width: 80px;
        }
        .top{
            padding:10px;
        }
        button{
            margin-top: 10px;
            margin-left: 45%;
        }
        td{
            font-family: 'Architects Daughter', cursive;
        }
        .fa-trash{
            color:red;
            background: white;
            border:none;
            font-size: 20px;
        }
        .fa-edit{
            color:#55511b;
            background: white;
            border:none;
            font-size: 20px;
        }
        .fa-check-circle{
            font-weight: 1000;
            color:green;
            background: white;
            border:none;
            font-size: 20px;
        }
        .fa-plus-square{
            font-weight: 1000;
            color:green;
            background: white;
            border:none;
            font-size: 20px;
            margin-right: 10px;
        }
    </style>
</head>
<body>
<div class="wrapper">

    <div class="content">
        <h1>Register in the system</h1>
        <form:form  action="/register" method="post">

            <div class="container">
                <p> ${error}</p>
                <div class="top">
                    <label class="label"><b>Fist name</b></label>
                    <input type="text" placeholder="Enter Username" name="firstName" id = "firstName" required>

                    <label class="label"><b>Last name</b></label>
                    <input type="text" placeholder="Enter Username" name="lastName" id = "lastName" required>

                </div>
                <div class="top">
                    <label class="label"><b>Email</b></label>
                    <input type="email" placeholder="Enter Username" name="email" id = "email" required>

                    <label class="label"><b>Password</b></label>
                    <input type="password" placeholder="Enter Password" name="password" id = "password" required>
                </div>
                <br>

                <button type="submit">Register</button>
            </div>

        </form:form>
    </div>
</div>
</body>
</html>
