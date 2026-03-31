package com.bigtablet.bigtablethompageserver.global.infra.gcp.service;

import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.FileErrorException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.FileIsEmptyException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.FileNotFoundException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.FileWrongTypeException;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.exception.StorageErrorException;
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
public class GcpService {

    @Value("${spring.cloud.gcp.credentials.location}")
    private String keyFileName;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    private Storage storage;

    /**
     * GCS에 파일 업로드
     * @param multipartFile MultipartFile 업로드할 파일
     * @return String 이미지 URL
     */
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

    /**
     * GCS에서 파일 삭제
     * @param fileUrl String 삭제할 파일 URL
     */
    public void delete(String fileUrl) {
        String fileName = extractFileNameFromUrl(fileUrl);
        Storage storage = getStorage();
        boolean deleted = storage.delete(bucketName, fileName);
        if (!deleted) {
            throw FileNotFoundException.EXCEPTION;
        }
    }

    /**
     * GCS Storage 클라이언트 싱글톤 조회
     * @return Storage GCS 스토리지 클라이언트
     */
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

    /**
     * URL에서 파일명 추출
     * @param url String 파일 URL
     * @return String 파일명
     */
    private String extractFileNameFromUrl(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex == -1 || lastSlashIndex == url.length() - 1) {
            throw FileNotFoundException.EXCEPTION;
        }
        return url.substring(lastSlashIndex + 1);
    }

    /**
     * 파일 비어있는지 검증
     * @param multipartFile MultipartFile 검증할 파일
     */
    public void checkFileIsEmpty(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw FileIsEmptyException.EXCEPTION;
        }
    }

    /**
     * 허용된 파일 타입인지 검증 (PDF, PNG, JPG, JPEG, MP4)
     * @param contentType String 파일 콘텐츠 타입
     */
    public void checkFileType(String contentType) {
        if (
                !contentType.equalsIgnoreCase("application/pdf") &&
                !contentType.equalsIgnoreCase("image/png") &&
                !contentType.equalsIgnoreCase("image/jpg") &&
                !contentType.equalsIgnoreCase("image/jpeg") &&
                !contentType.equalsIgnoreCase("video/mp4")
        ) {
            throw FileWrongTypeException.EXCEPTION;
        }
    }

    /**
     * GCS 이미지 URL 생성
     * @param uuid String 파일 고유 식별자
     * @return String URL
     */
    public String createImageUrl(String uuid) {
        return "https://storage.googleapis.com/" + bucketName + "/" + uuid;
    }

}
