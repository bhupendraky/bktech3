package com.bktech.user.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@EnableWebSecurity
@ConditionalOnProperty(name = "spring.security.type", havingValue = "JDBC")
public class JdbcAuthSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	/**
	 * Get the user details from data source
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select user_name as username, password "
				+ " from users where user_name=?")
		.authoritiesByUsernameQuery("select user_name as username, authority "
				+ " from authorities where user_name=?");
	}

	/**
	 * Configure runtime user data in default schema provided by Spring
	 * @param auth
	 * @throws Exception
	 */
	protected void configure2(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.withDefaultSchema()
		.withUser(User.withUsername("BK").password("BK").roles("ADMIN"))
		.withUser(User.withUsername("RK").password("RK").roles("USER"));
	}

}