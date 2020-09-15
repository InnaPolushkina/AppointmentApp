<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Services</title>
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
        <h1>All services</h1>

        <table class="blueTable">
            <c:if test="${serviceList.size() > 0}">
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Duration</th>
                <th>Price</th>
                <th>Action</th>
            </tr>
            </c:if>

            <c:forEach var="film" items="${serviceList}" varStatus="i">
            <tr>
                <td> ${film.name}</td>
                <td> ${film.description}</td>
                <td> ${film.duration} minutes</td>
                <td> ${film.price} $</td>
                <td>
                    <form:form  action="/scheduler" method="post">

                        <div class="container">
                            <input type="text" name="id" id = "id" required value="${film.serviceId}" hidden>
                            <input type="text" name="userId" required value="${user.userId}" hidden>
                            <button class="far fa-plus-square" type="submit">Create appointment with with service</button>
                        </div>

                    </form:form>
                </td>
            </tr>

            </c:forEach>

    </div></div>
</table>

</body>
</html>
