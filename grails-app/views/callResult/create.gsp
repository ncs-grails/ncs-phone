

<%@ page import="edu.umn.ncs.phone.CallResult" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'callResult.label', default: 'CallResult')}" />
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
            <g:hasErrors bean="${callResultInstance}">
            <div class="errors">
                <g:renderErrors bean="${callResultInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="callResult.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callResultInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${callResultInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="abbreviation"><g:message code="callResult.abbreviation.label" default="Abbreviation" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callResultInstance, field: 'abbreviation', 'errors')}">
                                    <g:textField name="abbreviation" maxlength="16" value="${callResultInstance?.abbreviation}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="callResult.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callResultInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" cols="40" rows="5" value="${callResultInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="callResultCategory"><g:message code="callResult.callResultCategory.label" default="Call Result Category" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callResultInstance, field: 'callResultCategory', 'errors')}">
                                    <g:select name="callResultCategory.id" from="${edu.umn.ncs.phone.CallResultCategory.list()}" optionKey="id" value="${callResultInstance?.callResultCategory?.id}"  />
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
