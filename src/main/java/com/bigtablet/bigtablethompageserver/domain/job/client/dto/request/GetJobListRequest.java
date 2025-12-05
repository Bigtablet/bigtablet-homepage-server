package com.bigtablet.bigtablethompageserver.domain.job.client.dto.request;

import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetJobListRequest extends PageRequest {

    private String title;
    private Department department;
    private Education education;
    private RecruitType recruitType;

}
