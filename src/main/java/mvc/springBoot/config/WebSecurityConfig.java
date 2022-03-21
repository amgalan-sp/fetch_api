package mvc.springBoot.config;

import mvc.springBoot.service.UserService;
import mvc.springBoot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SuccessUserHandler successUserHandler;
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(@Qualifier("userDetailServiceImpl") UserService userService, SuccessUserHandler successUserHandler) {
        this.userService = userService;
        this.successUserHandler = successUserHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
//            .antMatchers("/admin/**").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")

            .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
            .antMatchers("/login").anonymous()
            .anyRequest().authenticated()
            .and()
            .formLogin()
                .successHandler(successUserHandler)
            .permitAll()
            .and()
            .logout()
            .permitAll()
            .logoutSuccessUrl("/login")
            .and()
            .httpBasic();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

}