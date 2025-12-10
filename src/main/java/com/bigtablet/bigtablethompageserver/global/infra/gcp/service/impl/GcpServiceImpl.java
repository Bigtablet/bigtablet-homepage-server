package com.bigtablet.bigtablethompageserver.global.infra.gcp.service.impl;

import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.FileErrorException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.FileIsEmptyException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.FileNotFoundException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.FileWrongTypeException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.StorageErrorException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.service.GcpService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class GcpServiceImpl implements GcpService {

    @Value("${spring.cloud.gcp.credentials.location}")
    private String keyFileName;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    private Storage storage;

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        checkFileIsEmpty(multipartFile);
        String contentType = multipartFile.getContentType();
        if (contentType == null) {
            throw FileErrorException.EXCEPTION;
        }
        checkFileType(contentType);
        String uuid = UUID.randomUUID().toString();
        String imageUrl = createImageUrl(uuid);
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                .setContentType(contentType).build();
        Storage storage = getStorage();
        storage.create(blobInfo, multipartFile.getInputStream());
        return imageUrl;
    }

    @Override
    public void delete(String fileUrl) {
        String fileName = extractFileNameFromUrl(fileUrl);
        Storage storage = getStorage();
        boolean deleted = storage.delete(bucketName, fileName);
        if (!deleted) {
            throw FileNotFoundException.EXCEPTION;
        }
    }

    private Storage getStorage() {
        if (storage == null) {
            try (InputStream keyFile = ResourceUtils.getURL(keyFileName).openStream()) {
                storage = StorageOptions.newBuilder()
                        .setCredentials(GoogleCredentials.fromStream(keyFile))
                        .build()
                        .getService();
            } catch (IOException | RuntimeException e) {
                throw StorageErrorException.EXCEPTION;
            }
        }
        return storage;
    }

    private String extractFileNameFromUrl(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex == -1 || lastSlashIndex == url.length() - 1) {
            throw FileNotFoundException.EXCEPTION;
        }
        return url.substring(lastSlashIndex + 1);
    }

    public void checkFileIsEmpty(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw FileIsEmptyException.EXCEPTION;
        }
    }

    public void checkFileType(String contentType) {
        if (
                !contentType.equalsIgnoreCase("application/pdf") &&
                !contentType.equalsIgnoreCase("image/png") &&
                !contentType.equalsIgnoreCase("video/mp4")
        ) {
            throw FileWrongTypeException.EXCEPTION;
        }
    }

    public String createImageUrl(String uuid) {
        return "https://storage.googleapis.com/" + bucketName + "/" + uuid;
    }

}
