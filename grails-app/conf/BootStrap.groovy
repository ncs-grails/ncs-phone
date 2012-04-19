import edu.umn.ncs.*
import edu.umn.ncs.phone.*

class BootStrap {

    def init = { servletContext ->
		
		// Gender
		def male = Gender.findByName('male')
		if (! male) {
				male = new Gender(name:'male').save()
		}
		def female = Gender.findByName('female')
		if (! female) {
				female = new Gender(name:'female').save()
		}
		
        // BatchDirection
        def outgoing = BatchDirection.findByName('outgoing')
        if (! outgoing) {
            outgoing = new BatchDirection(name:'outgoing').save()
        }
        def incoming = BatchDirection.findByName('incoming')
        if (! incoming) {
            incoming = new BatchDirection(name:'incoming').save()
        }

		//Study
		def ncs = Study.findByName("NCS")
		if (! ncs) {
			ncs = new Study(name:"NCS",
				fullName:"National Children's Study",
				active:true, sponsor:'NCI/NICHD',
				coordinator:"NICHD",
				collaborator:"TBA",
				purpose:"improving the health of america's children",
				exclusionary: false)
			if (!ncs.save()) {
				ncs.errors.each{
					println "${it}"
				}
			}
		}
		
		// IsInitial
		def initial = IsInitial.findByName('initial')
		if (!initial) {
			initial = new IsInitial(name:'initial').save()
		}

		// Instrument Format
		def onThePhone = InstrumentFormat.findByName('phone')
		if ( ! onThePhone ) {
			onThePhone = new InstrumentFormat(name:'phone', groupName:'phone').save()
		}

		
		// CallResultCategory
		def stringList = [
			[suc: true, prob: false, name:'Completed'],
			[suc: false, prob: false, name: 'Not Completed'],
			[suc: false, prob: true, name:'Problem with Phone']
			]
		stringList.each {
			def found = CallResultCategory.findByName(it.name)
			if ( ! found ) {
				found = new CallResultCategory(name: it.name, success: it?.suc, problem: it.prob).save()
			}
		}

		def outgoingCall = Instrument.findByNickName('adhoc_call')
		if ( ! outgoingCall ) {
			outgoingCall = new Instrument(name:'Ad-hoc Call',
				nickName:'adhoc_call', study:ncs, requiresPrimaryContact:true).save()
		}
		
		/*
		 * SELECT '[abbreviation: ''' + [Abbrv] + ''', '
		 *     + 'name: ''' + [Description] + ''', '
		 *     + 'description: "' + REPLACE([DetailDescription], '"', '\"') + '"],'
		 * FROM [CallSystem].[dbo].[call_result_class]
		 */
		
		// CallResult
		stringList = [
			[crc: 1, abbreviation: 'CB', name: 'Call Back', description: "Person on receiving end of the call, whether it is the P or not, asks you to call back at a later time."],
			[crc: 1, abbreviation: 'Comp', name: 'Completed', description: "You are able to achieve the goal of the call.  For example, for an ASU call sheet, you were able to obtain the P's ASU information."],
			[crc: 3, abbreviation: 'Disc', name: 'Disconnected', description: "An operator message says the phone number has been disconnected or is \"not in service.\""],
			[crc: 1, abbreviation: 'HU', name: 'Hung Up', description: "Person on the receiving end hangs up on phone call."],
			[crc: 2, abbreviation: 'LP', name: 'Language Problem', description: "You are unable to communicate with the other person on the line because they do not speak English and there is no translator available."],
			[crc: 2, abbreviation: 'NA', name: 'No Answer', description: "No one picks up the phone. This includes calls where you get several phone rings first and then a fax number beep follows.  No one has picked up the phone while it was ringing."],
			[crc: 2, abbreviation: 'SC', name: 'Sent Correspondence', description: "An initial reminder letter is sent to the P.  To get to this point: a) the P has not responded to our phone calls, and the P has never been scheduled, and it has been at least 5 weeks since the pre-reg was printed; OR b) the P either cancelled or didn't not show up for their scheduled exam and has not rescheduled their appointment yet; OR c) miscellaneous information was sent to P (e.g., Participant Non-response letter (PNR), email or fax)."],
			[crc: 3, abbreviation: 'TD', name: 'Temporarily Disconnected', description: "A message says the phone number has been temporarily disconnected."],
			[crc: 3, abbreviation: 'Unl', name: 'Unlisted', description: "According to Directory Assistance (411), P's phone number is either: a) unlisted � they have no listing of a phone number for the P OR b) unpublished � they have a phone number listed but cannot give it out"],
			[crc: 1, abbreviation: 'ML', name: 'Message Left (with a person)', description: "You leave a detailed message with the P's spouse or alternate contact.  For example, you could say \"Hi, I'm calling from the University of MN.  Could you ask John Smith we would like to schedule his next appointment?\"  "],
			[crc: 2, abbreviation: 'MLV', name: 'Message Left (Voice)', description: "You leave a detailed voice message on the P's answering machine.  Here, you are 100% sure it is the P's answering machine.  For example, you could say \"Hi John Smith, this is ...calling from the lung study to schedule your next appointment.  "],
			[crc: 2, abbreviation: 'NML', name: 'No Message Left', description: "Whether you get a person or an answering machine, you do not leave a message."],
			[crc: 1, abbreviation: 'CML', name: 'Courtesy Message Left (with a person)', description: "The P is not there and someone else answers the phone.  Leave a generic message with that person to pass onto the P without providing detailed information. The intent here is to inform the P we have being trying to get in touch and we will try back another time. For example, you could say \"Hi, I am from the University of MN.  Could you ask John Smith to return my call?\" "],
			[crc: 2, abbreviation: 'CMLV', name: 'Courtesy Message Left (Voice)', description: "Leave a generic voice message without any P identification because you are not sure who will get the message.  Here, you would say something like \"Hi, I'm from the University of MN, please return my call.\""],
			[crc: 3, abbreviation: 'WN', name: 'Wrong Number', description: "Person on the other line says you have the wrong number."],
			[crc: 2, abbreviation: 'LB', name: 'Line Busy', description: "The line appears to be busy after dialing the phone number."],
			[crc: 3, abbreviation: 'OLP', name: 'Other Line Problem', description: "These other problems include a) calls in which one does not get a phone ring or b) the number is a fax number. Regarding numbers that are used as both a phone and fax, if you get several phone rings first and then a fax number beep follows, and no one has picked up the phone yet, then record call result as \"NA\" since no once picked up the phone while it was ringing."],
			[crc: 2, abbreviation: 'NPI', name: 'New Participant Contact Info', description: "You made a phone call to get new information on how and when to get in touch with the P and were successful in obtaining that data from directory assistance, P's spouse or P's alternate contact, etc.  The new information can be a new address or phone number, or notification that P is out of town or living in a different location. "],
			[crc: 2, abbreviation: 'UPI', name: 'Unavailable Participant Contact Info', description: "You made a phone call to get new information on how and when to get in touch with the P, but the person on the other line � directory assistance, spouse or alternate contact, etc. -- does not have that information. The type of information you are looking for is an address or phone number or their whereabouts."],
			[crc: 2, abbreviation: 'VPI', name: 'Verify Participant Contact Info', description: "You were able to verify whether or not the P contact information you have is correct through directory assistance, P's spouse, alternate contact, etc.  P contact information includes such things as an address, phone number, or the P's whereabouts."]
			]
		stringList.each {
			def found = CallResult.findByName(it.name)
			if ( ! found ) {
				found = new CallResult(abbreviation: it.abbreviation
					, name: it.name
					, description: it.description
					, callResultCategory: CallResultCategory.read(it.crc) ).save()
			}
		}
		
		// NonParticipationReason
		stringList = [
			'Confidentiality concerns',
			'Did not like randomization group assignment',
			'Already receiving same tests or has regular physician',
			'Study should pay if exams cause injury',
			'HMO/Insurance will not cover any follow-up or No Insurance',
			'Not interested',
			'Moved or moving',
			'Did not want to do exam(s) or prep',
			'Illness (self and others)',
			'No reason',
			'Too busy',
			'Too far (e.g. travel time)',
			'Transportation problems',
			'Protocol too involved',
			'Location',
			'MD said not to participate',
			'Compensation issue',
			'Would agree to biorep, but not additional screen',
			'Scheduling Conflict',
			'Other' ]

		stringList.each {
			def found = NonParticipationReason.findByName(it)
			if ( ! found ) {
				found = new NonParticipationReason(name: it).save()
			}
		}

		
		// AdminTask
		stringList = [
			'Faxing',
			'Copying',
			'Emailing'
			]
		stringList.each {
			def found = AdminTask.findByName(it)
			if ( ! found ) {
				found = new AdminTask(name: it).save()
			}
		}

		// Form
		stringList = [
			'Event of Interest (EOI) Report',
			'Contact Change',
			'Appointment Change'
			]
		stringList.each {
			def found = Form.findByName(it)
			if ( ! found ) {
				found = new Form(name: it).save()
			}
		}
		
		// ItemResult
		stringList = [
			[ refused: false, abbreviation: 'AR',  name: 'already returned/done item but not logged' ],
			[ refused: false, abbreviation: 'Blkd',  name: 'denied direct access to participant' ],
			[ refused: false, abbreviation: 'CanA',  name: 'canceled' ],
			[ refused: false, abbreviation: 'CanF',  name: 'canceled call, do not contact flag' ],
			[ refused: false, abbreviation: 'COMP',  name: 'completed' ],
			[ refused: false, abbreviation: 'Con',  name: 'considering' ],
			[ refused: false, abbreviation: 'Data',  name: 'data retrieved via phone' ],
			[ refused: false, abbreviation: 'Dead',  name: 'deceased' ],
			[ refused: false, abbreviation: 'PUCR',  name: 'subject unable to consent/refuse' ],
			[ refused: false, abbreviation: 'Rcvd',  name: 'received and logged' ],
			[ refused: true, abbreviation: 'Ref',  name: 'refused or will not do item' ],
			[ refused: false, abbreviation: 'Rsd',  name: 'resend' ],
			[ refused: false, abbreviation: 'SE',  name: 'scheduled event' ],
			[ refused: false, abbreviation: 'Send',  name: 'send item' ],
			[ refused: false, abbreviation: 'TO',  name: 'timed Out' ],
			[ refused: false, abbreviation: 'UPTL',  name: 'unable to reach within protocol' ],
			[ refused: false, abbreviation: 'UTS',  name: 'unable to schedule' ],
			[ refused: false, abbreviation: 'WD',  name: 'withdrawal from study prior to call' ],
			[ refused: false, abbreviation: 'WDRW',  name: 'withdraw from study' ],
			[ refused: false, abbreviation: 'WR',  name: 'will send/do item' ]
		]
		stringList.each {
			def found = Result.findByAbbreviation(it.abbreviation)
			if ( ! found ) {
				found = new Result(abbreviation: it.abbreviation
					, name: it.name
					, description: it.description
					, callSheet: true
					, refused: it.refused ).save()
			}
		}

		
		environments {
			development {
				
				// Make ajz
				def ajz = Person.findByFirstNameAndLastName('Aaron', 'Zirbes')
				if ( ! ajz ) {
						ajz = new Person(title:'Mr',
								firstName:'Aaron',
								lastName:'Zirbes',
								suffix:null,
								gender:male,
								alive:true,
								isRecruitable:false,
								appCreated:'byHand').save()
				}

				// Make ngp
				def ngp = Person.findByFirstNameAndLastName('Natalya', 'Portnov')
				if ( ! ngp ) {
						ngp = new Person(title:'Mrs',
								firstName:'Natalya',
								lastName:'Portnov',
								suffix:null,
								gender:female,
								alive:true,
								isRecruitable:false,
								appCreated:'byHand').save()
				}
			}
		}
		
    }
    def destroy = {
    }
}
