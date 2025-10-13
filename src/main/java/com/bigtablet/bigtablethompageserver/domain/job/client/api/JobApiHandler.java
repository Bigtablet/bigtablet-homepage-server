package com.bigtablet.bigtablethompageserver.domain.job.client.api;

import com.bigtablet.bigtablethompageserver.domain.job.application.usecase.JobUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobApiHandler {

    public final JobUseCase jobUseCase;

}
