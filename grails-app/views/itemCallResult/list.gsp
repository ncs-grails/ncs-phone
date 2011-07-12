
<%@ page import="edu.umn.ncs.phone.ItemCallResult" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'itemCallResult.label', default: 'ItemCallResult')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'itemCallResult.id.label', default: 'Id')}" />
                        
                            <th><g:message code="itemCallResult.trackedItem.label" default="Tracked Item" /></th>
                        
                            <th><g:message code="itemCallResult.associatedResult.label" default="Associated Result" /></th>
                        
                            <g:sortableColumn property="dateClosed" title="${message(code: 'itemCallResult.dateClosed.label', default: 'Date Closed')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'itemCallResult.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'itemCallResult.lastUpdated.label', default: 'Last Updated')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${itemCallResultInstanceList}" status="i" var="itemCallResultInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${itemCallResultInstance.id}">${fieldValue(bean: itemCallResultInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: itemCallResultInstance, field: "trackedItem")}</td>
                        
                            <td>${fieldValue(bean: itemCallResultInstance, field: "associatedResult")}</td>
                        
                            <td><g:formatDate date="${itemCallResultInstance.dateClosed}" /></td>
                        
                            <td><g:formatDate date="${itemCallResultInstance.dateCreated}" /></td>
                        
                            <td><g:formatDate date="${itemCallResultInstance.lastUpdated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${itemCallResultInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
