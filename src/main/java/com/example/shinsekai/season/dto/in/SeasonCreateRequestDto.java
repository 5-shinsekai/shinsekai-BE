package com.example.shinsekai.season.dto.in;

import com.example.shinsekai.season.entity.Season;
import com.example.shinsekai.season.vo.in.SeasonCreateRequestVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SeasonCreateRequestDto {
    private String seasonName;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public SeasonCreateRequestDto(String seasonName, LocalDate startDate, LocalDate endDate) {
        this.seasonName = seasonName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static SeasonCreateRequestDto from(SeasonCreateRequestVo vo){
        return SeasonCreateRequestDto.builder()
                .seasonName(vo.getSeasonName())
                .startDate(vo.getStartDate())
                .endDate(vo.getEndDate())
                .build();
    }

    public Season toEntity(){
        return Season.builder()
                .seasonName(seasonName)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
