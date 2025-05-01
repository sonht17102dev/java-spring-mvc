<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update User</title>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
   
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-6 col-12 mx-auto">
                <h1>Update User</h1>
                <hr>
                <form:form action="${pageContext.request.contextPath}/admin/user/update" 
                method="post" modelAttribute="newUser" >
                    <div class="mb-3 " style="display: none;">
                        <label for="id" class="form-label">Id: </label>
                        <form:input type="text" class="form-control"  path="id" />
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <form:input type="text" class="form-control" id="email" name="email"
                        path="email" disabled="true"/>
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Phone number:</label>
                        <form:input type="text" class="form-control" id="phoneNumber" name="phoneNumber" 
                        path="phone" />
                    </div>
                    <div class="mb-3">
                        <label for="fullName" class="form-label">Full Name:</label>
                        <form:input type="text" class="form-control" id="fullName" name="fullName"
                        path="fullName" />
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">Address:</label>
                        <form:input type="text" class="form-control" id="address" name="address"
                        path="address" />
                    </div>
                    <button type="submit" class="btn btn-warning">Update</button>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>