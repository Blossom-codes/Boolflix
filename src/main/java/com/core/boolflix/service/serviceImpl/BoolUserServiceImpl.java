package com.core.boolflix.service.serviceImpl;

import com.core.boolflix.dtos.ResponseDto;
import com.core.boolflix.dtos.UserRequestDto;
import com.core.boolflix.entities.BoolRoles;
import com.core.boolflix.entities.BoolUsers;
import com.core.boolflix.enums.BoolStatus;
import com.core.boolflix.enums.BoolUserRoles;
import com.core.boolflix.repositories.BoolRolesRepository;
import com.core.boolflix.repositories.BoolUsersRepository;
import com.core.boolflix.service.BoolUserService;
import com.core.boolflix.utils.BoolUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BoolUserServiceImpl implements BoolUserService {

    private final BoolUsersRepository boolUsersRepository;
    private final BoolRolesRepository boolRolesRepository;

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

            BoolRoles role = new BoolRoles();
            role.setUserId(boolUser.getId());
            role.setUserRole(BoolUserRoles.USER);

            boolUsersRepository.save(boolUser);
            boolRolesRepository.save(role);
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
    public ResponseDto updateUser(UserRequestDto userRequestDto, String type) {

        try {
            if (userRequestDto == null) {
                throw new RuntimeException("Please fill all the required fields");
            }

            switch (type) {
                case "NAME":

                    BoolUsers user = boolUsersRepository.findById(userRequestDto.getId()).orElseThrow(() -> new RuntimeException("Id must not be null or empty"));
                    user.setFirstName(userRequestDto.getFirstName());
                    user.setLastName(userRequestDto.getLastName());

                    boolUsersRepository.save(user);

                    break;
                case "PASSWORD":
                    BoolUsers user2 = boolUsersRepository.findById(userRequestDto.getId()).orElseThrow(() -> new RuntimeException("Id must not be null or empty"));
                    String oldPassword = user2.getPassword();
                    String newPassword = new BoolUtils().hashingInput(userRequestDto.getPassword());
//                    frontend to do the comparison for new password and confirm password
//                    to make sure they are the same
                    if (Objects.equals(oldPassword, newPassword)) {
                        throw new RuntimeException("You new password cannot be the same as your old password");
                    }

                    user2.setPassword(newPassword);
                    boolUsersRepository.save(user2);

                    break;

                case "EMAIL":

                    BoolUsers user3 = boolUsersRepository.findById(userRequestDto.getId()).orElseThrow(() -> new RuntimeException("Id must not be null or empty"));
                    String newEmail = userRequestDto.getEmail();
                    if (boolUsersRepository.findByEmail(newEmail) != null)
                    {
                        throw new RuntimeException("Email has already been taken");
                    }
//                    consider validating email in the future
                    user3.setEmail(newEmail);
                    boolUsersRepository.save(user3);
                    break;

                default:
                    throw new RuntimeException("Invalid Edit Type Parameter");
            }

            return ResponseDto.builder()
                    .responseCode(BoolUtils.SUCCESS_CODE)
                    .responseMessage(BoolUtils.SUCCESS)
                    .build();
        } catch (Exception ex) {
            return ResponseDto.builder()
                    .responseCode(BoolUtils.EXCEPTION_CODE)
                    .responseMessage(ex.getLocalizedMessage())
                    .build();
        }
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
