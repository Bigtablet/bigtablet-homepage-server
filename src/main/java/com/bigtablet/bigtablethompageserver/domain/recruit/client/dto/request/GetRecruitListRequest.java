package com.bigtablet.bigtablethompageserver.domain.recruit.client.dto.request;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRecruitListRequest extends PageRequest {

    @NotNull
    private Long jobId;

    private Status status;

}
