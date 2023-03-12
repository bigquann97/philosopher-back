package gladiator.philosopher.common.config;

import gladiator.philosopher.common.jwt.JwtAuthenticationFilter;
import gladiator.philosopher.common.jwt.JwtTokenProvider;
import gladiator.philosopher.common.security.CustomAccessDeniedHandler;
import gladiator.philosopher.common.security.CustomAuthenticationEntryPoint;
import gladiator.philosopher.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;
  private final RedisUtil redisUtil;

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .antMatchers("/h2-console/**");
  }

  @Bean
  protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http
        .httpBasic().disable()
        .csrf().disable()
        .formLogin().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .cors().configurationSource(corsConfigurationSource())
        .and()
        .authorizeRequests()
        .antMatchers("/h2-console/**").permitAll()
        .antMatchers("/api/accounts/**").permitAll()
        .antMatchers("/api/auth/**").permitAll()
        .antMatchers("/api/rank/**").permitAll()
        .and()
        .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        .and()
        .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
        .and()
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisUtil),
            UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();

    corsConfiguration.addAllowedOriginPattern("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);

    return source;
  }

}