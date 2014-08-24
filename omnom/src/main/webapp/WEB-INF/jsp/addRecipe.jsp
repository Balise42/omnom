<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<section class="container">
	<form:form modelAttribute="newRecipe" class="form-horizontal">
		<fieldset>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="name">Name:</label>
				<div class="col-lg-10">
					<form:input id="name" path="name" type="text"
						class="form:input-large" />
					<form:errors path="name" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="numPersons">Number
					of persons:</label>
				<div class="col-lg-10">
					<form:input id="numPersons" path="numPersons" type="text"
						class="form:input-large" />
					<form:errors path="numPersons" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="prepTime">Prep
					time:</label>
				<div class="col-lg-10">
					<form:input id="prepTime" path="prepTime" type="text"
						class="form:input-large" />
					<form:errors path="prepTime" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="cookingTime">Cooking
					time:</label>
				<div class="col-lg-10">
					<form:input id="cookingTime" path="cookingTime" type="text"
						class="form:input-large" />
					<form:errors path="cookingTime" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="restTime">Resting
					time:</label>
				<div class="col-lg-10">
					<form:input id="restTime" path="restTime" type="text"
						class="form:input-large" />
					<form:errors path="restTime" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="ingredientsList">Ingredients:</label>
				<div class="col-lg-10">
					<form:input id="ingredientsList[0].name" path="ingredientsList[0].name"
						type="text" class="form:input-large" />
					<form:input id="quantities[0].numberOfUnits" path="quantities[0].numberOfUnits" type="text"
						class="form:input-large" />
					<form:input id="quantities[0].unit" path="quantities[0].unit" type="text"
						class="form:input-large" />
				</div>
				<div class="col-lg-10">
					<form:input id="ingredientsList[1].name" path="ingredientsList[1].name"
						type="text" class="form:input-large" />
					<form:input id="quantities[1].numberOfUnits" path="quantities[1].numberOfUnits" type="text"
						class="form:input-large" />
					<form:input id="quantities[1].unit" path="quantities[1].unit" type="text"
						class="form:input-large" />
				</div>
				<div class="col-lg-10">
					<form:input id="ingredientsList[2].name" path="ingredientsList[2].name"
						type="text" class="form:input-large" />
					<form:input id="quantities[2].numberOfUnits" path="quantities[2].numberOfUnits" type="text"
						class="form:input-large" />
					<form:input id="quantities[2].unit" path="quantities[2].unit" type="text"
						class="form:input-large" />
				</div>
				<form:errors path="ingredientsList" cssClass="text-danger" />
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="">Steps:</label>
				<div class="col-lg-10">
					<form:input id="steps" path="steps" type="text"
						class="form:input-large" />
					<form:errors path="steps" cssClass="text-danger" />
				</div>
			</div>

			<div class="form-group">
				<div class="col-lg-offset-2 col-lg-10">
					<input type="submit" id="btnAdd" class="btn btn-primary" value="Add" />
				</div>
			</div>

		</fieldset>
	</form:form>
</section>