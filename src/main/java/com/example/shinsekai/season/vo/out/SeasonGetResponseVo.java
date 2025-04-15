package com.example.shinsekai.season.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SeasonGetResponseVo {
    private String seasonName;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public SeasonGetResponseVo(String seasonName, LocalDate startDate, LocalDate endDate) {
        this.seasonName = seasonName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
