<%-- 
    Document   : user
    Created on : 30-Oct-2022, 2:24:09 PM
    Author     : 14686
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Users</title>
    </head>
    <body>

        <h1>Manage Users</h1>
        <form action="User" method="post">
            <table border ="1">
                <tr>
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Role</th>
                    <th></th>
                </tr>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.email}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <c:choose>
                            <c:when test="${user.getRole().getId()== 1}">
                                <td>system admin</td>
                            </c:when>
                            <c:otherwise>
                                <td>regular user</td>
                            </c:otherwise>
                        </c:choose>
                        <td> <a href="<c:url value="User?action=edit">
                                    <c:param name="action" value="edit"/>
                                    <c:param name="userEmail" value="${user.email}"/>
                                </c:url>"> Edit </a> </td>
                        <td> <a href="<c:url value='User?action=delete&amp;userEmail=${user.email}'/>">
                                Delete</a> </td>
                    </tr>

                </c:forEach>   
            </table> 
        </form>

            <h2>Add User</h2>
            <form  action="" method = "post">
                Email: <input type="text" name="email"  required ><br>
                First Name: <input type="text" name="firstName"  required><br>
                Last Name: <input type="text" name="lastName"  required><br>
                Password: <input type="text" name="password"  required><br>
                Role:
                <select name="role">
                    <c:forEach var="role" items="${roles}" >
                        <c:choose>
                            <c:when test="${role.getId() == 1}">
                                <option>system admin</option>
                            </c:when>
                            <c:otherwise>
                                <option>regular user</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select><br>
                <input type="hidden" name="action" value="add" >
                <input type="submit" value="Add user">
            </form>


    </body>
</html>