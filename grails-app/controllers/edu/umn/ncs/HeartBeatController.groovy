package edu.umn.ncs

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS'])
class HeartBeatController {

	def springSecurityService

    def index = {
		
		// get the username from the authenticated principal (person) from the auth service
		def username = springSecurityService?.principal?.getUsername()

		def now = new Date()
		println "heartbeat: '${username}' connected at ${now}."
		render "<heartbeat>alive</heartbeat>"
	}
}
