							<h4>This will open lookup in a new window/tab.</h4>
							<g:each var="i" in="${trackedItemInstanceList}">
							<g:link base="/ncs-case-management" controller="trackedItem" action="show" id="${i.id}" target="_blank">
								Open Tracked Item (${i.id}) Lookup
							</g:link>
							</g:each>
