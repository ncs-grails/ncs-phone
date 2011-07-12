
<%@ page import="edu.umn.ncs.phone.UserQueue" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'userQueue.label', default: 'UserQueue')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link controller="workQueue" action="list" class="list">Work Queues</g:link></span>
        </div>
        <div class="body">
            <h1>Your Queue (${userQueueInstance.username})</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <p>
            This contains all of the phone call items that you are working on. 
            </p>
            <div class="dialog">
                <table>
                	<thead>
                		<tr>
                			<th>Person ID</th>
                			<th>Name</th>
                			<th>Item ID</th>
                			<th>Description</th>
                			<th>Created</th>
                			<th>Remove</th>
                		</tr>
                	</thead>
                    <tbody>
                    	<g:each var="i" in="${userQueueInstance.trackedItems?.sort{it.batch.dateCreated}}">
						<tr>
							<td>${i.person?.id}</td>
							<td>${i.person?.fullName}</td>
							<td><g:link controller="logCall" action="item" id="${i.id}">${i.id}</g:link></td>
							<td><g:link controller="logCall" action="item" id="${i.id}">${i.batch.primaryInstrument}</g:link></td>
							<td>
								<g:formatDate date="${i.batch.dateCreated}" format="MM/dd/yyyy" />
							</td>
							<td><g:link controller="userQueue" action="removeItem" params="${ [id: userQueueInstance?.id,  'trackedItem.id': i.id ]}" onclick="return confirm('Are you sure you wish to remove Item ID: ${i.id}?');">Remove This Item</g:link></td>
						</tr>                    	
                    	</g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
