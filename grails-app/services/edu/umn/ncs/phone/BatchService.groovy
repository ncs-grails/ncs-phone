package edu.umn.ncs.phone

import edu.umn.ncs.*

class BatchService {

    static transactional = true

	// creates a batch/item to call on.
    def createCallingItem(username, personInstance, householdInstance, dwellingUnitInstance, instrumentInstance, directionInstance) {

		def trackedItemInstance = null
		
		def today = new GregorianCalendar()
		def yesterday = new GregorianCalendar()
		def fourHoursAgo = new GregorianCalendar()
		
		yesterday.add(Calendar.DATE, -1)
		fourHoursAgo.add(Calendar.HOUR, -4)
		
		if ( (personInstance || householdInstance || dwellingUnitInstance) && instrumentInstance && directionInstance ) {
			
			def formatInstance = InstrumentFormat.findByName('phone')
			def isInitialInstance = IsInitial.findByName('initial')
						
			// generate batch/sid
			
			// first we'll see if an item of this type has been created in the past 24 hours.  If so, we'll re-use it
			def batchInstance = null
			trackedItemInstance = TrackedItem.createCriteria().get{
				and {
					person {
						idEq(personInstance.id)
					}
					batch {
						and {
							instruments {
								idEq(instrumentInstance.id)
							}
							direction {
								idEq(directionInstance.id)
							}
							gt('dateCreated', yesterday.time)
						}
					}
				}
			}
			
			// if the item was found, pull the batch info
			if (trackedItemInstance) {
				batchInstance = trackedItemInstance.batch
			} else {
				// next if no item was found, we'll try and find a batch of this type created by this user in the past 4 hours
				batchInstance = Batch.createCriteria().get {
					and {
						instruments {
							idEq(instrumentInstance.id)
						}
						direction {
							idEq(directionInstance.id)
						}
						gt('dateCreated', fourHoursAgo.time)
						eq('batchRunBy', username)
					}
				}
			}
			
			// next if no batch was found, we'll create a batch
			if ( ! batchInstance ) {
				batchInstance = new Batch(direction: directionInstance,
						batchRunBy: username,
						format: formatInstance,
						batchRunByWhat: 'ncs-call-system')
					.addToInstruments(instrument: instrumentInstance,
						isInitial: isInitialInstance)
					
				if ( batchInstance.hasErrors() || ! batchInstance.save(flush:true)) {
					flash.message = "Unable to create the batch"
					println "unable to create the batch"
					batchInstance.errors.each{
						println "\t${it}"
					}
				}
			}
			batchInstance.refresh()
			
			// now if no tracked item was found, we'll add the item to the batch
			if (batchInstance && ! trackedItemInstance) {
				
				trackedItemInstance = new TrackedItem(person:personInstance
					, household: householdInstance
					, dwellingUnit: dwellingUnitInstance)
				
				batchInstance.addToItems(trackedItemInstance)
				
				if ( batchInstance.hasErrors() || ! batchInstance.save(flush:true)) {
					flash.message = "Unable to add the item to the batch"
					println "Unable to add the item to the batch"
					batchInstance.errors.each{
						println "\t${it}"
					}
				} else {
					trackedItemInstance.refresh()
				}
			}
		}
		return trackedItemInstance
	}
}
