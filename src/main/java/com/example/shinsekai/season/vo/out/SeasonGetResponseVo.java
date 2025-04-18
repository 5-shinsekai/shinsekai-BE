package com.example.shinsekai.season.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SeasonGetResponseVo {
    private Integer code;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public SeasonGetResponseVo(Integer code, String name, LocalDate startDate, LocalDate endDate) {
        this.code = code;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
