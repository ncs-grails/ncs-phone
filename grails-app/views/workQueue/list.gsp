
<%@ page import="edu.umn.ncs.phone.WorkQueue" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'workQueue.label', default: 'Work Queue')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link id="userQueueLink" controller="userQueue" action="show" class="list">My Queue</g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Work Queues</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <p></p>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="name" title="${message(code: 'workQueue.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="priority" title="${message(code: 'workQueue.priority.label', default: 'Priority')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:if test="${! workQueueInstanceList}">
                    	<tr><td colspan="3">There are No Work Queues at this time.</td></tr>
                    </g:if>
                    <g:each in="${workQueueInstanceList}" status="i" var="workQueueInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${workQueueInstance.id}">${fieldValue(bean: workQueueInstance, field: "name")}</g:link></td>
                        
                            <td>${fieldValue(bean: workQueueInstance, field: "priority")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${workQueueInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
