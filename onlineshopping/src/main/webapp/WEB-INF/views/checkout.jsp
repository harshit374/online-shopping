<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="contextRoot" value="${pageContext.request.contextPath}" />

<spring:url var="css" value="/resources/css" />
<spring:url var="js" value="/resources/js" />
<spring:url var="images" value="/resources/images" />

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Online Shopping - ${title}</title>
<script>
	window.menu = '${title}';

	window.contextRoot = '${contextRoot}'
</script>

<!-- Bootstrap Core CSS -->
<link href="${css}/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap readable theme -->
<link href="${css}/bootstrap-readable-theme.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="${css}/myapp.css" rel="stylesheet">

</head>

<body>


	<div class="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="${contextRoot }/home">Home</a>
				</div>
			</div>
		</nav>

		<!-- Page Content -->

		<div class="content">
			<div class="container">

				<div class="row">

					<div class="col-xs-12">
<h2>Final CheckList</h2>
						<div class="jumbotron">
						
							<h4>Total Number of Products : ${userModel.cart.cartLines}</h4>
							<hr>
							<c:forEach items="${cartLines_checkout }" var="cartLine">
							<div class="row">
							<blockquote style="word-wrap: break-word">
								Product Name: ${cartLine.product.name} 
								&emsp; &emsp; &emsp;  Quantity: ${cartLine.productCount }
								</blockquote>
								<div class="text-right">Product Total Price: ${cartLine.total }</div>
								
								</div>
								
							
							</c:forEach>
							<blockquote style="word-wrap: break-word">
								Total Amount to Pay: <strong> &#8377; ${userModel.cart.grandTotal } /-</strong>
							</blockquote>
							<p class="text-center">
							<a href="${contextRoot}/cart/confirm" class="btn btn-success">Confirm</a>
						</p>
						</div>
					</div>
				</div>
			</div>

		</div>

		<!-- Footer -->
		<%@include file="./shared/footer.jsp"%>

	</div>
</body>

</html>
