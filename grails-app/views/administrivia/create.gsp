<%@ page import="edu.umn.ncs.phone.Administrivia" %>
<g:if test="${flash.message}">
<div class="message">${flash.message}</div>
</g:if>
<g:hasErrors bean="${administriviaInstance}">
<div class="errors">
    <g:renderErrors bean="${administriviaInstance}" as="list" />
</div>
</g:hasErrors>
<h4>Log Administrative Time</h4>
<g:form controller="administrivia">
	<dl class="newItem">
		<dt><label for="startTime">Adminstrivia Started:</label></dt>
		<dd><g:formatDate date="${administriviaInstance?.startTime}" format="M/d/yyyy hh:mm:ss a" />
		<g:hiddenField name="startTimeString" value="${formatDate(administriviaInstance?.startTime, format:'yyyy-MM-dd HH:mm:ss') }" /></dd>
		
		<dt><label for="formsCompleted"><g:message code="administriviaInstance.formsCompleted.label" default="Forms Completed" /></label></dt>
		<dd>
			<ul>
				<g:each var="f" in="${edu.umn.ncs.phone.Form.list()}">
				<li>
					${f.name}
					<g:checkBox name="formsCompleted" value="${f.id}" checked="${administriviaInstance?.formsCompleted?.contains(f)}" />
				</li>
				</g:each>
			</ul>
		</dd>
	
		<dt><label for="tasks"><g:message code="administriviaInstance.tasks.label" default="Tasks" /></label></dt>
		<dd>
			<ul>
				<g:each var="t" in="${edu.umn.ncs.phone.AdminTask.list()}">
				<li>
					${t.name}
					<g:checkBox name="tasks" value="${t.id}" checked="${administriviaInstance?.tasks?.contains(t)}" />					
				</li>
				</g:each>
			</ul>
		</dd>
	
		<dt><label for="comments"><g:message code="administriviaInstance.items.label" default="Comments" /></label></dt>
		<dd><g:textArea name="comments" value="${administriviaInstance?.comments}" cols="40" /></dd>
		
		<g:hiddenField name="items" value="${administriviaInstance?.items?.id?.join(',') }" />
	</dl>

	<div class="buttons">
	    <span class="button"><g:submitToRemote action="save" name="create" class="save" onSuccess="adminSaveSuccess();" onFailure="adminSaveFailure();" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
	    <span class="button"><input type="submit" class="delete" value="Cancel" onclick="return cancelTask();" /></span>
	</div>
</g:form>
