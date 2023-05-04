package com.example.backend.service.session

import com.example.backend.jpa.user.User
import com.example.backend.jpa.user.UserSession
import com.example.backend.repository.user.SessionRepository
import org.springframework.security.core.session.SessionRegistry
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.LocalDateTime
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class SessionService(
    private val sessionRepository: SessionRepository,
    private val sessionRegistry: SessionRegistry,
) {
    fun createSession(user: User, response: HttpServletResponse) {
        val session = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request.session.id
        sessionRegistry.registerNewSession(session, user.username)

        val newSession = UserSession(
            sessionId = session,
            user = user,
            creationTime = LocalDateTime.now(),
            lastAccessTime = LocalDateTime.now(),
        )
        sessionRepository.save(newSession)

        // Set session cookie to client
        response.addCookie(Cookie("SESSION", session))
    }

//    fun getSessionToken(sessionId: String): String? {
//        val session = sessionRegistry.getSession(sessionId)
//        if (session != null) {
//            val principal = sessionRegistry.getSessionInformation(sessionId)?.principal
//            if (principal is KeycloakAuthenticationToken) {
//                return principal.account.keycloakSecurityContext.tokenString
//            }
//        }
//        return null
//    }
//
//    fun refreshSessionToken(sessionId: String): String? {
//        val session = sessionRegistry.getSession(sessionId)
//        if (session != null) {
//            val refreshToken = session.refreshToken
//            if (refreshToken != null) {
//                val tokenManager = keycloak.tokenManager()
//                val newAccessToken = tokenManager.refreshToken(refreshToken)
//                return newAccessToken?.token
//            }
//        }
//        return null
//    }

//    fun updateSession(refreshToken: RefreshToken, response: HttpServletResponse) {
//        val session = sessionRegistry.getSession(refreshToken.sessionState!!)
//        if (session != null) {
//            val username = sessionRegistry.getSessionInformation(session.id)?.principal?.toString()
//            if (username != null) {
//                val user = if (userRepository.findByUsername(username).isPresent) userRepository.findByUsername(username).get()
//                else throw UsernameNotFoundException("User not found: $username")
//                sessionRegistry.registerNewSession(session.id, user.username)
//                response.addHeader("Authorization", "Bearer " + refreshToken.token)
//            }
//        } else {
//            throw SessionNotFoundException("Session not found for refreshToken: ${refreshToken.token}")
//        }
//    }

    fun getRefreshToken(request: HttpServletRequest): String? {
        val session = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request.session
        val savedToken = session.getAttribute("refreshToken") as String?
        val requestToken = request.getHeader("Authorization")?.replace("Bearer ", "")
        return requestToken ?: savedToken
    }

    fun closeSession(sessionId: String) {
        val userSession = sessionRepository.findBySessionId(sessionId)
            ?: throw IllegalArgumentException("Invalid session id: $sessionId")

        sessionRegistry.removeSessionInformation(sessionId)
        sessionRepository.deleteById(userSession.id)
    }
}


