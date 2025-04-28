package com.example.shinsekai.season.application;

import com.example.shinsekai.season.dto.in.SeasonCreateRequestDto;
import com.example.shinsekai.season.dto.in.SeasonUpdateRequestDto;
import com.example.shinsekai.season.dto.out.SeasonGetResponseDto;

import java.util.List;

public interface SeasonService {
    void createSeason(SeasonCreateRequestDto seasonCreateRequestDto);

    SeasonGetResponseDto getSeason(Integer seasonId);

    List<SeasonGetResponseDto> getAllSeason();

    void updateSeason(SeasonUpdateRequestDto seasonUpdateRequestDto);

    void deleteSeason(Integer seasonId);
}
