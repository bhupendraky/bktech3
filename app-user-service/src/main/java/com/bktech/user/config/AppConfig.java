package com.bktech.user.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.bktech.user.constants.Constants;
import com.bktech.user.ctx.ExecutionContext;
import com.bktech.user.ctx.UserContext;
import com.bktech.user.execp.AppException;
import com.bktech.user.execp.ExceptionCode;
import com.bktech.user.repository.UserRepository;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

	private final UserRepository userRepository;

	@Bean("auditorAwareImpl")
	public AuditorAware<String> auditorAwareImpl() {
		return () -> {
			String userId = Optional.ofNullable(ExecutionContext.getUserContext().get())
					.map(UserContext::getUserId)
					.orElse(Constants.APP_DOMAIN);
			return Optional.of(userId);
		};
	}

	@Bean
	public RequestInterceptor requestInterceptor() {
		return template -> {
			Optional.ofNullable(ExecutionContext.getUserContext().get())
			.ifPresent(ctx -> template.header(Constants.REQ_HEADER_USER_ID, ctx.getUserId()));
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return (request, response, authentication) ->
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> userRepository.findByUsername(username)
				.orElseThrow(() -> new AppException(ExceptionCode.USRSVC_0005, username));
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

}
