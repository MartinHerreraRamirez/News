package egg.news.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import egg.news.services.UsersService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity{

    @Autowired
    public UsersService usersService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usersService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(requests -> requests
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "JOURNALIST")
                .antMatchers("/css/**", "/js/**", "/img/**", "/**").permitAll())
            .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .permitAll())
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll())
            .csrf(csrf -> csrf.disable());
    
        return http.build();
    }
    
}
