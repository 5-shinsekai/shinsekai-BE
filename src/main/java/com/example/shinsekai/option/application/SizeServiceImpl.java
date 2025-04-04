package com.example.shinsekai.option.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.option.dto.in.SizeRequestDto;
import com.example.shinsekai.option.entity.Size;
import com.example.shinsekai.option.infrastructure.SizeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;


    @Override
    @Transactional
    public void createSize(SizeRequestDto dto) {
        if (sizeRepository.findBySizeName(dto.getSizeName()).isPresent()) {
            throw new IllegalStateException("중복된 사이즈 입니다.");
        }

        sizeRepository.save(dto.toEntity());
    }

    @Override
    @Transactional
    public void updateSize(SizeRequestDto dto) {
        Size size = sizeRepository.findById(dto.getId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_OPTION));

        size.updateSizeName(dto.getSizeName());
    }
}
