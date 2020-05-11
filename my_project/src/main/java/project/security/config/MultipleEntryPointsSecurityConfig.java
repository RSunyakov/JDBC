package project.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan({"project"})
public class MultipleEntryPointsSecurityConfig {
    @Configuration
    @Order(1)
    public static class RestConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        @Qualifier("jwtAuthenticationProvider")
        private AuthenticationProvider authenticationProvider;

        @Autowired
        @Qualifier(value = "jwtAuthenticationFilter")
        private GenericFilterBean jwtAuthenticationFilter;


        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                    .cors()
                    .and()
                    .csrf().disable()
                    .addFilterAt(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.formLogin().disable();
            http.logout().disable();
            http.httpBasic().disable();
        }

        @Autowired
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider);
        }
    }

    @Configuration
    @Order(2)
    public static class WebSecurityConfig1 extends WebSecurityConfigurerAdapter {
        @Autowired
        @Qualifier(value = "customUserDetailsService")
        private UserDetailsService userDetailsService;
        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

        @Autowired
        public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/**");
            http.authorizeRequests().and()
                    .rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository());

            http.formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error")
                    .permitAll();
            http.addFilter(new AnonymousAuthenticationFilter("anonymous"));

            http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                    .deleteCookies("JSESSIONID", "remember-me")
                    .invalidateHttpSession(true);


        }
        @Autowired
        private DataSource dataSource;

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
            jdbcTokenRepository.setDataSource(dataSource);
            return jdbcTokenRepository;
        }

    }


}