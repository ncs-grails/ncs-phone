

<%@ page import="edu.umn.ncs.phone.ItemCallResult" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'itemCallResult.label', default: 'ItemCallResult')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${itemCallResultInstance}">
            <div class="errors">
                <g:renderErrors bean="${itemCallResultInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="trackedItem"><g:message code="itemCallResult.trackedItem.label" default="Tracked Item" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemCallResultInstance, field: 'trackedItem', 'errors')}">
                                    <g:select name="trackedItem.id" from="${edu.umn.ncs.TrackedItem.list()}" optionKey="id" value="${itemCallResultInstance?.trackedItem?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="associatedResult"><g:message code="itemCallResult.associatedResult.label" default="Associated Result" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemCallResultInstance, field: 'associatedResult', 'errors')}">
                                    <g:select name="associatedResult.id" from="${edu.umn.ncs.Result.list()}" optionKey="id" value="${itemCallResultInstance?.associatedResult?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateClosed"><g:message code="itemCallResult.dateClosed.label" default="Date Closed" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemCallResultInstance, field: 'dateClosed', 'errors')}">
                                    <g:datePicker name="dateClosed" precision="day" value="${itemCallResultInstance?.dateClosed}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userCreated"><g:message code="itemCallResult.userCreated.label" default="User Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemCallResultInstance, field: 'userCreated', 'errors')}">
                                    <g:textField name="userCreated" value="${itemCallResultInstance?.userCreated}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userUpdated"><g:message code="itemCallResult.userUpdated.label" default="User Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemCallResultInstance, field: 'userUpdated', 'errors')}">
                                    <g:textField name="userUpdated" value="${itemCallResultInstance?.userUpdated}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
