<% def aDayAgo = ( new Date() ) - 1 %>
<g:if test="${administriviaInstanceList}">
<ul>
	<g:each var="a" in="${administriviaInstanceList}">
		<li>
		
		<g:if test="${a.startTime > aDayAgo }">
			<g:formatDate date="${a.startTime}" format="h:mm" />
		</g:if>
		<g:else>
			<g:formatDate date="${a.startTime}" format="M/d/yyyy" />
		</g:else>
		
		<g:if test="${a.formsCompleted}"><ul>
			<li>Forms: <strong>
				<g:each  status="i" var="f" in="${a.formsCompleted}"><g:if test="${i > 0}">,</g:if>
					${f.name}</g:each>
			</strong></li></ul>
			</g:if><g:if test="${a.tasks}"><ul>
			<li>Tasks: <strong>
				<g:each status="i" var="t" in="${a.tasks}"><g:if test="${i > 0}">,</g:if>
					${t.name}</g:each>
			</strong></li></ul>
			
			</g:if><g:if test="${a.comments}"><ul>
			
				<li><strong>"${a.comments}"</strong> - <em>${a.phoningUser}</em>
				</li></ul>
			</g:if>
		</li>
	</g:each>
</ul>
</g:if>
<g:else>
<h4>No Administrative Tasks Logged</h4>
</g:else>