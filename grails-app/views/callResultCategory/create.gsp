

<%@ page import="edu.umn.ncs.phone.CallResultCategory" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'callResultCategory.label', default: 'CallResultCategory')}" />
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
            <g:hasErrors bean="${callResultCategoryInstance}">
            <div class="errors">
                <g:renderErrors bean="${callResultCategoryInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="callResultCategory.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callResultCategoryInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${callResultCategoryInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="success"><g:message code="callResultCategory.success.label" default="Success" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callResultCategoryInstance, field: 'success', 'errors')}">
                                    <g:checkBox name="success" value="${callResultCategoryInstance?.success}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="problem"><g:message code="callResultCategory.problem.label" default="Problem" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: callResultCategoryInstance, field: 'problem', 'errors')}">
                                    <g:checkBox name="problem" value="${callResultCategoryInstance?.problem}" />
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
