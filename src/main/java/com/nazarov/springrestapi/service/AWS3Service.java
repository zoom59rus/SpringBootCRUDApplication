package com.nazarov.springrestapi.service;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import java.io.IOException;
import java.util.List;

public interface AWS3Service {

    boolean isExist(String bucketName);
    Bucket createBucket(String bucketName);
    List<Bucket> getBucketList();
    boolean removeBucket(String bucketName);
    PutObjectResult upload(String bucketName, String filePath);
    ObjectListing getFileList(String bucketName);
    S3Object getS3Object(String bucketName, String fileKey);
    void writeObjectToPath(String bucketName, String fileKey, String destinationPath) throws IOException;
}