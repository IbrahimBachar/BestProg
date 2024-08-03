<!DOCTYPE html>
<html>
<%@ page import="java.util.List" %>
<%@ page import="com.example.servlet.Student" %>

<head>
<meta charset="ISO-8859-1">
<title>Student Management</title>
<style>
    body {
        background: skyblue;
        font-family: Arial, sans-serif;
    }
    .container {
        background: white;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        max-width: 1200px;
        margin: auto;
    }
    h1, h2 {
        text-align: center;
        color: #333;
    }
    .form-container, .table-container {
        padding: 20px;
        margin-bottom: 20px;
    }
    .form-container {
        border-right: 1px solid #ccc;
    }
    input[type='number'], input[type='text'] {
        width: 100%;
        padding: 10px;
        margin: 10px 0;
        border: 1px solid #ccc;
        border-radius: 5px;
    }
    input[type='submit'], input[type='reset'] {
        width: 48%;
        padding: 10px;
        margin: 10px 1%;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }
    input[type='submit'] {
        background: #28a745;
        color: white;
    }
    input[type='reset'] {
        background: #dc3545;
        color: white;
    }
    input[type='submit']:hover {
        background: #218838;
    }
    input[type='reset']:hover {
        background: #c82333;
    }
    table {
        width: 100%;
        border-collapse: collapse;
    }
    table, th, td {
        border: 1px solid #ccc;
    }
    th, td {
        padding: 10px;
        text-align: left;
    }
    th {
        background-color: #f2f2f2;
    }
</style>
</head>
<body>
    <div class="container">
        <h1>Student Management System</h1>
        <div class="form-container">
            <h2>Insert/Update Student</h2>
            <form action="StudentServlet" method="POST">
                ID: <input type="number" name="id" required><br>
                First Name: <input type="text" name="fname" required><br>
                Last Name: <input type="text" name="lname" required><br>
                <input type="submit" value="Save"> <input type="reset" value="Reset">
                <input type="hidden" name="action" value="save">
            </form>

            <h2>Delete Student</h2>
            <form action="StudentServlet" method="POST">
                ID: <input type="number" name="id" required><br>
                <input type="submit" value="Delete"> <input type="reset" value="Reset">
                <input type="hidden" name="action" value="delete">
            </form>
        </div>

        <div class="table-container">
            <h2>Student List</h2>
            <table>
                <tr><th>ID</th><th>First Name</th><th>Last Name</th></tr>
                <%-- Student rows will be populated here by Servlet --%>
                <%
                    // Fetch students from request attribute set by the servlet
                    List<Student> students = (List<Student>) request.getAttribute("students");
                    if (students != null) {
                        for (Student student : students) {
                            out.println("<tr><td>" + student.getId() + "</td><td>" + student.getFname() + "</td><td>" + student.getLname() + "</td></tr>");
                        }
                    }
                %>
            </table>
        </div>
    </div>
</body>
</html>
