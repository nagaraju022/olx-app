package com.olx;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class OlxMasterdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxMasterdataApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Docket getCustomizedDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.olx"))
				.paths(PathSelectors.any()).build().apiInfo(getApiInfo());

	}

	private ApiInfo getApiInfo() {
		ApiInfo apiInfo = new ApiInfo("OLX MasterData Rest API Documentaion", "OLX MasterData lunched API for OLX App ",
				"3.2", "http://olx.com/termsofservice",
				new Contact("nagaraju", "http://olx.com/nagaraju", "nagaraju.kumar@zensar.com"), "GPL",
				"http://olx.com/license", new ArrayList<VendorExtension>());
		return apiInfo;
	}

}
