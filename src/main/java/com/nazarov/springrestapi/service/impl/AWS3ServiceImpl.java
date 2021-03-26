package com.nazarov.springrestapi.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.nazarov.springrestapi.service.AWS3Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class AWS3ServiceImpl implements AWS3Service {

    private final AmazonS3 s3Client;

    public AWS3ServiceImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public boolean isExist(String bucketName) {
        boolean result = false;

        for (int i = 0; i < 50; ) {
            try {
                result = !s3Client.doesBucketExistV2(bucketName);
                System.out.println(result);
                if (result) {
                    System.out.println("Имя " + bucketName + " свободно.");
                } else {
                    System.out.println("Имя " + bucketName + " занято. Выберите другое имя.");
                }

                return result;
            } catch (AmazonServiceException e) {
                System.err.println("Ошибка AmazonServiceException: ");
                e.printStackTrace();
            } catch (SdkClientException e) {
                if (e.getMessage().matches("(.*)Connection refused(.*)")) {
                    System.err.println("Ошибка подключения, пробуем еще " + (50 - ++i) + " раз.");
                } else {
                    System.err.println("Другая ошибка SdkClientException: ");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.err.println("Ошибка Exception другого характера: ");
                e.printStackTrace();
            }
        }
        System.err.println("Ошибка в подключении или сервер не доступен. Попробуйте позднее.");

        return result;
    }

    @Override
    public Bucket createBucket(String bucketName) {
        Bucket bucket = null;

        try {
            if (!s3Client.doesBucketExistV2(bucketName)) {
                bucket = s3Client.createBucket(bucketName);
            }
        } catch (AmazonS3Exception a) {
            log.error("IN createBucket - Удаленный сервер получил запрос, но не может его обработать. Сообщение об ошибке: \n{}", a.getMessage());
        } catch (SdkClientException s) {
            log.error("IN createBucket - Удаленный сервер не доступен или не отвечает. Сообщение об ошибке: \n{}", s.getMessage());
        }

        return bucket;
    }

    @Override
    public List<Bucket> getBucketList() {

        try {
            return s3Client.listBuckets();
        } catch (AmazonS3Exception a) {
            log.error("IN getBucketList - Удаленный сервер получил запрос, но не может его обработать. Сообщение об ошибке: \n{}", a.getMessage());
        } catch (SdkClientException s) {
            log.error("IN getBucketList - Удаленный сервер не доступен или не отвечает. Сообщение об ошибке: \n{}", s.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public boolean removeBucket(String bucketName) {
        try {
            ObjectListing objectListing = s3Client.listObjects(bucketName);
            while (true) {
                Iterator<S3ObjectSummary> objIter = getFileList(bucketName).getObjectSummaries().iterator();
                while (objIter.hasNext()) {
                    s3Client.deleteObject(bucketName, objIter.next().getKey());
                }

                if (objectListing.isTruncated()) {
                    objectListing = s3Client.listNextBatchOfObjects(objectListing);
                } else {
                    break;
                }
            }

            s3Client.deleteBucket(bucketName);
            return true;
        } catch (AmazonS3Exception a) {
            log.error("IN getBucketList - Удаленный сервер получил запрос, но не может его обработать. Сообщение об ошибке: \n{}", a.getMessage());
        } catch (SdkClientException s) {
            log.error("IN getBucketList - Удаленный сервер не доступен или не отвечает. Сообщение об ошибке: \n{}", s.getMessage());
        }

        return false;
    }

    @Override
    public PutObjectResult upload(String bucketName, String filePath) {
        File file = new File(filePath);

        try {
            return s3Client.putObject(bucketName, file.getName(), file);
        } catch (AmazonS3Exception a) {
            log.error("IN upload - Удаленный сервер получил запрос, но не может его обработать. Сообщение об ошибке: \n{}", a.getMessage());
        } catch (SdkClientException s) {
            log.error("IN upload - Удаленный сервер не доступен или не отвечает. Сообщение об ошибке: \n{}", s.getMessage());
        }

        return new PutObjectResult();
    }

    @Override
    public ObjectListing getFileList(String bucketName) {
        try {
            return s3Client.listObjects(bucketName);
        } catch (AmazonS3Exception a) {
            log.error("IN getBucketList - Удаленный сервер получил запрос, но не может его обработать. Сообщение об ошибке: \n{}", a.getMessage());
        } catch (SdkClientException s) {
            log.error("IN getBucketList - Удаленный сервер не доступен или не отвечает. Сообщение об ошибке: \n{}", s.getMessage());
        }

        return new ObjectListing();
    }

    @Override
    public S3Object getS3Object(String bucketName, String fileKey) {
        S3Object s3Object = null;

        try {
            s3Object = s3Client.getObject(bucketName, fileKey);
        } catch (AmazonS3Exception a) {
            log.error("IN createBucket - Удаленный сервер получил запрос, но не может его обработать. Сообщение об ошибке: \n{}", a.getMessage());
        } catch (SdkClientException s) {
            log.error("IN createBucket - Удаленный сервер не доступен или не отвечает. Сообщение об ошибке: \n{}", s.getMessage());
        }

        return s3Object;
    }

    @Override
    public void writeObjectToPath(String bucketName, String fileKey, String destinationPath) throws IOException {
        S3Object object = getS3Object(bucketName, fileKey);

        if (object != null) {
            FileUtils.copyInputStreamToFile(object.getObjectContent(), new java.io.File(destinationPath + "/" + fileKey));
        }
    }
}