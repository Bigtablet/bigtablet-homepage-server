package com.bigtablet.bigtablethompageserver.domain.job.client.dto.request;

import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Department;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.Education;
import com.bigtablet.bigtablethompageserver.domain.job.domain.enums.RecruitType;
import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;

public record GetJobListRequest(
        PageRequest PageRequest,
        String title,
        Department department,
        Education education,
        RecruitType recruitType
) {}
