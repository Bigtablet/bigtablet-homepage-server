package com.bigtablet.bigtablethompageserver.global.infra.gcp.api;

import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.service.GcpService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/gcp")
public class GcpApiHandler {

    private final GcpService gcpService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponseData<String> upload(@RequestPart @NotNull final MultipartFile multipartFile) throws IOException {
        return BaseResponseData.created(
                "업로드 성공",
                gcpService.upload(multipartFile));
    }

}
