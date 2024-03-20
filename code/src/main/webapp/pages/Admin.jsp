<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>JSP Page</title>
<meta charset="UTF-8">
<%@include file="../components/Import.jsp"%>
</head>
<body>
	<div class="flex fixed w-full">
		<%@include file="../components/admin/SideNav.jsp"%>
		<c:if test="${tab == 'Dashboard'}">
		</c:if>
		<c:if test="${tab == 'ProductManager'}">
			<c:if test="${action == ''}">
				<%@include file="../components/admin/product/ProductManager.jsp"%>
			</c:if>
			<c:if test="${action == 'create'}">
				<%@include file="../components/admin/product/CreateProduct.jsp"%>
			</c:if>
			<c:if test="${action == 'update'}">
				<%@include file="../components/admin/product/CreateProduct.jsp"%>
			</c:if>
		</c:if>
		<c:if test="${tab == 'OrderManager'}">
			<c:if test="${action == ''}">
				<%@include file="../components/admin/order/OrderManager.jsp"%>
			</c:if>
			<c:if test="${action == 'detail'}">
				<%@include file="../components/admin/order/OrderDetail.jsp"%>
			</c:if>
		</c:if>
		<c:if test="${tab == 'CustomerManager'}">
			<c:if test="${action == ''}">
				<%@include file="../components/admin/customer/CustomerManager.jsp"%>
			</c:if>
			<c:if test="${action == 'detail'}">
				<%@include file="../components/admin/customer/CustomerDetail.jsp"%>
			</c:if>
		</c:if>
		<c:if test="${tab == 'CategoryManager'}">
			<c:if test="${action == ''}">
				<%@include file="../components/admin/category/CategoryManager.jsp"%>
			</c:if>
			<c:if test="${action == 'create'}">
				<%@include file="../components/admin/category/CreateCategory.jsp"%>
			</c:if>
			<c:if test="${action == 'update'}">
				<%@include file="../components/admin/category/CreateCategory.jsp"%>
			</c:if>
		</c:if>
		<c:if test="${tab == 'AuthorManager'}">
			<c:if test="${action == ''}">
				<%@include file="../components/admin/author/AuthorManager.jsp"%>
			</c:if>
			<c:if test="${action == 'detail'}">
				<%@include file="../components/admin/author/AuthorManager.jsp"%>
			</c:if>
			<c:if test="${action == 'update'}">
				<%@include file="../components/admin/author/CreateAuthor.jsp"%>
			</c:if>
			<c:if test="${action == 'create'}">
				<%@include file="../components/admin/author/CreateAuthor.jsp"%>
			</c:if>
		</c:if>
	</div>
	<%@include file="./admin/script.jsp" %>
</body>
</html>
