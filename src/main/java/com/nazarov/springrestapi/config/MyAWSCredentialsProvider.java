package com.nazarov.springrestapi.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyAWSCredentialsProvider implements AWSCredentialsProvider {
    @Override
    public AWSCredentials getCredentials() {
        String key = System.getenv("AWS_ACCESS_KEY_ID");
        String secret = System.getenv("AWS_SECRET_ACCESS_KEY");
        if(key == null || secret ==null){
            log.error("Ошибка получения AWS_ACCESS_KEY_ID или AWS_SECRET_ACCESS_KEY");
            try {
                throw new Exception("AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        return new BasicAWSCredentials(key, secret);
    }

    @Override
    public void refresh() {

    }
}
