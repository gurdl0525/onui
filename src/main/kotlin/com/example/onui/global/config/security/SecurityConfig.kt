package com.example.onui.global.config.security

import com.example.onui.global.config.error.handler.ExceptionHandlerFilter
import com.example.onui.global.config.filter.FilterConfig
import com.example.onui.global.config.jwt.JwtTokenResolver
import com.example.onui.global.config.jwt.TokenProvider
import com.vane.badwordfiltering.BadWordFiltering
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import java.util.*

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val exceptionHandlerFilter: ExceptionHandlerFilter,
    private val tokenResolver: JwtTokenResolver
) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf().disable()
            .formLogin().disable()
            .cors()

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()

            .antMatchers("/**/test").permitAll()
            .antMatchers("/auth/**").permitAll()
            .antMatchers(HttpMethod.DELETE, "/auth").authenticated()
            .antMatchers(HttpMethod.GET, "/healthcheck").permitAll()
            .anyRequest().authenticated()
            .and()

            .apply(FilterConfig(tokenProvider, tokenResolver, exceptionHandlerFilter))
            .and().build()
    }

    @Bean
    fun badWordFilter() = BadWordFiltering()
}