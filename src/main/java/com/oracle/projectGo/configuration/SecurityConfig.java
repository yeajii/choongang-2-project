package com.oracle.projectGo.configuration;



import com.oracle.projectGo.type.UsersRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Lazy
    private  CustomAuthenticationProvider authProvider;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return ((web) -> web.ignoring()
                .requestMatchers("/resources/**", "/h2-console/**"));
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((authorizeRequests) ->authorizeRequests
                .requestMatchers("/homework/submissionHomework").hasRole(UsersRoleType.STUDENT.getLabel())
                .requestMatchers("/homework/editSubmissionHomework").hasRole(UsersRoleType.STUDENT.getLabel())
                .requestMatchers("/homework/**").hasAnyRole(UsersRoleType.EDUCATOR.getLabel(),UsersRoleType.ADMIN.getLabel())
                .anyRequest().permitAll()
        );
        http.formLogin(form -> form
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/")
            .usernameParameter("nickname")
        );
      http.logout(logout -> logout
            .permitAll()
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true));

        http.exceptionHandling(e -> e
                .accessDeniedHandler(new CustomAccessDeniedHandler())
        );

        http.authenticationProvider(authProvider);
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
