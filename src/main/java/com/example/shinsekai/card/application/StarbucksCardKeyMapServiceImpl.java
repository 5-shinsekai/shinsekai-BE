package com.example.shinsekai.card.application;

import com.example.shinsekai.card.dto.in.StarbucksCardKeyMapRequestDto;
import com.example.shinsekai.card.entity.StarbucksCardKeyMap;
import com.example.shinsekai.card.infrastructure.StarbucksCardKeyMapRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StarbucksCardKeyMapServiceImpl implements StarbucksCardKeyMapService {

    private final StarbucksCardKeyMapRepository starbucksCardKeyMapRepository;

    @Override
    public boolean matchStarbucksCardAndPin(StarbucksCardKeyMap starbucksCardKeyMap,String pinNumber) {
        return BCrypt.checkpw(pinNumber, starbucksCardKeyMap.getPinNumber());
    }

    @Override
    public Optional<StarbucksCardKeyMap> getStarbucksCardKeyMap(String cardNumber) {
        return starbucksCardKeyMapRepository.findByCardNumberAndIsRegistered(cardNumber,false);
    }

    @Override
    public void createStarbucksCardKeyMap(StarbucksCardKeyMapRequestDto starbucksCardKeyMapRequestDto) {
        String hashedPin = BCrypt.hashpw(starbucksCardKeyMapRequestDto.getPinNumber(), BCrypt.gensalt()); //8자리

        try {
            starbucksCardKeyMapRepository.save(StarbucksCardKeyMap.builder()
                    .cardNumber(starbucksCardKeyMapRequestDto.getCardNumber()) //16자리
                    .pinNumber(hashedPin)
                    .isRegistered(starbucksCardKeyMapRequestDto.isRegistered())
                    .amount(starbucksCardKeyMapRequestDto.getAmount())
                    .build());
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.DUPLICATED_STARBUCKS_CARD);
        }
    }

    @Override
    public void updateStarbuckscardKeyMap(StarbucksCardKeyMapRequestDto starbucksCardKeyMapRequestDto) {
        starbucksCardKeyMapRepository.save(StarbucksCardKeyMap.builder()
                        .id(starbucksCardKeyMapRequestDto.getId())
                        .cardNumber(starbucksCardKeyMapRequestDto.getCardNumber())
                        .pinNumber(starbucksCardKeyMapRequestDto.getPinNumber())
                        .isRegistered(starbucksCardKeyMapRequestDto.isRegistered())
                        .amount(starbucksCardKeyMapRequestDto.getAmount())
                        .build());
    }
}
