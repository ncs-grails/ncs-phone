package edu.umn.ncs

import org.codehaus.groovy.grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS'])
class HeartBeatController {

	def authenticateService

    def index = {
		
		// get the username from the authenticated principal (person) from the auth service
		def username = authenticateService?.principal()?.getUsername()

		def now = new Date()
		println "heartbeat: '${username}' connected at ${now}."
		render "<heartbeat>alive</heartbeat>"
	}
}
