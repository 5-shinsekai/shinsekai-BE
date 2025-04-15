package com.example.shinsekai.season.vo.in;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SeasonUpdateRequestVo {
    private Integer id;
    private String seasonName;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public SeasonUpdateRequestVo(Integer id, String seasonName, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.seasonName = seasonName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
