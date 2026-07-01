package com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request;

import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchTalentRequest extends PageRequest {

    @NotBlank
    @Size(max = 100)
    private String keyword;

}
