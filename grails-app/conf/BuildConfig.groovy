grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
        grailsRepo "http://svn.cccs.umn.edu/grails-plugins"
        grailsRepo "http://svn.cccs.umn.edu/ncs-grails-plugins"

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

		// Exclude dependencies to resolve conflicts with pdf and renderer plugin
		// compile("org.xhtmlrenderer:core-renderer:R8") {
		// 	excludes 'xml-apis', 'xmlParserAPIs'
		// }

        // runtime 'mysql:mysql-connector-java:5.1.5'
    }
    plugins {
		compile ":hibernate:$grailsVersion"
		compile ":tomcat:$grailsVersion"

		compile ":spring-security-core:1.2.7.2"
		compile ":spring-security-ldap:1.0.5.1"
		compile ":spring-security-shibboleth-native-sp:1.0.3"
		provided ":spring-security-mock:1.0.1"

		compile ":audit-logging:0.5.4"
		test ":code-coverage:1.2.5"
		test ":codenarc:0.16.1"
		compile ":joda-time:1.0"
		compile ":jquery:1.6.1.1"
		compile ":ncs-calling:0.1"
		compile ":ncs-people:0.2"
		compile ":ncs-tracking:0.5"
		compile ":ncs-web-template:0.2"
	}
}

codenarc.reports = {
	JenkinsXmlReport('xml') {
		outputFile = 'target/test-reports/CodeNarcReport.xml'
		title = 'CodeNarc Report for NCS Phone System'
	}
	JenkinsHtmlReport('html') {
		outputFile = 'CodeNarcReport.html'
		title = 'CodeNarc Report for NCS Phone System'
	}
}
codenarc.propertiesFile = 'grails-app/conf/codenarc.properties'
