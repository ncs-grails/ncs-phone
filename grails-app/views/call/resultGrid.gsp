<table>
	<thead>
		<tr>
			<td></td>
			<th>S</th>
			<th>M</th>
			<th>T</th>
			<th>W</th>
			<th>T</th>
			<th>F</th>
			<th>S</th>
		</tr>
	</thead>
	<tbody>
		<g:each var="h" in="${grid}">
			<tr>
				<th>${(h.hourOfDay - 1) % 12 + 1}<g:if test="${h.hourOfDay < 12 }">a</g:if><g:else>p</g:else></th>
				<g:each var="d" in="${h.daysOfWeek}">
				<td class="callResultGrid-${d.resultStatus}"></td>
				</g:each>
			</tr>
		</g:each>
	</tbody>
</table>