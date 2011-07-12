<html>
    <head>
        <title>Call System - National Children's Study</title>
        <meta name="layout" content="ncs" />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link controller="userQueue" action="show" class="list">My Queue</g:link></span>
            <span class="menuButton"><g:link controller="workQueue" action="list" class="list">Work Queues</g:link></span>
        </div>
    	<div id="mainMenu">
    		<h1>Call System Menu</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
    		<p>Please click your destination</p>
    		
    		<div style="border: thin dotted maroon; margin: 1em; padding: 1em;">
    			<g:link controller="logCall" action="newCallToPerson" id="1">Call Aaron</g:link>
    		</div>
    		<div style="border: thin dotted maroon; margin: 1em; padding: 1em;">
    			<g:link controller="logCall" action="newCallToPerson" id="2">Call Natalya</g:link>
    		</div>
    	</div>
    </body>
</html>
