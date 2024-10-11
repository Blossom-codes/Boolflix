package com.core.bingehaven.service.serviceImpl;

import com.core.bingehaven.config.JWTTokenProvider;
import com.core.bingehaven.dtos.*;
import com.core.bingehaven.enums.BnhUpdateTypes;
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
import java.util.Locale;
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
    public ResponseDto updateUser(GeneralEditRequestDto editRequestDto) {
        try {
            if (editRequestDto == null) {
                throw new RuntimeException("Please fill all the required fields");
            }

            BnhUsers user = bnhUsersRepository.findById(editRequestDto.getId()).orElseThrow(() -> new RuntimeException("User not found"));
            BnhUsers ifUserExist = bnhUsersRepository.findByUsername(user.getUsername());
            if (ifUserExist != null && !Objects.equals(editRequestDto.getUsername(), user.getUsername())) {
                throw new RuntimeException("Sorry, " + editRequestDto.getUsername() + " has already been taken");
            }
            user.setFirstName(editRequestDto.getFirstName());
            user.setLastName(editRequestDto.getLastName());
            user.setUsername(editRequestDto.getUsername());
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
    public ResponseDto advanceUserUpdate(AdvanceEditReqDto editReqDto, BnhUpdateTypes type) {

        try {
            if (editReqDto == null) {
                throw new RuntimeException("Please fill all the required fields");
            }

            switch (type) {
                case PASSWORD:
                    BnhUsers user2 = bnhUsersRepository.findById(editReqDto.getId()).orElseThrow(() -> new RuntimeException("Id must not be null or empty"));
                    String oldPassword = user2.getPassword();
                    String newPassword = new BnhUtils().hashingInput(editReqDto.getPassword());
//                    frontend to do the comparison for new password and confirm password
//                    to make sure they are the same
                    if (Objects.equals(oldPassword, newPassword)) {
                        throw new RuntimeException("You new password cannot be the same as your old password");
                    }

                    user2.setPassword(newPassword);
                    bnhUsersRepository.save(user2);

                    break;

                case EMAIL:

                    BnhUsers user3 = bnhUsersRepository.findById(editReqDto.getId()).orElseThrow(() -> new RuntimeException("Id must not be null or empty"));
                    String newEmail = editReqDto.getEmail();
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
            BnhUsers user = bnhUsersRepository.findById(id).orElseThrow(() -> new RuntimeException("Id must not be null or empty"));
            return ResponseDto.builder()
                    .responseCode(BnhUtils.SUCCESS_CODE)
                    .responseMessage(BnhUtils.SUCCESS)
                    .info(UsersDto.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .status(user.getStatus())
                            .createdAt(user.getCreatedAt())
                            .updatedAt(user.getUpdatedAt())
                            .role(user.getRole())
                            .build())
                    .build();
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

    private static char firstChar = 'A';
    private static char secondChar = 'A';
    private static int number = 0;

    public synchronized String generateTerminalId() {
        String subId = String.format("%02d%c%c", number, firstChar, secondChar);
        String testTerminalId = "2011" + subId;
//            MtmTerminals ifExistingTerminalId = terminalsRepository.findByTerminalId(testTerminalId);
        String ifExistingTerminalId = null;
        // Generate next ID if this one already exists
        if (ifExistingTerminalId != null) {
            incrementId(); // Move to the next possible ID
            return generateTerminalId(); // Recursively generate the next ID
        } else {
            incrementId(); // Move to the next possible ID for future calls
            return testTerminalId;
        }
    }

    private void incrementId() {
        number++;
        if (number >= 100) {
            number = 0;
            secondChar++;
            if (secondChar > 'Z') {
                secondChar = 'A';
                firstChar++;
            }
        }
    }


}
