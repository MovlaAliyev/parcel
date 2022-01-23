package com.parcel.ms.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SrpFirstStepRespDto {
    private String srpB;
    private String srpS;
}
