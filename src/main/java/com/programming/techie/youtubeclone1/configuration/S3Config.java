package com.programming.techie.youtubeclone1.configuration;

import com.amazonaws.auth.*;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Bean
    public static AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIA2N3KIMHU7JE7ZSFO", "L77KYn97pf4hJ4s2Pn7aejWf7B2XNIdwFzxaHQw/");
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion("eu-west-2")
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}
