package com.core.boolflix.service.serviceImpl;

import com.core.boolflix.dtos.ResponseDto;
import com.core.boolflix.dtos.UserRequestDto;
import com.core.boolflix.entities.BoolUsers;
import com.core.boolflix.enums.BoolStatus;
import com.core.boolflix.repositories.BoolUsersRepository;
import com.core.boolflix.service.BoolUserService;
import com.core.boolflix.utils.BoolUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BoolUserServiceImpl implements BoolUserService {

    private final BoolUsersRepository boolUsersRepository;

    @Override
    public ResponseDto saveNewUser(UserRequestDto userRequestDto) {

        try {
            if (userRequestDto == null) {
                throw new RuntimeException("Please fill all the required fields");
            }
            if (boolUsersRepository.findByUsername(userRequestDto.getUsername()) != null) {
                throw new RuntimeException("Hey, Sorry that username has already been taken");
            }
            if (boolUsersRepository.findByEmail(userRequestDto.getEmail()) != null) {
                throw new RuntimeException("Hey, Sorry that email has already been taken");
            }

            String password = new BoolUtils().hashingInput(userRequestDto.getPassword());

            BoolUsers boolUser = new BoolUsers();
            boolUser.setFirstName(userRequestDto.getFirstName());
            boolUser.setLastName(userRequestDto.getLastName());
            boolUser.setUsername(userRequestDto.getUsername());
            boolUser.setEmail(userRequestDto.getEmail());
            boolUser.setPassword(password);
            boolUser.setStatus(BoolStatus.ACTIVE);
            boolUser.setCreatedAt(LocalDateTime.now().withNano(0));

            boolUsersRepository.save(boolUser);
            return ResponseDto.builder()
                    .responseCode(BoolUtils.SUCCESS_CODE)
                    .responseMessage(BoolUtils.SUCCESS_MESSAGE)
                    .build();
        } catch (Exception ex) {
            return ResponseDto.builder()
                    .responseCode(BoolUtils.EXCEPTION_CODE)
                    .responseMessage(ex.getLocalizedMessage())
                    .build();

        }

    }

    @Override
    public ResponseDto updateUser(UserRequestDto userRequestDto) {
        return null;
    }

    @Override
    public ResponseDto deleteUser(Long id) {
        return null;
    }

    @Override
    public ResponseDto getUsers(Long id) {
        return null;
    }

    @Override
    public ResponseDto addItemToMyList(Long movieId, Long userId, String type) {
        return null;
    }
}
