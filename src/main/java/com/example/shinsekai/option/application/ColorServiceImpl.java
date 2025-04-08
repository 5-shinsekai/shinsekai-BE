package com.example.shinsekai.option.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.option.dto.in.ColorRequestDto;
import com.example.shinsekai.option.dto.out.ColorResponseDto;
import com.example.shinsekai.option.entity.Color;
import com.example.shinsekai.option.infrastructure.ColorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    @Transactional
    public void updateColor(ColorRequestDto dto) {
        Color color = colorRepository.findById(dto.getId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_OPTION));

        color.updateColorName(dto.getColorName());
    }

    @Override
    @Transactional
    public void deleteColor(Long id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_OPTION));

        colorRepository.delete(color);
    }

    @Override
    public List<ColorResponseDto> getAllColors() {
        List<Color> colors = colorRepository.findAll();
        return colors.stream()
                .map(ColorResponseDto::from)
                .toList();
    }

    @Override
    public ColorResponseDto getColor(Long id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_OPTION));
        return ColorResponseDto.from(color);
    }


}
