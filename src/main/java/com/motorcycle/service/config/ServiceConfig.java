package com.motorcycle.service.config;

import com.motorcycle.db.repository.api.IMotorcycleRepository;
import com.motorcycle.db.repository.api.IRoleRepository;
import com.motorcycle.db.repository.api.ITypeRepository;
import com.motorcycle.db.repository.api.IUserRepository;
import com.motorcycle.exception.CustomErrorAttributes;
import com.motorcycle.facade.mvc.api.IMvcFacade;
import com.motorcycle.facade.mvc.impl.MvcFacade;
import com.motorcycle.facade.rest.api.IRestFacade;
import com.motorcycle.facade.rest.impl.RestFacade;
import com.motorcycle.property.resolver.PropertyResolver;
import com.motorcycle.security.SecurityConfigurer;
import com.motorcycle.security.TokenProvider;
import com.motorcycle.service.api.IMotorcycleService;
import com.motorcycle.service.api.IRoleService;
import com.motorcycle.service.api.ITypeService;
import com.motorcycle.service.api.IUserService;
import com.motorcycle.service.impl.MotorcycleService;
import com.motorcycle.service.impl.RoleService;
import com.motorcycle.service.impl.TypeService;
import com.motorcycle.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ServiceConfig extends WebSecurityConfigurerAdapter {

    private final IMotorcycleRepository motorcycleRepository;
    private final ITypeRepository typeRepository;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PropertyResolver propertyResolver;

    @Bean
    public IMotorcycleService motorcycleService() {
        return new MotorcycleService(motorcycleRepository, typeRepository);
    }

    @Bean
    public ITypeService typeService() {
        return new TypeService(typeRepository);
    }

    @Bean
    public IUserService userService() {
        return new UserService(userRepository, roleRepository, passwordEncoder());
    }

    @Bean
    public IRoleService roleService() {
        return new RoleService(roleRepository);
    }

    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider(propertyResolver, userService());
    }

    @Bean
    public IRestFacade restFacade() throws Exception {
        return new RestFacade(motorcycleService(), typeService(), userService(), roleService(), authenticationManagerBean(), tokenProvider());
    }

    @Bean
    public IMvcFacade mvcFacade() {
        return new MvcFacade(typeService());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CustomErrorAttributes customErrorAttributes() {
        return new CustomErrorAttributes();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//        .antMatchers("/**").permitAll()
                .antMatchers("/motorcycles/authentication", "/main-page", "/mvc/motorcycles/type/all").permitAll()
                .antMatchers(HttpMethod.GET, "/motorcycles/**").hasAnyRole("READ", "ADMIN")
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new SecurityConfigurer(tokenProvider()));
    }
}
