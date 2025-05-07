<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
    <meta name="author" content="Hỏi Dân IT" />
    <title>Update Product</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <script>
        $(document).ready(() => {
            const avatarFile = $("#imageFile");
            const orgImage = "${newProduct.image}";
            if (orgImage) {
                const imgURL = "/images/product/" + orgImage;
                $("#avatarPreview").attr("src", imgURL);
                $("#avatarPreview").css({ "display": "block" });
            }
            avatarFile.change(function (e) {
                const imgURL = URL.createObjectURL(e.target.files[0]);
                console.log(imgURL);
                $("#avatarPreview").attr("src", imgURL);
                $("#avatarPreview").css({ "display": "block" });
            });
        });
    </script>

    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>

</head>

<body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
        <jsp:include page="../layout/sidebar.jsp" />
        
        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Manage Products</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item"><a href="/admin"> Dashboard</a></li>
                        <li class="breadcrumb-item active"> Products </li>
                        
                    </ol>
                    <div class="mt-5">
                        <div class="row">
                            <div class="col-md-6 col-12 mx-auto">
                                <h1>Update Product</h1>
                                <hr>
                                <form:form action="${pageContext.request.contextPath}/admin/product/update" 
                                method="post" modelAttribute="newProduct" enctype="multipart/form-data" >
                                <c:set var="errorName">
                                    <form:errors path="name" cssClass="invalid-feedback"/>
                                </c:set>
                                <c:set var="errorPrice">
                                    <form:errors path="price" cssClass="invalid-feedback"/>
                                </c:set>
                                <c:set var="errorDetailDesc">
                                    <form:errors path="detailDesc" cssClass="invalid-feedback"/>
                                </c:set>
                                <c:set var="errorShortDesc">
                                    <form:errors path="shortDesc" cssClass="invalid-feedback"/>
                                </c:set>
                                <c:set var="errorQuantity">
                                    <form:errors path="quantity" cssClass="invalid-feedback"/>
                                </c:set>
                                <c:set var="productImage">
                                    <form:errors path="quantity" cssClass="invalid-feedback"/>
                                </c:set>
                                <div class="mb-3 " style="display: none;">
                                    <label for="id" class="form-label">Id: </label>
                                    <form:input type="text" class="form-control"  path="id" />
                                </div>
                                <div class="mb-3 col-12 col-md-6" >
                                    <label for="name" class="form-label">Name</label>
                                    <form:input type="text" class="form-control ${not empty errorName ? 'is-invalid': ''}" 
                                        path="name" />
                                        ${errorName}
                                </div>
                                <div class="mb-3 col-12 col-md-6" >
                                    <label for="price" class="form-label">Price</label>
                                    <form:input type="number" class="form-control ${not empty errorPrice ? 'is-invalid': ''}" 
                                    id="price" name="price" path="price" />
                                    ${errorPrice}
                                </div>
                                <div class="mb-3 col-12" >
                                    <label for="detailDesc" class="form-label">Detail description:</label>
                                    <form:textarea type="text" class="form-control ${not empty errorDetailDesc ? 'is-invalid': ''}"  id="detailDesc" name="detailDesc" 
                                    path="detailDesc" />
                                    ${errorDetailDesc}
                                </div>
                                <div class="mb-3 col-12 col-md-6" >
                                    <label for="shortDesc" class="form-label">Short description:</label>
                                    <form:input type="text" class="form-control ${not empty errorShortDesc ? 'is-invalid': ''}" id="shortDesc" name="shortDesc"
                                    path="shortDesc" />
                                    ${errorShortDesc}
                                </div>
                                <div class="mb-3 col-12 col-md-6" >
                                    <label for="quantity" class="form-label">Quantity:</label>
                                    <form:input type="number" class="form-control" id="quantity" name="quantity"
                                    path="quantity" />
                                    ${errorQuantity}
                                </div>
                                <div class="mb-3 col-12 col-md-6" >
                                    <label class="form-label">Factory: </label>
                                    <form:select class="form-select" path="factory">
                                        <form:option value="APPLE">Apple (MacBook)</form:option>
                                        <form:option value="ASUS">Asus</form:option>
                                        <form:option value="LENOVO">Lenovo</form:option>
                                        <form:option value="DELL">Dell</form:option>
                                        <form:option value="LG">LG</form:option>
                                        <form:option value="ACER">Acer</form:option>
                                    </form:select>
                                </div>
                                <div class="mb-3 col-12 col-md-6" >
                                    <label class="form-label">Target: </label>
                                    <form:select class="form-select" path="target">
                                        <form:option value="GAMING">Gaming</form:option>
                                        <form:option value="SINHVIEN">Sinh viên - Văn phòng </form:option>
                                        <form:option value="THIETKE">Thiết kế đồ hoạ</form:option>
                                        <form:option value="MONGNHE">Mỏng nhẹ</form:option>
                                        <form:option value="DOANHNHAN">Doanh nhân</form:option>
                                    </form:select>
                                </div>
                                <div class="mb-3 col-12 col-md-6" >
                                    <label for="image" class="form-label">Image: </label>
                                    <input type="file" class="form-control"  name="imageFile"
                                    id="imageFile" accept=".png, .jpg, .jpeg"/>
                                </div>
                                <div class="mb-3 col-12" >
                                    <img style="max-height: 250px;" 
                                    alt="avatar preview" id="avatarPreview">
                                </div>
                                <div class="mb-5 col-12" >
                                    <button type="submit" class="btn btn-primary">Update Product</button>
                                </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                
                    
                </div>
            </main>
            <jsp:include page="../layout/footer.jsp" />
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
    <script src="js/scripts.js"></script>
   
</body>

</html>


    
