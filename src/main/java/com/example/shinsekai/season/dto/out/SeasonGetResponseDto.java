package com.example.shinsekai.season.dto.out;

import com.example.shinsekai.season.entity.Season;
import com.example.shinsekai.season.vo.out.SeasonGetResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SeasonGetResponseDto {
    private Integer id;
    private String seasonName;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public SeasonGetResponseDto(Integer id, String seasonName, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.seasonName = seasonName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static SeasonGetResponseDto from(Season season) {
        return SeasonGetResponseDto.builder()
                .id(season.getId())
                .seasonName(season.getSeasonName())
                .startDate(season.getStartDate())
                .endDate(season.getEndDate())
                .build();
    }

    public SeasonGetResponseVo toVo() {
        return SeasonGetResponseVo.builder()
                .code(id)
                .name(seasonName)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
