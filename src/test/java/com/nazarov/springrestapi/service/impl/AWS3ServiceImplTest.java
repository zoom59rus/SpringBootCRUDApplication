package com.nazarov.springrestapi.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AWS3ServiceImplTest {

    @Autowired
    private AWS3ServiceImpl s3Service;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createBucket() {
    }

    @Test
    void getBucketList() {
    }

    @Test
    void remove() {
    }

    @Test
    void upload() {
    }

    @Test
    void getFileList() {
    }

    @Test
    void get() {
    }

    @Test
    void writeObjectToPath() {
    }
}