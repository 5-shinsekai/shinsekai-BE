package com.example.shinsekai.option.application;

import com.example.shinsekai.option.dto.in.ColorRequestDto;
import com.example.shinsekai.option.infrastructure.ColorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    @Transactional
    public void createColor(ColorRequestDto dto) {
        if (colorRepository.findByColorName(dto.getColorName()).isPresent()) {
            throw new IllegalArgumentException("중복된 색상입니다.");
        }

        colorRepository.save(dto.toEntity());
    }


}
