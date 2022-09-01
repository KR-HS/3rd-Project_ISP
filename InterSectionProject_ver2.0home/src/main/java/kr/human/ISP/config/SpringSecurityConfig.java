package kr.human.ISP.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import kr.human.ISP.service.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	UserService userService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/", "/index", "/login/**", "/decorators/**", "static/**", "static/moim/**", "/Category","/moimOfDate").permitAll()
				.antMatchers("/applyPage").hasAnyRole("USER").antMatchers("/myPage").hasAnyRole("USER")
				.antMatchers("/moim/**").hasAnyRole("USER") // 동원 추가 모임폴더
				.antMatchers("/admin").hasAnyRole("ADMIN").anyRequest().authenticated().and().formLogin()
				.loginPage("/login/login").successHandler(new MyLoginSuccessHandler()).permitAll()
				.and().logout()
				.logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/");
		return http.build();
	}

	// Enable jdbc authentication
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

		// 4. 시큐리티에서 현재의 VO를 사용하려면
		// 여기에서 회원 정보를 가져와 인증 영역에 정보를 저장할 서비스를 등록해 준다.
		// 등록해주는 서비스는 UserDetailsService를 구현한 서비스이어야 한다.

		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());

		/*
		 * auth .jdbcAuthentication() .passwordEncoder(new BCryptPasswordEncoder())
		 * .dataSource(dataSource)
		 * .authoritiesByUsernameQuery("select Username,role from roles where Username = ?"
		 * )
		 * .usersByUsernameQuery("select Username, Password, Enabled from Users where Username = ?"
		 * ) ;
		 */
	}

	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder authenticationMgr)
	// throws Exception {
	// authenticationMgr
	// .inMemoryAuthentication()
	// .withUser("admin").password("$2a$10$X0qzsQU2QHANeSAtK6x9v.NkBT5MDjG1p6rayU8HBiR12lNAfXJVK").authorities("ROLE_USER","ROLE_ADMIN")
	// .and()
	// .withUser("user").password("$2a$10$X0qzsQU2QHANeSAtK6x9v.NkBT5MDjG1p6rayU8HBiR12lNAfXJVK").authorities(
	// "ROLE_USER");
	// }
}
/* 예전 */
/*
 * @Configuration
 * 
 * @EnableWebSecurity public class SpringSecurityConfig extends
 * WebSecurityConfigurerAdapter {
 * 
 * @Override protected void configure(HttpSecurity http) throws Exception { http
 * .authorizeRequests() .anyRequest().authenticated() .and() .formLogin()
 * .defaultSuccessUrl("/index", true) .permitAll() .and() .logout(); } }
 */