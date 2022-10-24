package ru.se4oev.tacocloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.se4oev.tacocloud.entity.User;
import ru.se4oev.tacocloud.repository.UserRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null)
                return user;

            throw new UsernameNotFoundException("User " + username + " not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .antMatchers("/design", "/orders").access("hasRole('USER')")
                .antMatchers("/", "/**").access("permitAll()")
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeRequests()
//                .antMatchers("/design", "/orders").hasRole("USER")
//                .antMatchers("/", "/**").permitAll()
//                .and()
//                .build();
//    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        List<UserDetails> usersList = new ArrayList<>();
//        usersList.add(new User(
//                "buzz", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")))
//        );
//        usersList.add(new User(
//                "woody", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")))
//        );
//        return new InMemoryUserDetailsManager(usersList);
//    }

}
