<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Scheduler</title>
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
        <c:import url="userLink.jsp"> </c:import>
        <h1>Schedule by you request</h1>
        <table>
            <c:set var = "free" value = "free"/>
            <p>${errorMsg}</p>

            <c:if test="${schedulePointList.size() > 0}">
                <tr>
                    <th>Date</th>
                    <th>Time start</th>
                    <th>Time end</th>
                    <th>Service</th>
                    <th>Staffer</th>
                    <th>Available status</th>
                </tr>
            </c:if>

            <c:forEach var="film" items="${schedulePointList}" varStatus="i">
                <tr>
                    <td> ${film.dateStart}</td>
                    <td> ${film.timeStart}</td>
                    <td> ${film.timeEnd}</td>
                    <td> ${film.service.name}</td>
                    <td> ${film.staffer.firstName}  ${film.staffer.lastName}</td>
                    <td> ${film.availableStatus == free} <c:if test="${film.availableStatus == free}" >
                        <br/>
                        <br/>
                        <form:form  action="/createApp" method="post">

                            <div class="container">
                                <input type="text" name="id" id = "id" required value="${film.schedulePointId}" hidden>
                                <input type="text" name="userId" required value="${user.userId}" hidden>
                                <button class="far fa-check-circle" type="submit">Assign this time for me</button>
                            </div>

                        </form:form>
                    </c:if>
                    </td>
                </tr>

            </c:forEach>
        </table>
    </div></div>
</body>
</html>
