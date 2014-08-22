<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<section class="container">
	<div class="row">
		<c:forEach items="${recipes}" var="recipe">
			<div class="col-sm-6 col-md-3" style="padding-bottom: 15px">
				<h3>
					<a href="<spring:url value="/recipe/${recipe.recipeId}"/>">${recipe.name}</a>
				</h3>
			</div>
		</c:forEach>
	</div>
</section>
