
<%@ page import="edu.umn.ncs.phone.Form" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'form.label', default: 'Form')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'form.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'form.name.label', default: 'Name')}" />
                        
                            <th><g:message code="form.linkedInstrument.label" default="Linked Instrument" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${formInstanceList}" status="i" var="formInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${formInstance.id}">${fieldValue(bean: formInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: formInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: formInstance, field: "linkedInstrument")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${formInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
