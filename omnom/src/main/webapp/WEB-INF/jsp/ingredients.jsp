<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<section class="container">
	<div class="row">
		<h3>ALL THE INGREDIENTS</h3>
		<c:forEach items="${ingredients}" var="ingredient">
			<div class="col-sm-6 col-md-3" style="padding-bottom: 15px">
				<h3>
					${ingredient.name}
				</h3>
			</div>
		</c:forEach>
	</div>
</section>