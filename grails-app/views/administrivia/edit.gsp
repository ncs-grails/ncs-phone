

<%@ page import="edu.umn.ncs.phone.Administrivia" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'administrivia.label', default: 'Administrivia')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${administriviaInstance}">
            <div class="errors">
                <g:renderErrors bean="${administriviaInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${administriviaInstance?.id}" />
                <g:hiddenField name="version" value="${administriviaInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="comments"><g:message code="administrivia.comments.label" default="Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: administriviaInstance, field: 'comments', 'errors')}">
                                    <g:textField name="comments" value="${administriviaInstance?.comments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endTime"><g:message code="administrivia.endTime.label" default="End Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: administriviaInstance, field: 'endTime', 'errors')}">
                                    <g:datePicker name="endTime" precision="day" value="${administriviaInstance?.endTime}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="formsCompleted"><g:message code="administrivia.formsCompleted.label" default="Forms Completed" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: administriviaInstance, field: 'formsCompleted', 'errors')}">
                                    <g:select name="formsCompleted" from="${edu.umn.ncs.phone.Form.list()}" multiple="yes" optionKey="id" size="5" value="${administriviaInstance?.formsCompleted*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="items"><g:message code="administrivia.items.label" default="Items" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: administriviaInstance, field: 'items', 'errors')}">
                                    <g:select name="items" from="${edu.umn.ncs.TrackedItem.list()}" multiple="yes" optionKey="id" size="5" value="${administriviaInstance?.items*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoningUser"><g:message code="administrivia.phoningUser.label" default="Phoning User" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: administriviaInstance, field: 'phoningUser', 'errors')}">
                                    <g:textField name="phoningUser" value="${administriviaInstance?.phoningUser}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="startTime"><g:message code="administrivia.startTime.label" default="Start Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: administriviaInstance, field: 'startTime', 'errors')}">
                                    <g:datePicker name="startTime" precision="day" value="${administriviaInstance?.startTime}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tasks"><g:message code="administrivia.tasks.label" default="Tasks" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: administriviaInstance, field: 'tasks', 'errors')}">
                                    <g:select name="tasks" from="${edu.umn.ncs.phone.AdminTask.list()}" multiple="yes" optionKey="id" size="5" value="${administriviaInstance?.tasks*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="userCreated"><g:message code="administrivia.userCreated.label" default="User Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: administriviaInstance, field: 'userCreated', 'errors')}">
                                    <g:textField name="userCreated" value="${administriviaInstance?.userCreated}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="userUpdated"><g:message code="administrivia.userUpdated.label" default="User Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: administriviaInstance, field: 'userUpdated', 'errors')}">
                                    <g:textField name="userUpdated" value="${administriviaInstance?.userUpdated}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
