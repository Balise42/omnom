<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


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
				<li><span style="text-transform: capitalize">${fn:toLowerCase(ingredient.key.name)}</span>:${ingredient.value.numberOfUnits}
					${ingredient.value.unit}</li>
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