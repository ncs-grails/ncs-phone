
<%@ page import="edu.umn.ncs.phone.Administrivia" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'administrivia.label', default: 'Administrivia')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'administrivia.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="comments" title="${message(code: 'administrivia.comments.label', default: 'Comments')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'administrivia.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="endTime" title="${message(code: 'administrivia.endTime.label', default: 'End Time')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'administrivia.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <g:sortableColumn property="phoningUser" title="${message(code: 'administrivia.phoningUser.label', default: 'Phoning User')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${administriviaInstanceList}" status="i" var="administriviaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${administriviaInstance.id}">${fieldValue(bean: administriviaInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: administriviaInstance, field: "comments")}</td>
                        
                            <td><g:formatDate date="${administriviaInstance.dateCreated}" /></td>
                        
                            <td><g:formatDate date="${administriviaInstance.endTime}" /></td>
                        
                            <td><g:formatDate date="${administriviaInstance.lastUpdated}" /></td>
                        
                            <td>${fieldValue(bean: administriviaInstance, field: "phoningUser")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${administriviaInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
