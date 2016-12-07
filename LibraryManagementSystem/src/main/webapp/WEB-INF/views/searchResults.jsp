<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page  isELIgnored="false" %>
 <%-- 
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 --%>
<html>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	
		var val = $('#val1').val();
		console.log(val);
		if (val == "failure")
			alert("Book Cannot be deleted. It is checked out by a patron.");
		else if (val == "exception")
			alert("Something went wrong. Please try again.");
		else if(val=="Success")
			alert("Book Deleted successfully.")
});


</script>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Admin page</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
	<div class="col-md-12">
	<div class="col-md-8">
	<h3>Welcome <strong>${user}</strong></h3>
	</div>
	
	<div class="col-md-4">
	<a href="<c:url value="/logout" />" class="btn btn-default" style="margin: 20px 0px 0px 300px;">Logout</a>
	</div>
	</div>
	<div class="panel panel-default">
		<div class="form-group row">
 			 <div class="col-xs-6">
 			 <input type="hidden" id="val1" value="${val1 }">
    		 	<input class="form-control" type="text" id="txtSearch" placeholder="Search Book Name" name="txtSearch">
    		 	<input type="button" class="btn btn-primary" value="Search" id="btnSearch" name="btnSearch" style="margin:10px 0px 0px 0px;">
  			</div>
		</div>
		<div class="panel-heading"><span class="lead">List of Books</span></div>
		<!-- Default Panel Contents -->
		<table class="table table-hover">
			<thead>
				<tr>
					<th>Publication Year</th>
					<th>Location</th>
					<th>Status</th>
					<th>Author</th>
					<th>Title</th>
					<th>Publisher</th>
					<sec:authorize access="hasRole('ADMIN')">
						<th width="100"></th>
					</sec:authorize>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${books}" var="book">
					<tr>
						<td id="idField" style="display:none;">${book.id}</td>
						<td>${book.publicationYear}</td>
						<td>${book.libraryLocation}</td>
						<td>${book.availability}</td>
						<td>${book.author}</td>
						<td>${book.title}</td>
						<td>${book.publisher}</td>
							<td><a href="<c:url value='/edit-book-${book.id}'/>" class="btn btn-success custom-width">Edit</a></td>
							<td><a href="<c:url value='/delete-book-search-${book.id}?name=${book.title }'/>" class="btn btn-danger custom-width">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<sec:authorize access="hasRole('USER')">
		<div class="well">
			<a href="<c:url value='/admin' />" class="btn btn-primary" >Return Back to Home</a>
		</div>
	</sec:authorize>
</body>
</html>