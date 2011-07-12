<% def aDayAgo = ( new Date() ) - 1 %>
<g:if test="${callInstanceList}">
<ul>
	<g:each var="c" in="${callInstanceList}">
		<li>
			<g:if test="${c.startTime > aDayAgo }">
				<g:formatDate date="${c.startTime}" format="h:mm" />
			</g:if>
			<g:else>
				<g:formatDate date="${c.startTime}" format="M/d/yyyy" />
			</g:else>
			<ul>
				<g:if test="${c.numberDialed}">
				<li>Dialed: <strong>${c.numberDialed}</strong></li>
				</g:if>
				<li>Result: <strong>${c.callResult ?: 'none'}</strong></li>
				<g:if test="${c.comments}">
				<li><strong>"${c.comments}"</strong> - <em>${c.phoningUser}</em>
				</li>
				</g:if>
			</ul>
		</li>
	</g:each>
</ul>
</g:if>
<g:else>
<h4>No Calls Logged</h4>
</g:else>