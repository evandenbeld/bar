/*
 * 
 * Bluetooth Bar Admin
 * BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.badmin.glasses.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Spring boot security configuration.
 * 
 * @author erwin
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        // TODO service discovery including authentication?
    	// TODO use Roles for authorization
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }
}
