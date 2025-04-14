package com.example.shinsekai.season.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.season.dto.in.SeasonCreateRequestDto;
import com.example.shinsekai.season.dto.in.SeasonUpdateRequestDto;
import com.example.shinsekai.season.dto.out.SeasonGetResponseDto;
import com.example.shinsekai.season.entity.Season;
import com.example.shinsekai.season.infrastructure.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SeasonServiceImpl implements SeasonService {
    private final SeasonRepository seasonRepository;

    public void createSeason(SeasonCreateRequestDto seasonCreateRequestDto) {
        seasonRepository.save(seasonCreateRequestDto.toEntity());
    }

    @Override
    public SeasonGetResponseDto getSeason(Integer seasonId) {
        return SeasonGetResponseDto.from(seasonRepository.findById(seasonId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_SEASON)));
    }

    @Override
    public List<SeasonGetResponseDto> getAllSeason() {
        return seasonRepository.findAll(Sort.by(Sort.Order.asc("startDate")))
                .stream()
                .map(SeasonGetResponseDto::from)
                .toList();
    }

    @Override
    public void updateSeason(SeasonUpdateRequestDto seasonUpdateRequestDto) {
        Season season = seasonRepository.findById(seasonUpdateRequestDto.getId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_SEASON));

        seasonRepository.save(seasonUpdateRequestDto.toEntity(season));
    }


    public void deleteSeason(Integer seasonId) {
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_SEASON));

        seasonRepository.delete(season);
    }
}
