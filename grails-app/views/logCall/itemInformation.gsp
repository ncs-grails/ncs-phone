	<g:each var="i" in="${trackedItemInstanceList}">
	<h4>${i.batch.primaryInstrument}</h4>
	<ul>
		<% def itemCallResultInstance = itemCallResultInstanceList.find{ it.trackedItem.id == i.id } %>
		<g:if test="${itemCallResultInstance}">
		<li>
			Closed
			<br/>
			<strong>${itemCallResultInstance.associatedResult.name}</strong>
			on
			<strong><g:formatDate date="${itemCallResultInstance.dateCreated}" format="MM/dd/yyyy h:mm a" /></strong>
			<g:form controller="itemCallResult" action="delete">
			<g:hiddenField name="id" value="${itemCallResultInstance.id}"/>
			<div class="buttons">
			<span class="button"><g:actionSubmit class="delete" action="delete" value="Delete Result and Re-Open Case" onclick="return confirm('Are you sure? This cannot be undone.');" /></span>
			</div>
			</g:form>
		</li>
		</g:if>
	
		<li>Created <g:formatDate date="${i.batch.dateCreated}" format="MM/dd/yyyy" /></li>
		<li>Created by ${i.batch.batchRunBy}</li>
		<g:if test="${i.batch.instrumentDate}">
			<li>Call Starting <g:formatDate date="${i.batch.instrumentDate}" format="MM/dd/yyyy" /></li>
		</g:if>
		<g:if test="${i.result}" >
			<li>Result: ${i.result.result} on <g:formatDate date="${i.result.receivedDate}" format="MM/dd/yyyy" /></li>
		</g:if>
		<!-- TODO: -->
		<li>
			Do not contact flags?
			<strong>No</strong>
		</li>
	</ul>
	</g:each>
