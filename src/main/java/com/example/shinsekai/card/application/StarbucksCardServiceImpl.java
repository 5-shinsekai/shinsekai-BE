package com.example.shinsekai.card.application;

import com.example.shinsekai.card.dto.in.StarbucksCardKeyMapRequestDto;
import com.example.shinsekai.card.dto.in.StarbucksCardRequestDto;
import com.example.shinsekai.card.dto.out.StarbucksCardResponseDto;
import com.example.shinsekai.card.entity.StarbucksCard;
import com.example.shinsekai.card.entity.StarbucksCardKeyMap;
import com.example.shinsekai.card.infrastructure.StarbucksCardRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StarbucksCardServiceImpl implements StarbucksCardService {

    private final StarbucksCardRepository starbucksCardRepository;
    private final StarbucksCardKeyMapService starbucksCardKeyMapService;

    @Override
    public List<StarbucksCardResponseDto> getStarbucksCard(String memberUuid) {
        List<StarbucksCard> starbucksCards = starbucksCardRepository.findByMemberUuid(memberUuid);
        List<StarbucksCardResponseDto> starbucksCardResponseDtos = new ArrayList<>();
        for(StarbucksCard starbucksCard : starbucksCards){
            starbucksCardResponseDtos.add(StarbucksCardResponseDto.from(starbucksCard));
        }
        return starbucksCardResponseDtos;
    }

    @Override
    @Transactional
    public void createStarbucksCard(StarbucksCardRequestDto starbucksCardDto) {
        //인증용 카드 정보 불러오기
        StarbucksCardKeyMap starbucksCardKeyMap = starbucksCardKeyMapService.getStarbucksCardKeyMap(starbucksCardDto.getCardNumber())
                .orElseThrow(()->new BaseException(BaseResponseStatus.NO_EXIST_STARBUCKS_CARD));

        boolean matchResult = starbucksCardKeyMapService.matchStarbucksCardAndPin(starbucksCardKeyMap, starbucksCardDto.getPinNumber());
        if(!matchResult) throw new BaseException(BaseResponseStatus.INVALID_STARBUCKS_CARD);

        try{
            /*
            * 임시 memberUuid
            * */
            String memberUuid = "7d8eb4ca-d560-4f45-ba76-ef9b6ce6346f";

            starbucksCardRepository.save(starbucksCardDto.toEntity(
                        memberUuid, UUID.randomUUID().toString(), starbucksCardKeyMap.getAmount()
                    )
            );

            //키맵 등록 여부 수정
            starbucksCardKeyMapService.updateStarbuckscardKeyMap(StarbucksCardKeyMapRequestDto.builder()
                            .id(starbucksCardKeyMap.getId())
                            .cardNumber(starbucksCardKeyMap.getCardNumber())
                            .pinNumber(starbucksCardKeyMap.getPinNumber())
                            .amount(starbucksCardKeyMap.getAmount())
                            .isRegistered(true)
                            .build());

        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.NO_CREATION_STARBUCKS_CARD);
        }

    }
}
