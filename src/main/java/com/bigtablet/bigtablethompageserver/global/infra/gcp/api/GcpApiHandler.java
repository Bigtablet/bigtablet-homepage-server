package com.bigtablet.bigtablethompageserver.global.infra.gcp.api;

import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponse;
import com.bigtablet.bigtablethompageserver.global.common.dto.response.BaseResponseData;
import com.bigtablet.bigtablethompageserver.global.infra.gcp.service.GcpService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse upload(
            @RequestParam @NotNull
            @URL(message = "유효한 URL 형식이어야 합니다.")
            final String fileUrl
    ) throws IOException {
        gcpService.delete(fileUrl);
        return BaseResponse.ok("삭제 성공");
    }

}
