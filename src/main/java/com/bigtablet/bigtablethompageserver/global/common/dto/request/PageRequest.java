package com.bigtablet.bigtablethompageserver.global.common.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PageRequest(
        @NotNull
        @Positive
        int page,
        @NotNull
        @Positive
        int size
) {}
