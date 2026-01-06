package com.bigtablet.bigtablethompageserver.domain.recruit.client.request;

import com.bigtablet.bigtablethompageserver.domain.recruit.domain.enums.Status;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRecruitListRequest extends PageRequest {

    private Long jobId;
    private Status status;

}
