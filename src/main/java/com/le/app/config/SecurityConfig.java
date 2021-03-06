package com.le.app.config;

import com.le.app.security.jwt.JwtConfigurer;
import com.le.app.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final String GENERAL_CONTENT_ENDPOINT = "/api/content/**";
    private static final String AUTH_ENDPOINT = "/api/auth/**";
    private static final String ADMIN_ENDPOINT = "/api/admin/**";


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(GENERAL_CONTENT_ENDPOINT).permitAll()
                .antMatchers(AUTH_ENDPOINT).permitAll()
               // .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN") // ToDo uncomment it was done for tests
                .antMatchers(ADMIN_ENDPOINT).permitAll() // ToDo delete it was done for tests
//                .anyRequest().authenticated() // ToDo uncomment it was done for tests
                .anyRequest().permitAll()  // ToDo delete it was done for tests
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
