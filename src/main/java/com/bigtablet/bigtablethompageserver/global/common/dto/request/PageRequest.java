package com.bigtablet.bigtablethompageserver.global.common.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {

    @NotNull
    @Positive
    private int page;

    @NotNull
    @Positive
    private int size;

}
