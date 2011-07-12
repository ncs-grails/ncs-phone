<%@ page import="edu.umn.ncs.phone.Call" %>
<h4>Log a Call</h4>
<g:form controller="call">
	<dl class="newItem">
		<dt><label for="startTime">Call Started:</label></dt>
		<dd><g:formatDate date="${callInstance?.startTime}" format="M/d/yyyy hh:mm:ss a" />
		<g:hiddenField name="startTimeString" value="${formatDate(date:callInstance?.startTime, format:'yyyy-MM-dd HH:mm:ss') }" /></dd>
	
		<dt><label for="outgoing"><g:message code="call.comments.label" default="Direction" /></label></dt>
		<dd><g:select name="outgoing" from="${[ [id:false, value:'incoming'], [id:true, value:'outgoing' ] ]}" optionKey="id" optionValue="value" value="${callInstance?.outgoing}" /></dd>
	
		<dt><label for="numberDialed"><g:message code="call.numberDialed.label" default="Number Dialed" /></label></dt>
		<dd><g:textField name="numberDialed" value="${callInstance?.numberDialed}" /></dd>
	
		<dt><label for="alternateContactedParty"><g:message code="call.alternateContactedParty.label" default="Party Contacted" /></label></dt>
		<dd><g:textField name="alternateContactedParty" value="${callInstance?.alternateContactedParty}" /></dd>
	
		<dt><label for="callResult"><g:message code="call.callResult.label" default="Result" /></label></dt>
		<dd><g:select name="callResult.id" from="${edu.umn.ncs.phone.CallResult.list()}" optionKey="id" value="${callInstance?.callResult?.id}" noSelection="['null': '']" /></dd>
	
		<dt><label for="comments"><g:message code="call.comments.label" default="Comments" /></label></dt>
		<dd><g:textArea name="comments" value="${callInstance?.comments}" cols="40" /></dd>
		
		<g:hiddenField name="items" value="${callInstance?.items?.id?.join(',') }" />
	</dl>

	<div class="buttons">
	    <span class="button"><g:submitToRemote action="save" name="create" class="save" onSuccess="callSaveSuccess();" onFailure="callSaveFailure();" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
	    <span class="button"><input type="submit" class="delete" value="Cancel" onclick="return cancelTask();" /></span>
	</div>
</g:form>
