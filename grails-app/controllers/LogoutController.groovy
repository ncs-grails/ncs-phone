import org.grails.plugins.springsecurity.service.AuthenticateService
import edu.umn.auth.UmnCookieAuthenticationProvider

/**
 * Logout Controller (Example).
 */
class LogoutController {

    /**
     * Dependency injection for the authentication service.
     */
    def authenticateService

    /**
     * Dependency injection for UmnCookieAuthenticationProvider.
     */
    def umnCookieAuthenticationProvider

    /**
     * Index action. Redirects to the Spring security logout uri.
     */
    def index = {

        def config = authenticateService.securityConfig.security
        
        if (config.useUmnCookie) {
			def rurl = config.logoutUrl

			if (authenticateService.isLoggedIn()) {
				request.getSession()?.invalidate()
				redirect url:umnCookieAuthenticationProvider.logoutUrl(rurl)
			} else {
				redirect uri:'/'
			}
        } else {
            redirect uri:'/j_spring_security_logout'
        }
    }
}
