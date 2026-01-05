package faceweb.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

//    //계층권한
//    @Bean
//    public RoleHierarchyImpl roleHierarchy(){
//        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
//
//        hierarchy.setHierarchy("ROLE_ADIN > ROLE_PROFESSIONAL > ROLE_USER");
//
//        return hierarchy;
//    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/images/**", "/css/**", "/scripts/**");
    }

    @Bean
    @Order(1)
    public SecurityFilterChain firstChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .securityMatchers((auth)->auth
                        .requestMatchers("/", "/user/**", "/board/**", "/mypage/**", "/bad-access"));

        //접근권한
        httpSecurity
                .authorizeHttpRequests((auth)->auth
                        .requestMatchers("/", "/user/signup", "/user/signup-tac", "/user/face", "/board/user",
                                 "/board/user/article/*", "/board/reply", "/user/face/upload", "/user/face/recommend")
                        .permitAll()

                        .requestMatchers("/board/user/write","/mypage/**","board/user/article/*/delete",
                                "/board/user/*/articleRateUp","/board/user/*/articleRateDown")
                        .hasAnyRole("USER", "PROFESSIONAL", "ADMIN")

                        .requestMatchers("/board/professional/**")
                        .hasAnyRole("PROFESSIONAL", "ADMIN")
                );

        //로그인 설정
        httpSecurity
                .formLogin((auth)->auth
                        .loginPage("/user/login")
                        .loginProcessingUrl("/user/login")
                        //.successForwardUrl("/")
                        .permitAll()
                );

        //로그아웃 설정
        httpSecurity
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/user/logout").permitAll()
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)

                );

        httpSecurity
                .sessionManagement((auth)->auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                );

        httpSecurity
                .sessionManagement((auth)->auth
                        .sessionFixation().changeSessionId()
                );

        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain secondChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .securityMatchers((auth)->auth
                        .requestMatchers("/admin/**"));

        httpSecurity.authorizeHttpRequests((auth)->auth
                .requestMatchers("/admin/front").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN"));

        httpSecurity
                .formLogin((auth)->auth
                        .loginPage("/admin/front")
                        .loginProcessingUrl("/admin/login").permitAll()
                        .defaultSuccessUrl("/admin/board")
                );

        return httpSecurity.build();
    }
}
