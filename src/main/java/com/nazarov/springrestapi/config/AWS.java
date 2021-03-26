package com.nazarov.springrestapi.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWS {

    @Bean
    public AmazonS3 s3Client(){
        return AmazonS3Client.builder()
                .withRegion(Regions.EU_WEST_3)
                .withCredentials(new MyAWSCredentialsProvider())
                .build();
    }
}