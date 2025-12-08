package com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request;

import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTalentListRequest extends PageRequest {

    @NotNull
    private boolean isActive;

}
