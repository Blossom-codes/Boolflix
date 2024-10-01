package com.core.boolflix.service.serviceImpl;

import com.core.boolflix.dtos.ResponseDto;
import com.core.boolflix.dtos.UserRequestDto;
import com.core.boolflix.entities.BoolUsers;
import com.core.boolflix.enums.BoolStatus;
import com.core.boolflix.repositories.BoolUsersRepository;
import com.core.boolflix.service.UserService;
import com.core.boolflix.utils.BoolUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final BoolUsersRepository boolUsersRepository;

    @Override
    public ResponseDto saveNewUser(UserRequestDto userRequestDto) {

        try {
            BoolUsers boolUser = new BoolUsers();
            boolUser.setFirstName(userRequestDto.getFirstName());
            boolUser.setLastName(userRequestDto.getLastName());
            boolUser.setUsername(userRequestDto.getUsername());
            boolUser.setEmail(userRequestDto.getEmail());
            boolUser.setPassword(userRequestDto.getPassword());
            boolUser.setStatus(BoolStatus.ACTIVE);
            boolUser.setCreatedAt(LocalDateTime.now());

            boolUsersRepository.save(boolUser);

            return ResponseDto.builder()
                    .responseCode(BoolUtils.SUCCESS_CODE)
                    .responseMessage(BoolUtils.SUCCESS_MESSAGE)
                    .build();
        } catch (Exception ex) {
            return ResponseDto.builder()
                    .responseCode(BoolUtils.EXCEPTION_CODE)
                    .responseMessage(BoolUtils.EXCEPTION_MESSAGE + ex.getLocalizedMessage())
                    .build();

        }

    }
}
