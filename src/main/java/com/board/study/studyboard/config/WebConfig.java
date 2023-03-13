package com.board.study.studyboard.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
//                        .allowedOrigins(
//                                "http://localhost:3000",
//                                "http://192.168.0.16:3000"
//                        )
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
                        .allowedHeaders("*")
                        .allowCredentials(false);

/*
    addMapping("/**")은 모든 URL에 대해 CORS 규칙이 적용된다는 것을 의미합니다.
    allowedOrigins("*")은 모든 도메인에서의 요청을 허용한다는 것을 의미합니다
    allowedHeaders("*")은 모든 헤더를 허용한다는 것을 의미합니다.
    allowCredentials(true)은 쿠키와 같은 인증정보를 허용한다는 것을 의미합니다.
 */
            }
        };
    }

    @Bean
    public Validator getValidator(){
        return new LocalValidatorFactoryBean();
    }
}

// 파일업로드
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("/files/**")
//                .addResourceLocations("file:/secureapp/src/main/resources/static/files/");
//
//
//
//    }
//}


