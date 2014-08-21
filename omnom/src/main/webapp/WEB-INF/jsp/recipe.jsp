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
					<p>See one recipe</p>
				</div>
			</div>
		</section>
		<section class="container">
			<div class="row">
			<h3>${recipe.name}</h3>
			<p>For ${recipe.numPersons} persons</p>
			<p>Preparation time: ${recipe.prepTime.standardMinutes} minutes</p>
			<p>Cooking time: ${recipe.cookingTime.standardMinutes} minutes</p>
			<p>Resting time: ${recipe.restTime.standardMinutes} minutes</p>
			<p>Ingredients:</p>
			<ul>
				<c:forEach items="${recipe.ingredients}" var="ingredient">
					<li>${ingredient.key.name}: ${ingredient.value.numberOfUnits} ${ingredient.value.unit}</li>
				</c:forEach>
			</ul>
			<p>Steps:
			<ul>
				<c:forEach items="${recipe.steps}" var="step">
					<li>${step}</li>
				</c:forEach>
			</ul>
			</div>
		</section>
	</body>
</html>	