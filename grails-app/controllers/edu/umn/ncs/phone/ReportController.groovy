package edu.umn.ncs.phone

import edu.umn.ncs.*
import org.codehaus.groovy.grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_CALLING'])
class ReportController {

	def authenticateService
	def debug = false
	
    def index = {
		redirect(view:'/')
	}
	
	def item = {
		
			// set up some of the collections that get passed to the page.
			def itemCallResultInstanceList = []
			// look up the tracked item(s) to be called on
			def trackedItemInstanceList = []
			
			if (params?.id) {
				def idList = params?.id.split(',')
				idList.each{ trackedItemId ->
					def trackedItemInstance = TrackedItem.read(trackedItemId)

					// check to see if it's been closed out
					def itemCallResultInstance = ItemCallResult.findByTrackedItem(trackedItemInstance)
					
					if (itemCallResultInstance) {
						itemCallResultInstanceList.add(itemCallResultInstance)
					}	
					if (trackedItemInstance) {
						trackedItemInstanceList.add(trackedItemInstance)
					}
				}
			}
			
			if (trackedItemInstanceList) {
				// fill in the collections that fill the page. (or should we AJAX this?)
				
				return [ trackedItemInstanceList: trackedItemInstanceList,
					itemCallResultInstanceList: itemCallResultInstanceList ]
				
			} else {
				if (flash.message) {
					flash.message += "<br/>"
				} else {
					flash.message = ""
				}
				flash.message += "Sorry, we didn't find any items that matched your criteria."
				redirect(action:'index')
			}
	}
	
}
