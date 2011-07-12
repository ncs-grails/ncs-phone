

<%@ page import="edu.umn.ncs.phone.Call" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'call.label', default: 'Call')}" />
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
            <g:hasErrors bean="${callInstance}">
            <div class="errors">
                <g:renderErrors bean="${callInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${callInstance?.id}" />
                <g:hiddenField name="version" value="${callInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="startTime"><g:message code="call.startTime.label" default="Start Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callInstance, field: 'startTime', 'errors')}">
                                    <g:datePicker name="startTime" precision="day" value="${callInstance?.startTime}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endTime"><g:message code="call.endTime.label" default="End Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callInstance, field: 'endTime', 'errors')}">
                                    <g:datePicker name="endTime" precision="day" value="${callInstance?.endTime}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="numberDialed"><g:message code="call.numberDialed.label" default="Number Dialed" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callInstance, field: 'numberDialed', 'errors')}">
                                    <g:textField name="numberDialed" value="${callInstance?.numberDialed}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="callResult"><g:message code="call.callResult.label" default="Call Result" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callInstance, field: 'callResult', 'errors')}">
                                    <g:select name="callResult.id" from="${edu.umn.ncs.phone.CallResult.list()}" optionKey="id" value="${callInstance?.callResult?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="outgoing"><g:message code="call.outgoing.label" default="Outgoing" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callInstance, field: 'outgoing', 'errors')}">
                                    <g:checkBox name="outgoing" value="${callInstance?.outgoing}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="alternateContactedParty"><g:message code="call.alternateContactedParty.label" default="Alternate Contacted Party" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callInstance, field: 'alternateContactedParty', 'errors')}">
                                    <g:textField name="alternateContactedParty" value="${callInstance?.alternateContactedParty}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="comments"><g:message code="call.comments.label" default="Comments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callInstance, field: 'comments', 'errors')}">
                                    <g:textField name="comments" value="${callInstance?.comments}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="items"><g:message code="call.items.label" default="Items" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callInstance, field: 'items', 'errors')}">
                                    <g:select name="items" from="${edu.umn.ncs.TrackedItem.list()}" multiple="yes" optionKey="id" size="5" value="${callInstance?.items*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phoningUser"><g:message code="call.phoningUser.label" default="Phoning User" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callInstance, field: 'phoningUser', 'errors')}">
                                    <g:textField name="phoningUser" value="${callInstance?.phoningUser}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="userCreated"><g:message code="call.userCreated.label" default="User Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callInstance, field: 'userCreated', 'errors')}">
                                    <g:textField name="userCreated" value="${callInstance?.userCreated}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="userUpdated"><g:message code="call.userUpdated.label" default="User Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callInstance, field: 'userUpdated', 'errors')}">
                                    <g:textField name="userUpdated" value="${callInstance?.userUpdated}" />
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
