package com.example.shinsekai.season.vo.in;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SeasonCreateRequestVo {
    private String seasonName;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public SeasonCreateRequestVo(String seasonName, LocalDate startDate, LocalDate endDate) {
        this.seasonName = seasonName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
