package com.bigtablet.bigtablethompageserver.global.infra.gcp.service.impl;

import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.FileErrorException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.FileIsEmptyException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.FileWrongTypeException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class GcpServiceImpl {

    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileName;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public String upload(MultipartFile multipartFile) throws IOException {
        checkFileIsEmpty(multipartFile);
        InputStream keyFile = ResourceUtils.getURL(keyFileName).openStream();
        String contentType = multipartFile.getContentType();
        if (contentType == null) {
            throw FileErrorException.EXCEPTION;
        }
        checkFileType(contentType);
        String uuid = UUID.randomUUID().toString();
        String imageUrl = createImageUrl(uuid);
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                .setContentType(contentType).build();
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();
        storage.create(blobInfo, multipartFile.getInputStream());
        return imageUrl;
    }

    public void checkFileIsEmpty(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw FileIsEmptyException.EXCEPTION;
        }
    }

    public void checkFileType(String contentType) {
        if (!contentType.equalsIgnoreCase("application/pdf")) {
            throw FileWrongTypeException.EXCEPTION;
        }
    }

    public String createImageUrl(String uuid) {
        return "https://storage.googleapis.com/" + bucketName + "/" + uuid;
    }

}
