package com.example.backend.service.user

import com.example.backend.jpa.user.Role
import com.example.backend.jpa.user.RoleName
import com.example.backend.jpa.user.User
import com.example.backend.models.users.SignUpRequest
import com.example.backend.models.users.UserCreateResponseDTO
import com.example.backend.repository.user.RoleRepository
import com.example.backend.repository.user.UserRepository
import com.example.backend.service.keycloak.KeycloakService
import com.example.backend.service.session.SessionService
import com.example.backend.util.exceptions.BadRequestException
import org.keycloak.admin.client.CreatedResponseUtil
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.UserResource
import org.keycloak.authorization.client.AuthzClient
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse


@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleRepository: RoleRepository,
    private val keycloakService: KeycloakService,
    private val keycloak: Keycloak,
    @Value("\${keycloak.realm}") private val realm: String,
    private val authzClient: AuthzClient,
    private val sessionService: SessionService
) {
    fun authenticate(email: String, password: String, res: HttpServletResponse) {
        val user = if (userRepository.findByEmail(email).isPresent) userRepository.findByEmail(email).get()
        else {
            throw BadCredentialsException("User not found with email: $email")
        }
        if (!passwordEncoder.matches(password, user.password)) {
            throw BadCredentialsException("Invalid email/password supplied")
        }
        try {
            val response = authzClient.obtainAccessToken(email, password)
            res.addCookie(Cookie("refreshToken", response.refreshToken))
            res.addCookie(Cookie("accessToken", response.token))
            sessionService.createSession(user, res)
            res.status = HttpStatus.OK.value()
        } catch (e: Exception) {
            throw BadCredentialsException("Invalid email/password supplied")
        }
    }

    fun register(signUpRequest: SignUpRequest, response: HttpServletResponse) {
        if (userRepository.findByEmail(signUpRequest.email).isPresent)
            throw BadCredentialsException("Email already exists")

        if (userRepository.findByUsername(signUpRequest.username).isPresent)
            throw BadCredentialsException("Username already exists")

        val user = UserRepresentation()

        user.apply {
            isEnabled = true
            username = signUpRequest.username
            email = signUpRequest.email
        }

        val role =
            if (roleRepository.findByName(RoleName.ROLE_USER).isPresent) roleRepository.findByName(RoleName.ROLE_USER)
                .get() else roleRepository.save(Role(name = RoleName.ROLE_USER))

        val realmResource = keycloak.realm(realm)
        val usersResource = realmResource.users()
        val userCreateResponseDto = UserCreateResponseDTO()
        var userFinal: User? = null
        try {
            usersResource.create(user).use { res ->
                userCreateResponseDto.statusCode = res.status
                userCreateResponseDto.status = res.statusInfo.toString()
                if (res.status == HttpStatus.CREATED.value()) {
                    val userId = CreatedResponseUtil.getCreatedId(res)

                    userCreateResponseDto.userId = userId
                    val passwordCred = keycloakService.getCredentialRepresentation(signUpRequest.password)
                    val userResource = usersResource[userId]
                    userResource.resetPassword(passwordCred)
                    insertNewRole(role.name.name, realmResource, userResource)
                    val userEntity = User(
                        email = signUpRequest.email,
                        username = signUpRequest.username,
                        password = passwordEncoder.encode(signUpRequest.password),
                        roles = mutableSetOf()
                    )
                    userEntity.roles.add(role)
                    val final = userRepository.save(userEntity)
                    userFinal = final
                }
            }
        } catch (e: Exception) {
            throw BadRequestException("${e.message}")
        }

        if(userFinal != null) sessionService.createSession(userFinal!!, response)
        response.status = HttpStatus.CREATED.value()
    }

    private fun insertNewRole(
        newRole: String,
        realmResource: RealmResource,
        userResource: UserResource
    ) {
        val realmRoleUser: RoleRepresentation = realmResource.roles()[newRole].toRepresentation()
        userResource.roles().realmLevel().add(listOf(realmRoleUser))
    }

    private fun removeOldRoles(
        newRole: String,
        realmResource: RealmResource,
        userResource: UserResource
    ) {
        val roleRepresentationList: MutableList<RoleRepresentation> = ArrayList()
        RoleName.stream()
            .filter { i -> i.name != newRole.uppercase(Locale.getDefault()) }
            .forEach { i ->
                val realmRoleUser = realmResource.roles()[i.name].toRepresentation()
                roleRepresentationList.add(realmRoleUser)
            }
        userResource.roles().realmLevel().remove(roleRepresentationList)
    }
}