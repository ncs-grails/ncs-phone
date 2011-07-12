
<%@ page import="edu.umn.ncs.phone.Call" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'call.label', default: 'Call')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'call.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="startTime" title="${message(code: 'call.startTime.label', default: 'Start Time')}" />
                        
                            <g:sortableColumn property="endTime" title="${message(code: 'call.endTime.label', default: 'End Time')}" />
                        
                            <g:sortableColumn property="numberDialed" title="${message(code: 'call.numberDialed.label', default: 'Number Dialed')}" />
                        
                            <th><g:message code="call.callResult.label" default="Call Result" /></th>
                        
                            <g:sortableColumn property="outgoing" title="${message(code: 'call.outgoing.label', default: 'Outgoing')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${callInstanceList}" status="i" var="callInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${callInstance.id}">${fieldValue(bean: callInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${callInstance.startTime}" /></td>
                        
                            <td><g:formatDate date="${callInstance.endTime}" /></td>
                        
                            <td>${fieldValue(bean: callInstance, field: "numberDialed")}</td>
                        
                            <td>${fieldValue(bean: callInstance, field: "callResult")}</td>
                        
                            <td><g:formatBoolean boolean="${callInstance.outgoing}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${callInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
