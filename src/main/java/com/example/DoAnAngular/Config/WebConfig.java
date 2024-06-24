package com.example.DoAnAngular.Config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        converter.setObjectMapper(objectMapper);
        converter.setSupportedMediaTypes(Collections.singletonList(new MediaType("application", "json", Charset.forName("UTF-8"))));
        return converter;
    }
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.favorPathExtension(false).favorParameter(true);
//    }

//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setMaxUploadSize(10485760); // Set max upload size (10 MB, for example)
//        return resolver;
//    }
//    @Bean
//    public CommonsMultipartResolver multipartResolver() { // didn't work
//        return new CommonsMultipartResolver();
//    }
}
