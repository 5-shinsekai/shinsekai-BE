package com.example.shinsekai.option.dto.in;

import com.example.shinsekai.option.entity.Size;
import com.example.shinsekai.option.vo.in.SizeRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SizeRequestDto {

    private Long id;
    private String sizeName;

    @Builder
    public SizeRequestDto(Long id, String sizeName) {
        this.id = id;
        this.sizeName = sizeName;
    }

    public Size toEntity() {
        return Size.builder()
                .sizeName(sizeName)
                .build();
    }

    public static SizeRequestDto from(SizeRequestVo sizeRequestVo) {
        return SizeRequestDto.builder()
                .sizeName(sizeRequestVo.getSizeName().toUpperCase())
                .build();
    }

    public static SizeRequestDto from(Long id, SizeRequestVo sizeRequestVo) {
        return SizeRequestDto.builder()
                .id(id)
                .sizeName(sizeRequestVo.getSizeName().toUpperCase())
                .build();
    }

}
