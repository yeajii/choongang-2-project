package com.oracle.projectGo.service;

import com.oracle.projectGo.dao.UsersDao;
import com.oracle.projectGo.dto.Users;
import com.oracle.projectGo.type.UsersRoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {

    private final UsersDao ud;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    public Optional<Users> getUserByNickname(String nickname) {
        Users getUserByNickname = ud.getUserByNickname(nickname);
        return Optional.ofNullable(getUserByNickname);

    }

    public int getLoggedInId() {
        Users user =new Users();
        user.setId(0);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getName().equals("anonymousUser") ){
            user = ud.getUserByNickname(authentication.getName());


            log.info("로그인아이디:{}",user.getId());
            return user.getId();
        }
        return user.getId();
    }
    public Users getLoggedInUserInfo() {
        Users user =new Users();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getName().equals("anonymousUser") ){

            user = ud.getUserByNickname(authentication.getName());

            return user;
        }
        return user;
    }

    public UsersRoleType getLoggedInUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 로그인하지 않았거나 권한이 없는 경우 ANONYMOUS 리턴
        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            return UsersRoleType.ANONYMOUS;
        }

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No authority found"));

        // "ROLE_" 접두사 제거
        String roleLabel = role.replace("ROLE_", "");
        log.info("getLoggedInUserRole:{}",roleLabel);

        return UsersRoleType.findByLabel(roleLabel);
    }

    public String getPasswordResetToken(String nickname) {
        // 1. 닉네임으로 사용자 찾기
        Users user = ud.getUserByNickname(nickname);

        if (user != null) {
            // 2. 새로운 비밀번호 생성
            String newPassword = generateRandomPassword();

            // 3. 새로운 비밀번호를 사용자에게 전송
            // 3. 새로운 비밀번호를 사용자에게 전송
            sendPasswordResetNotification(user.getEmail(), newPassword);

            Users users = new Users();
            users.setId(user.getId());
            String encryptedPassword = bCryptPasswordEncoder.encode(newPassword);
            users.setPassword(encryptedPassword);
            log.info("newPassword = "+ users.getPassword());
            log.info("users nickname = "+ user.getId());
            ud.userUpdatePassword(users);

            // 4. 새로운 비밀번호를 반환
            return newPassword;
        }

        // 사용자를 찾지 못한 경우에 대한 예외를 던짐
        return null;
    }

    private String generateRandomPassword() {
        // 적절한 방식으로 무작위 비밀번호 생성
        return RandomStringUtils.randomNumeric(6);
    }

    public void sendPasswordResetNotification(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);  // userEmail 변수를 email로 수정
        message.setSubject("비밀번호 재설정 안내");
        message.setText("새로운 비밀번호: " + newPassword);

        mailSender.send(message);
    }





    public int insertUsers(Users users) {
        log.info("UsersService start");
        int insertUsers = 0;

        // 사용자로부터 입력받은 비밀번호를 암호화
        String encryptedPassword = bCryptPasswordEncoder.encode(users.getPassword());

        // 암호화된 비밀번호를 사용자 객체에 설정
        users.setPassword(encryptedPassword);

        // 나머지 사용자 정보를 설정

        // 사용자 정보를 데이터베이스에 저장
        insertUsers = ud.insertUsers(users);
        return insertUsers;
    }
    public int nickCheck(Users users) {
        int result = ud.nickCheck(users);
        return result;
    }

    public int emailCheck(Users users) {
        int result = ud.emailCheck(users);
        return result;
    }

    public Users idSearchByPhone(Users users) {
        Users idSearchByPhone = ud.idSearchByPhone(users);
        return idSearchByPhone;
    }

    public Users passwordSearchByEmail(Users users) {
        Users passwordSearchByEmail = ud.passwordSearchByEmail(users);
        return passwordSearchByEmail;
    }
    public String sendEmail(String userEmail) {

        String token = RandomStringUtils.randomNumeric(6);


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("회원가입 인증 메일");
        message.setText("인증 코드: " + token);

        mailSender.send(message);

        return token;
    }

    public int totalUsers(Users users) {
        int totalUsers = 0;
        totalUsers = ud.totalUsers(users);
        return totalUsers;
    }

    public List<Users> getSearchUserList(Users users) {
        List<Users> getSearchUserList = null;
        getSearchUserList = ud.getSearchUserList(users);
        return getSearchUserList;

    }

    public Users getUserById(Users users) {
        Users getUserById = null;
        getUserById = ud.getUserById(users);
        return getUserById;
    }

    public int getBuyCount(int id) {
        int getBuyCount = 0;
        getBuyCount = ud.getBuyCount(id);
        return getBuyCount;
    }

    public int userUpdate(Users users) {
        int userUpdate = 0;

        String encryptedPassword = bCryptPasswordEncoder.encode(users.getPassword());

        // 암호화된 비밀번호를 사용자 객체에 설정
        users.setPassword(encryptedPassword);
        userUpdate = ud.userUpdate(users);
        return userUpdate;
    }
}
