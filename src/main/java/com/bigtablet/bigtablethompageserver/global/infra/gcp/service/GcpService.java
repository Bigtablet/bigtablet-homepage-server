package com.bigtablet.bigtablethompageserver.global.infra.gcp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GcpService {

    String upload(MultipartFile multipartFile) throws IOException;

    void delete(String fileUrl);

}
