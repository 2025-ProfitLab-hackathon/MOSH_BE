package com.example.mosh_be.dto.alarm;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmCreateRequest {
    @NotNull
    private Long festivalId;
    @NotBlank
    private String performanceId;
    @NotNull
    @Min(1)
    @Max(240)
    private Integer notifyMinutesBefore;
}
