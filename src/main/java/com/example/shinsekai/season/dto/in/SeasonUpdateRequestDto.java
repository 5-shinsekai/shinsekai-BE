package com.example.shinsekai.season.dto.in;

import com.example.shinsekai.season.entity.Season;
import com.example.shinsekai.season.vo.in.SeasonUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SeasonUpdateRequestDto {
    private Integer id;
    private String seasonName;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public SeasonUpdateRequestDto(Integer id, String seasonName, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.seasonName = seasonName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static SeasonUpdateRequestDto from(SeasonUpdateRequestVo seasonUpdateRequestVo) {
        return SeasonUpdateRequestDto.builder()
                .id(seasonUpdateRequestVo.getId())
                .seasonName(seasonUpdateRequestVo.getSeasonName())
                .startDate(seasonUpdateRequestVo.getStartDate())
                .endDate(seasonUpdateRequestVo.getEndDate())
                .build();
    }

    public Season toEntity(Season season) {
        return Season.builder()
                .id(season.getId())
                .seasonName(seasonName == null ? season.getSeasonName() : seasonName)
                .startDate(startDate == null ? season.getStartDate() : startDate)
                .endDate(endDate == null ? season.getEndDate() : endDate)
                .build();
    }
}
