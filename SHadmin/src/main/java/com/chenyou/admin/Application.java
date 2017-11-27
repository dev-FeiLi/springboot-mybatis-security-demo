package com.chenyou.admin;

import com.chenyou.admin.Utils.ApplicationConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;


@ComponentScan(basePackages ="com.chenyou.admin")
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    /**
     * 系统错误处理Bean
     *
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return (container -> {
            ErrorPage error400 = new ErrorPage(HttpStatus.BAD_REQUEST, ApplicationConstant.ERROR_PAGE_PATH);
            ErrorPage error403 = new ErrorPage(HttpStatus.FORBIDDEN, ApplicationConstant.ERROR_PAGE_PATH);
            ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, ApplicationConstant.ERROR_PAGE_PATH);
            ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, ApplicationConstant.ERROR_PAGE_PATH);
            container.addErrorPages(error400, error403, error404, error500);
        });
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
