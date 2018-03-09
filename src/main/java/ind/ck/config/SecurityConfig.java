package ind.ck.config;

import ind.ck.filter.MyAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author BJQXDN0626
 * @create 2018/3/5
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/css/**", "/index", "/acl/**").permitAll() //, "/acl/**"
//                .antMatchers("/user/**").hasRole("USER")
//                .and()
//                .formLogin()
//                .loginPage("/login").failureUrl("/login-error");
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login","/login/form**","/register",
                        "/logout","/rest/users/login",
                        "/css/**",
                        "/public", "/index", "/resources/**").permitAll() // #4
                .antMatchers("/admin","/admin/**").hasRole("ADMIN") // #6
                .anyRequest().authenticated() // 7
                .and().addFilterBefore(authenticationFilter(),UsernamePasswordAuthenticationFilter.class)
                .httpBasic().authenticationEntryPoint(new DistributeAuthEntryPoint("/login/form"))
                .and()
                //bcrypt "123456a";//$2a$10$ofPkBDUezOJp6Sik63Q/0.QlU8a1itEyzldjSXqfn2nDPqXjN0Ljm
                .formLogin()  // #8
                .loginPage("/login") // #9
                .loginProcessingUrl("/login/form")
                .failureUrl("/login/form?error")
                .permitAll(); // #5
    }

    @Override
    protected void configure(AuthenticationManagerBuilder registry) throws Exception {
        registry.userDetailsService(customUserDetailsService);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 新建filter
     * @return
     */
    @Bean
    public MyAuthenticationFilter authenticationFilter() {
        MyAuthenticationFilter authFilter = new MyAuthenticationFilter();
        authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login/form","POST"));
        try {
            authFilter.setAuthenticationManager(this.authenticationManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //		SavedRequestAwareAuthenticationSuccessHandler sra = new SavedRequestAwareAuthenticationSuccessHandler();
        //		sra.setDefaultTargetUrl();
        //		authFilter.setAuthenticationSuccessHandler(sra);
        //you must set FailureHandler but loss SuccessHandler is acceptable,why?
        authFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?loginError"));
        authFilter.setUsernameParameter("username");
        authFilter.setPasswordParameter("password");
        return authFilter;
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .passwordEncoder(new PasswordEncoder() {
//                    @Override
//                    public String encode(CharSequence charSequence) {
//                        return charSequence.toString();
//                    }
//
//                    @Override
//                    public boolean matches(CharSequence charSequence, String s) {
//                        return s.equals(charSequence.toString());
//                    }
//                })
//                .withUser("user").password("password").roles("USER");
//    }
}
