package edu.umn.ncs.phone

class ReportService {

    static transactional = true
	
	def dataSource

    def getCallResultGrid(trackedItemInstanceList) {
		// takes a tracked item instance list, and returns
		// a call success/failure grid.

		// this will hold all the calls		
		def callInstanceList = []
		
		// get the list of all completed call results
		if (trackedItemInstanceList) {
			def trackedItemIds = trackedItemInstanceList.collect{it?.id}
			callInstanceList = Call.createCriteria().list{
				and {
					isNotNull('callResult')
					items {
						'in'("id", trackedItemIds)
					}
				}
			}
		}
		
		// We'll return an empty grid for now
		def grid = []
		
		(7..19).each{ h ->
			def daysOfWeek = []
			(1..7).each{ d ->
				// get all the results
				def callResultInstanceList = callInstanceList.findAll{ it.hourOfDay == h && it.dayOfWeek == d }.collect{it.callResult}
				def resultStatus = ''
				callResultInstanceList.each{
					if (! resultStatus && it.callResultCategory.success) {
						resultStatus = 'success'
					} else if (! resultStatus && it.callResultCategory.problem) {
						resultStatus = 'problem'
					} else if (! resultStatus) {
						resultStatus = 'fail'
					} else if (resultStatus == 'problem' && it.callResultCategory.success) {
						resultStatus = 'success'
					} else if (resultStatus == 'fail' && it.callResultCategory.success) {
						resultStatus = 'success'
					} else if (resultStatus == 'fail' && it.callResultCategory.problem) {
						resultStatus = 'fail'
					}
				}
				
				daysOfWeek.add([dayOfWeek: d, resultStatus: resultStatus])
			}
			def hour = [hourOfDay:h, daysOfWeek: daysOfWeek ]
			grid.add(hour)
		}
		
		return grid
    }
}
