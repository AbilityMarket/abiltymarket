package com.example.abmarket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAspectJAutoProxy // aop 추가

// filter적용시키기
@ServletComponentScan(basePackages = {
		"com.example.filter"
})

// 외부에서 설정값 끌어오기
@PropertySource("classpath:global.properties")

// 컨트롤러, 환경설정파일
@ComponentScan(basePackages = {
		"com.example.controller",
		"com.example.config",
		"com.example.restcontroller",
		"com.example.service",
		"com.example.jwt",
		"com.example.schedule",
		"com.example.aop",
		"com.example.interceptor"
})

// 매퍼
@MapperScan(basePackages = {
		"com.example.mapper"
})

// 인테티(jpa)
@EntityScan(basePackages = { "com.example.entity" })

// 저장소(jpa)
@EnableJpaRepositories(basePackages = { "com.example.repository" })

@SpringBootApplication
public class AbmarketApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AbmarketApplication.class, args);
		System.out.println("서버구동완료");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

		return builder.sources(AbmarketApplication.class);
	}
}
