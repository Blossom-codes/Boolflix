package com.core.bingehaven.service.serviceImpl;

import com.core.bingehaven.config.JWTTokenProvider;
import com.core.bingehaven.dtos.LoginRequestDto;
import com.core.bingehaven.dtos.ResponseDto;
import com.core.bingehaven.dtos.UserRequestDto;
import com.core.bingehaven.repositories.BnhUsersRepository;
import com.core.bingehaven.entities.BnhUsers;
import com.core.bingehaven.enums.BnhStatus;
import com.core.bingehaven.enums.BnhUserRoles;
import com.core.bingehaven.service.BnhUserService;
import com.core.bingehaven.utils.BnhUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BnhUserServiceImpl implements BnhUserService {

    private final BnhUsersRepository bnhUsersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public ResponseDto saveNewUser(UserRequestDto userRequestDto) {

        try {
            if (userRequestDto == null) {
                throw new RuntimeException("Please fill all the required fields");
            }
            if (bnhUsersRepository.findByUsername(userRequestDto.getUsername()) != null) {
                throw new RuntimeException("Hey, Sorry that username has already been taken");
            }
            if (bnhUsersRepository.findByEmail(userRequestDto.getEmail()) != null) {
                throw new RuntimeException("Hey, Sorry that email has already been taken");
            }


            BnhUsers boolUser = new BnhUsers();
            boolUser.setFirstName(userRequestDto.getFirstName());
            boolUser.setLastName(userRequestDto.getLastName());
            boolUser.setUsername(userRequestDto.getUsername());
            boolUser.setEmail(userRequestDto.getEmail());
            boolUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            boolUser.setStatus(BnhStatus.ACTIVE);
            boolUser.setCreatedAt(LocalDateTime.now().withNano(0));
            boolUser.setRole(BnhUserRoles.USER);

            bnhUsersRepository.save(boolUser);
            return ResponseDto.builder()
                    .responseCode(BnhUtils.SUCCESS_CODE)
                    .responseMessage(BnhUtils.SUCCESS_MESSAGE)
                    .build();
        } catch (Exception ex) {
            return ResponseDto.builder()
                    .responseCode(BnhUtils.EXCEPTION_CODE)
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

                    BnhUsers user = bnhUsersRepository.findById(userRequestDto.getId()).orElseThrow(() -> new RuntimeException("Id must not be null or empty"));
                    user.setFirstName(userRequestDto.getFirstName());
                    user.setLastName(userRequestDto.getLastName());

                    bnhUsersRepository.save(user);

                    break;
                case "PASSWORD":
                    BnhUsers user2 = bnhUsersRepository.findById(userRequestDto.getId()).orElseThrow(() -> new RuntimeException("Id must not be null or empty"));
                    String oldPassword = user2.getPassword();
                    String newPassword = new BnhUtils().hashingInput(userRequestDto.getPassword());
//                    frontend to do the comparison for new password and confirm password
//                    to make sure they are the same
                    if (Objects.equals(oldPassword, newPassword)) {
                        throw new RuntimeException("You new password cannot be the same as your old password");
                    }

                    user2.setPassword(newPassword);
                    bnhUsersRepository.save(user2);

                    break;

                case "EMAIL":

                    BnhUsers user3 = bnhUsersRepository.findById(userRequestDto.getId()).orElseThrow(() -> new RuntimeException("Id must not be null or empty"));
                    String newEmail = userRequestDto.getEmail();
                    if (bnhUsersRepository.findByEmail(newEmail) != null) {
                        throw new RuntimeException("Email has already been taken");
                    }
//                    consider validating email in the future
                    user3.setEmail(newEmail);
                    bnhUsersRepository.save(user3);
                    break;

                default:
                    throw new RuntimeException("Invalid Edit Type Parameter");
            }

            return ResponseDto.builder()
                    .responseCode(BnhUtils.SUCCESS_CODE)
                    .responseMessage(BnhUtils.SUCCESS)
                    .build();
        } catch (Exception ex) {
            return ResponseDto.builder()
                    .responseCode(BnhUtils.EXCEPTION_CODE)
                    .responseMessage(ex.getLocalizedMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto deleteUser(Long id) {
        return null;
    }

    @Override
    public ResponseDto fetchUser(Long id) {

        try {
            if (id == null) {
                throw new RuntimeException("User not found");
            }


        } catch (Exception ex) {

        }
        return null;
    }

    @Override
    public ResponseDto login(LoginRequestDto requestDto) {

        try {
            Authentication authentication = null;

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getIdentifier(), requestDto.getPassword())
            );

            return ResponseDto.builder()
                    .responseCode(BnhUtils.LOGIN_SUCCESS_CODE)
                    .responseMessage(BnhUtils.LOGIN_WELCOME_MESSAGE)
                    .info(jwtTokenProvider.generateToken(authentication))
                    .build();
        } catch (Exception ex) {
            return ResponseDto.builder()
                    .responseCode(BnhUtils.EXCEPTION_CODE)
                    .responseMessage(ex.getMessage())
                    .build();
        }
    }


    @Override
    public ResponseDto addItemToMyList(Long movieId, Long userId, String type) {
        return null;
    }
}
