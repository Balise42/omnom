<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1">
		<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
		<title>Omnom</title>
	</head>
	<body>
		<section>
			<div class="jumbotron">
				<div class="container">
					<h1>Omnom</h1>
					<p>List of all the recipes</p>
				</div>
			</div>
		</section>
		<section class="container">
			<div class="row">
				<c:forEach items="${recipes}" var="recipe">
					 <div class="col-sm-6 col-md-3" style="padding-bottom: 15px">
					 	<h3><a href="recipe/${recipe.recipeId}">${recipe.name}</a></h3>
					 </div>
				</c:forEach>
			</div>
		</section>
	</body>
</html>