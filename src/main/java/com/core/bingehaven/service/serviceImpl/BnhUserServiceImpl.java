package com.core.bingehaven.service.serviceImpl;

import com.core.bingehaven.config.JWTTokenProvider;
import com.core.bingehaven.dtos.global.ResponseDto;
import com.core.bingehaven.dtos.user.*;
import com.core.bingehaven.entities.BnhTokens;
import com.core.bingehaven.repositories.BnhTokenRepository;
import com.core.bingehaven.repositories.BnhUsersRepository;
import com.core.bingehaven.entities.BnhUsers;
import com.core.bingehaven.enums.BnhStatus;
import com.core.bingehaven.enums.BnhUserRoles;
import com.core.bingehaven.service.BnhUserService;
import com.core.bingehaven.service.EmailService;
import com.core.bingehaven.utils.BnhUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BnhUserServiceImpl implements BnhUserService {

    private final BnhUsersRepository bnhUsersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    private final EmailService emailService;
    private final BnhTokenRepository bnhTokenRepository;
    @Value("${reset.url}")
    private String resetUrl;

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
            boolUser.setRole(BnhUserRoles.ROLE_USER);

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
            BnhUsers ifUserExist = bnhUsersRepository.findByUsername(editRequestDto.getUsername());
            if (ifUserExist != null) {
                throw new RuntimeException("Sorry, " + editRequestDto.getUsername() + " has already been taken");
            }
            user.setFirstName(editRequestDto.getFirstName() == null ? user.getFirstName() : editRequestDto.getFirstName());
            user.setLastName(editRequestDto.getLastName() == null ? user.getLastName() : editRequestDto.getLastName());
            user.setUsername(editRequestDto.getUsername() == null ? user.getUsername() : editRequestDto.getUsername());

            bnhUsersRepository.save(user);
//          force users to logout and login if they change their usernames
//            front end should prompt users
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
    public ResponseDto advanceUserUpdate(AdvanceEditReqDto editReqDto) {

        try {
            if (editReqDto == null) {
                throw new RuntimeException("Please fill all the required fields");
            }


            BnhUsers user = bnhUsersRepository.findById(editReqDto.getId()).orElseThrow(() -> new RuntimeException("Id must not be null or empty"));
            String currentPassword = user.getPassword();
            String newPassword = passwordEncoder.encode(editReqDto.getNewPassword());

            if (!passwordEncoder.matches(editReqDto.getOldPassword(), currentPassword)) {
                throw new RuntimeException("Please enter your current existing password");
            }
//                    frontend to do the comparison for new password and confirm password
            if (Objects.equals(editReqDto.getOldPassword(), editReqDto.getNewPassword())) {
                throw new RuntimeException("Your new password cannot be the same as your old password");
            }


            user.setPassword(newPassword);
            bnhUsersRepository.save(user);


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
    public ResponseDto forgotPassword(ForgotPasswordReq forgotPasswordReq, String token) {
        try {

            if (!validateResetToken(token)) {
                throw new RuntimeException("Invalid or Expired Token");
            }
            if (forgotPasswordReq == null) {
                throw new RuntimeException("Please fill all the required fields");
            }


            BnhTokens resetToken = bnhTokenRepository.findByToken(token);
            BnhUsers user = bnhUsersRepository.findByEmail(resetToken.getEmail());
            if (user == null) {
                throw new RuntimeException("Account not found");
            }

            String newPassword = passwordEncoder.encode(forgotPasswordReq.getNewPassword());
//                    frontend to do the comparison for new password and confirm password
//                    to make sure they are the same

            user.setPassword(newPassword);
            bnhUsersRepository.save(user);
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


    public Boolean validateResetToken(String token) {
        BnhTokens resetToken = bnhTokenRepository.findByToken(token);
        if (resetToken == null) {
            return false; // Invalid token
        }

        // Check if the token is expired
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
//            if (resetToken.getStatus() == BnhStatus.EXPIRED) {
            return false; // Token expired
        }

        return true; // Token is valid
    }

    public String generateResetTokenForUser(String email) {
        // Generate a random UUID token
        String token = UUID.randomUUID().toString();

        // Store the token in the database with the user's email (and set expiration)
        saveTokenToDatabase(email, token);

        return token;
    }

    private void saveTokenToDatabase(String email, String token) {
        BnhTokens tokens = new BnhTokens();

        tokens.setToken(token);
        tokens.setType("RESET_PASSWORD");
        tokens.setEmail(email);
        tokens.setExpiryDate(LocalDateTime.now().plusMinutes(5));
        tokens.setStatus(BnhStatus.VALID);

        bnhTokenRepository.save(tokens);
    }

    @Override
    public ResponseDto sendForgotPasswordMail(String recipient) {
        try {
            if (!StringUtils.isNotEmpty(recipient)) {
                throw new RuntimeException("Please enter a valid email address");
            }

            BnhUsers user = bnhUsersRepository.findByEmail(recipient);
            if (user == null) {
                throw new RuntimeException("Account not found");
            }
//        write logic for generating and validating token
            String token = generateResetTokenForUser(recipient);

            String resetLink = resetUrl + token; // Generate reset link

            String message = buildForgotPasswordEmail(resetLink);

            emailService.sendHtmlEmailAlert(EmailDto.builder()
                    .subject(BnhUtils.RECOVER_PASSWORD_MAIL)
                    .recipient(recipient)
                    .message(message)
                    .build());
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

    private String buildForgotPasswordEmail(String resetLink) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }" +
                "        .container { background-color: #ffffff; padding: 20px; max-width: 600px; margin: 0 auto; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                "        .header { text-align: center; margin-bottom: 20px; }" +
                "        .header h1 { color: #333333; font-size: 24px; }" +
                "        .content { text-align: center; margin-bottom: 20px; color: #555555; }" +
                "        .content p { font-size: 16px; }" +
                "        .content a { display: inline-block; text-decoration: none; background-color: #007bff; color: #ffffff; padding: 10px 20px; border-radius: 5px; font-size: 16px; margin-top: 20px; }" +
                "        .footer { text-align: center; font-size: 12px; color: #999999; margin-top: 20px; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <div class='header'>" +
                "            <h1>Password Recovery</h1>" +
                "        </div>" +
                "        <div class='content'>" +
                "            <p>Hello,</p>" +
                "            <p>We received a request to reset your password. If you did not request this, please ignore this email. Otherwise, click the link below to reset your password:</p>" +
                "            <a href='" + resetLink + "' target='_blank'>Reset Password</a>" +
                "        </div>" +
//                add expiry notification
                "        <div class='footer'>" +
                "            <p>This link will expire in 5 minutes</p>" +
                "            <p>If you didn't request a password reset, no further action is required.</p>" +
                "            <p>&copy; 2024 BingeHaven. All rights reserved.</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
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
                    .info(UserResponseDto.builder()
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
