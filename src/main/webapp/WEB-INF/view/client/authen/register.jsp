<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Register</title>
        <link href="/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="bg-primary">
        <div id="layoutAuthentication">
            <div id="layoutAuthentication_content">
                <main>
                    <div class="container">
                        <div class="row justify-content-center">
                            <div class="col-lg-7">
                                <div class="card shadow-lg border-0 rounded-lg mt-5">
                                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Create Account</h3></div>
                                    <div class="card-body">
                                        <form:form method="post" action="/register" modelAttribute="registerUser">
                                            <c:set var="errorEmail">
                                                <form:errors path="email" cssClass="invalid-feedback"/>
                                            </c:set>
                                            <c:set var="errorPassword">
                                                <form:errors path="password" cssClass="invalid-feedback"/>
                                            </c:set>
                                            <c:set var="errFirstName">
                                                <form:errors path="firstName" cssClass="invalid-feedback"/>
                                            </c:set>
                                            <c:set var="errLastName">
                                                <form:errors path="lastName" cssClass="invalid-feedback"/>
                                            </c:set>
                                            <c:set var="errorPasswordConfirm">
                                                <form:errors path="confirmPassword" cssClass="invalid-feedback"/>
                                            </c:set>
                                            <div class="row mb-3">
                                                <div class="col-md-6">
                                                    <div class="form-floating mb-3 mb-md-0">
                                                        <form:input class="form-control ${not empty errFirstName ? 'is-invalid': ''}" 
                                                         type="text" path="firstName"/>
                                                        <label for="inputFirstName">First name</label>
                                                        ${errFirstName}
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-floating">
                                                        <form:input class="form-control ${not empty errLastName ? 'is-invalid': ''}" 
                                                        type="text" path="lastName"/>
                                                        <label for="inputLastName">Last name</label>
                                                        ${errLastName}
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-floating mb-3">
                                                <form:input class="form-control ${not empty errorEmail ? 'is-invalid': ''}" 
                                                type="email" path="email"/>
                                                <label for="inputEmail">Email address</label>
                                                ${errorEmail}
                                            </div>
                                            <div class="row mb-3">
                                                <div class="col-md-6">
                                                    <div class="form-floating mb-3 mb-md-0">
                                                        <form:input class="form-control ${not empty errorPassword ? 'is-invalid': ''}" 
                                                        type="password"  path="password"/>
                                                        <label for="inputPassword">Password</label>
                                                        ${errorPassword}
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-floating mb-3 mb-md-0">
                                                        <form:input class="form-control ${not empty errorPasswordConfirm ? 'is-invalid': ''}" 
                                                         type="password"  path="confirmPassword"/>
                                                        <label for="inputPasswordConfirm">Confirm Password</label>
                                                        ${errorPasswordConfirm}
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="mt-4 mb-0">
                                                <div class="d-grid"><button class="btn btn-primary btn-block" type="submit">Create Account</button></div>
                                            </div>
                                        </form:form>
                                    </div>
                                    <div class="card-footer text-center py-3">
                                        <div class="small"><a href="/login">Have an account? Go to login</a></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
           
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="/js/scripts.js"></script>
    </body>
</html>
