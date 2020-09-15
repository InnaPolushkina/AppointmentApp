<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Appointments</title>
    <link rel="stylesheet" href="style.css">
    <script src="https://kit.fontawesome.com/c5422dcdc2.js" crossorigin="anonymous"></script>
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
            padding: 80px;
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
        <%--<c:set var ="user" value="${user}"/>--%>
        <div class = "appHeader">
            <h1>Appointments</h1>
            <h2>Welcome to Appointment system !</h2>

            <p><a href="/services">All services</a></p>
            <form:form  action="/services" method="post" >

                <div class="container">
                    <input type="text" name="userId" required value="${user.userId}" hidden>

                    <button type="submit" hidden>All services</button>
                </div>

            </form:form>
            <p><a href="/staffers">All staffers</a></p>
            <h2>All your appointments</h2>
            <%-- <c:import url="userLink.jsp"> </c:import>--%>
        </div>
        <table>
            <p>${errorListMsg}</p>
            <c:if test="${appointmentList.size() > 0}">
                <tr>
                    <th>Staffer</th>
                    <th>Service</th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Action</th>
                </tr>

                <c:forEach var="film" items="${appointmentList}" varStatus="i">
                    <tr>
                        <td> ${film.staffer.firstName} ${film.staffer.lastName}</td>
                        <td> ${film.service.name}</td>
                        <td> ${film.appointmentDate}</td>
                        <td> ${film.schedulePoint.timeStart}</td>

                        <td>
                            <form:form  action="/delete" method="post">

                                <div >
                                    <input type="text" name="id" required value="${film.appointmentId}" hidden>

                                    <button class="fas fa-trash" type="submit"></button>
                                </div>

                            </form:form>
                            <form:form  action="/edit" method="post">

                                <div >
                                    <input type="text" name="id" required value="${film.appointmentId}" hidden>

                                    <button class="far fa-edit" type="submit">Edit</button>
                                </div>

                            </form:form>
                        </td>
                    </tr>


                </c:forEach>
            </c:if>
        </table>

        <%--<h2><a href="/edit">Edit appointment page</a></h2>--%>

        <p>${appointment}</p>

    </div></div>
</body>
</html>
