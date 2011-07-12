<g:if test="${flash.message}">
<div class="message">${flash.message}</div>
</g:if>
<g:hasErrors bean="${administriviaInstance}">
<div class="errors">
    <g:renderErrors bean="${administriviaInstance}" as="list" />
</div>
</g:hasErrors>
<g:form controller="itemCallResult">
	<h4>Close Out the Case</h4>
	<g:hiddenField name="refusedResultIds" value="${refusedResultIds}" />
	<ul style="margin-bottom: 1em; margin-top: 1em;">
		<g:each var="i" in="${trackedItemInstanceList}">
		<li>
			<g:checkBox name="items" value="${i.id}" checked="${true}" /> 
			${i.batch.primaryInstrument}
		</li>
		</g:each>
	</ul>
	<dl class="newItem">
		<dt><label for="associatedResult">Final Result:</label></dt>
		<dd>
			<g:select name="associatedResult.id" from="${resultInstanceList}" optionKey="id" optionValue="name" value="${itemCallResultInstance?.associatedResult?.id}" noSelection="${ ['null':' -- Please Choose One -- '] }" onchange="return resultChanged();" />
		</dd>
		
		<div id="refusalReasonContainer" style="display:none; margin-top: 1em;">
		<dt><label for="formsCompleted" style="font-weight: bold;">Refusal Reason</label></dt>
		<dd style="max-height: 10em; overflow: auto;">
			<table>
				<tbody>
				<g:each var="npr" in="${nonParticipationReasonInstanceList}">
					<tr style="border-bottom: thin dotted grey;">
						<td>${npr.name}</td>
						<td><g:checkBox name="nonParticipationReasons" value="${npr.id}" checked="${itemCallResultInstance?.nonParticipationReasons?.contains(npr)}" /></td>
					</tr>
				</g:each>
				</tbody>
			</table>
		</dd>
		</div>
	</dl>
	
	<div class="buttons">
	    <span class="button" id="closeOutButton" style="display:none;"><g:submitToRemote controller="itemCallResult" action="saveCollection" name="create" class="save" onSuccess="itemCallResultSaveSuccess();" onFailure="itemCallResultSaveFailure();" value="Close Out" /></span>
		<span class="button"><input type="submit" class="delete" value="Cancel" onclick="return cancelTask();" /></span>	    
	</div>
</g:form>

