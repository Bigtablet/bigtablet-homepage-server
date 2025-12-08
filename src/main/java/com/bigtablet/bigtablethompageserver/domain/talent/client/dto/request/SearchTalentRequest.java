package com.bigtablet.bigtablethompageserver.domain.talent.client.dto.request;

import com.bigtablet.bigtablethompageserver.global.common.dto.request.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchTalentRequest extends PageRequest {

    private String keyword;

}
