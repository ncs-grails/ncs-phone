
<%@ page import="edu.umn.ncs.phone.CallResult" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'callResult.label', default: 'CallResult')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'callResult.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'callResult.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="abbreviation" title="${message(code: 'callResult.abbreviation.label', default: 'Abbreviation')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'callResult.description.label', default: 'Description')}" />
                        
                            <th><g:message code="callResult.callResultCategory.label" default="Call Result Category" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${callResultInstanceList}" status="i" var="callResultInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${callResultInstance.id}">${fieldValue(bean: callResultInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: callResultInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: callResultInstance, field: "abbreviation")}</td>
                        
                            <td>${fieldValue(bean: callResultInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: callResultInstance, field: "callResultCategory")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${callResultInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
