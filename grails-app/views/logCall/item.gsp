<html>
    <head>
        <title>Call System - National Children's Study</title>
        <meta name="layout" content="ncs" />
        <link rel="stylesheet" type="text/css" href="${resource(dir:'css',file:'call.css')}" />
        <g:javascript src="callLog.js" />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link elementId="reportLink" controller="report" action="item" id="${ trackedItemInstanceList?.id?.join(',') }" class="list">Read Only</g:link></span>
            <span class="menuButton"><g:link elementId="userQueueLink" controller="userQueue" action="show" class="list">My Queue</g:link></span>
            <span class="menuButton"><g:link elementId="workQueueLink" controller="workQueue" action="list" class="list">Work Queues</g:link></span>
        </div>
    	<g:form name="itemForm">
    		<g:hiddenField name="trackedItemInstanceList" value="${ trackedItemInstanceList?.id?.join(',') }" />
    		<g:hiddenField name="heartBeatUrl" value="${ createLink(controller:'heartBeat') }" />
			<input name="call-create-action" value="${createLink(controller:'call', action:'create')}" type="hidden" />
			<input name="tasks-menu-action" value="${createLink(controller:'logCall', action:'tasks')}" type="hidden" />
			<input name="admin-create-action" value="${createLink(controller:'administrivia', action:'create')}" type="hidden" />
			<input name="finishUp-action" value="${createLink(controller:'itemCallResult', action:'createCollection')}" type="hidden" />
    	</g:form>
        <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
        </g:if>
    	<div class="container_12">
    		<div id="topBar" class="grid_12 pull_12 ui-widget-content ui-corner-bottom ui-corner-top">
			    <div id="pageTitle" class="grid_3 alpha">
		    		<h1>Calling System</h1>
		    	</div>
			    <div id="pageTitle" class="grid_9 omega" style="margin-top: 1em;">
		    		<g:if test="${trackedItemInstanceList}">
		    			<% def firstItem = trackedItemInstanceList[0] %>
		    			<g:if test="${firstItem?.person}">
		    				<span style="font-size:1.5em;">${firstItem?.person?.fullName}</span>
		    			</g:if>
		    			<g:if test="${firstItem?.household}">
		    				Household (TODO)
		    			</g:if>
		    			<g:if test="${firstItem?.dwellingUnit}">
		    				<span style="font-size:1.5em;">${firstItem?.dwellingUnit?.address?.address}</span>
		    			</g:if>
		    		</g:if>
		    		<g:else>
		    		<h2>No Items Passed</h2>
		    		</g:else>
		    	</div>
    		</div>
    		<div class="clear"></div>

    		<div id="leftSide" class="grid_9 pull_9">
			    <div id="leftAccordion" class="grid_3 alpha">
			    	<% def itemIdList = trackedItemInstanceList.collect{it?.id}.join(',') %>
				    <h3 id="itemInformation"><a href="${createLink(action:'itemInformation') }/${itemIdList}">Information</a></h3>
				    <div></div>
			    				    
				    <h3 id="callHistory"><a href="${createLink(controller:'call', action:'history') }/${itemIdList}">Calling History</a></h3>
				    <div id="callHistoryContainer"></div>
					
				    <h3 id="callResultGrid"><a href="${createLink(controller:'call', action:'resultGrid') }/${itemIdList}">Call Result Grid</a></h3>
				    <div id="callResultGridContainer"></div>
					
				    <h3 id="adminHistory"><a href="${createLink(controller:'administrivia', action:'history') }/${itemIdList}">Administrative History</a></h3>
				    <div></div>
					
				</div> <!-- End leftAccordion -->
				
				<div id="centerSection" class="grid_6 omega">
					<div id="taskArea" class="ui-widget-content ui-corner-bottom ui-corner-top"></div>
				</div>
			</div>
			
			<div id="rightSide" class="grid3 pull_3">
			    <div id="rightAccordion" class="grid_3 alpha">
				    <h3 id="lookup"><a href="${createLink(action:'lookup') }/${itemIdList}">Look Up</a></h3>
				    <div></div>
	
					<h3 id="deletedTransactions"><a href="${createLink(action:'deletedTransactions') }/${itemIdList}">Deleted Transactions</a></h3>
				    <div></div>
				</div>
			</div>
		</div>
    </body>
</html>
