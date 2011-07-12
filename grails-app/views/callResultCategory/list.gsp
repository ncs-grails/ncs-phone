
<%@ page import="edu.umn.ncs.phone.CallResultCategory" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'callResultCategory.label', default: 'CallResultCategory')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'callResultCategory.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'callResultCategory.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="success" title="${message(code: 'callResultCategory.success.label', default: 'Success')}" />
                        
                            <g:sortableColumn property="problem" title="${message(code: 'callResultCategory.problem.label', default: 'Problem')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${callResultCategoryInstanceList}" status="i" var="callResultCategoryInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${callResultCategoryInstance.id}">${fieldValue(bean: callResultCategoryInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: callResultCategoryInstance, field: "name")}</td>
                        
                            <td><g:formatBoolean boolean="${callResultCategoryInstance.success}" /></td>
                        
                            <td><g:formatBoolean boolean="${callResultCategoryInstance.problem}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${callResultCategoryInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
