package com.example.shinsekai.option.dto.out;

import com.example.shinsekai.option.entity.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SizeResponseDto {

    private Long id;
    private String sizeName;

    @Builder
    public SizeResponseDto(Long id, String sizeName) {
        this.id = id;
        this.sizeName = sizeName;
    }

    public static SizeResponseDto from(Size size) {
        return SizeResponseDto.builder()
                .id(size.getId())
                .sizeName(size.getSizeName())
                .build();
    }
}
