<g:if test="${! deletedTransactionsList}">
<h4>No Deleted Transactions Found</h4>
</g:if>
<g:else>
<h4>Deleted Transactions</h4>
<ul>
<g:each var="dt" in="${deletedTransactionsList}">
	<li>${dt.oldValue}
	</li>
</g:each>
</ul>
</g:else>