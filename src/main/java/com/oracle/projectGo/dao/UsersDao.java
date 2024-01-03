package com.oracle.projectGo.dao;

import com.oracle.projectGo.dto.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UsersDao {

    private final SqlSession session;

    public Users getUserByNickname(String nickname) {
        Users getUserByNickname = null;
    try {
        getUserByNickname = session.selectOne("getUserByNickname",nickname);


    }catch (Exception e){
        log.error("BoardDao boardCount Exception : {}", e.getMessage());

    } return getUserByNickname;

    }

    public int insertUsers(Users users) {
        log.info("UsersDao insertUsers start");
        int insertUsers = 0;
        try {
            insertUsers = session.insert("insertUsers", users);

        } catch (Exception e) {
            log.info("UsersDao insertUsers => " + e.getMessage());
        }
        log.info("insertUsers="+insertUsers);

        return insertUsers;


    }

    public int nickCheck(Users users) {
        log.info("UsersDao nickCheck start");
        int nickCheck = 0;
        try {
            nickCheck = session.selectOne("usersNicknameCheck", users);

        } catch (Exception e) {
            log.info("UsersDao nickCheck => " + e.getMessage());
        }
        log.info("result="+nickCheck);

        return nickCheck;
    }

    public int emailCheck(Users users) {
        log.info("UsersDao emailCheck start");
        int emailCheck = 0;
        try {
            emailCheck = session.selectOne("usersEmailCheck", users);

        } catch (Exception e) {
            log.info("UsersDao emailCheck => " + e.getMessage());
        }
        log.info("result="+emailCheck);

        return emailCheck;
    }

    public Users idSearchByPhone(Users users) {
        log.info("UsersDao idSearchByPhone start");
        Users idSearchByPhone = null;
        try {
            idSearchByPhone = session.selectOne("idSearchByPhone", users);

        } catch (Exception e) {
            log.info("UsersDao idSearchByPhone => " + e.getMessage());
        }
        return idSearchByPhone;
    }

    public int totalUsers(Users users) {
        log.info("UsersDao totalUsers start");
        int totalUsers = 0;
        try {
            totalUsers = session.selectOne("totalUsers", users);

        } catch (Exception e) {
            log.info("UsersDao totalUsers => " + e.getMessage());
        }
        return totalUsers;
    }

    public List<Users> getSearchUserList(Users users) {
        log.info("UsersDao getSearchUserList start");
        List<Users> getSearchUserList = null;
        try {
            getSearchUserList = session.selectList("getSearchUserList", users);
            log.info("getSearchUserList="+getSearchUserList.size());

        } catch (Exception e) {
            log.info("UsersDao getSearchUserList => " + e.getMessage());
        }
        return getSearchUserList;
    }

    public Users passwordSearchByEmail(Users users) {
        log.info("UsersDao passwordSearchByEmail start");
        Users passwordSearchByEmail = null;
        try {
            passwordSearchByEmail = session.selectOne("passwordSearchByEmail", users);

        } catch (Exception e) {
            log.info("UsersDao passwordSearchByEmail => " + e.getMessage());
        }
        return passwordSearchByEmail;
    }


    public Users getUserById(Users users) {
        log.info("UsersDao getUserById start");
        Users getUserById = null;
        try {
            getUserById = session.selectOne("getUserById", users);

        } catch (Exception e) {
            log.info("UsersDao getUserById => " + e.getMessage());
        }
        return getUserById;

    }

    public int getBuyCount(int id) {
        log.info("UsersDao getBuyCount start");
        int getBuyCount = 0;
        try {
            getBuyCount = session.selectOne("getBuyCount", id);

        } catch (Exception e) {
            log.info("UsersDao getBuyCount => " + e.getMessage());
        }
        return getBuyCount;
    }

    public int userUpdate(Users users) {
        log.info("UsersDao userUpdate start");
        int userUpdate = 0;
        try {
            userUpdate = session.update("userUpdate", users);

            log.info("userUpdate result"+ userUpdate);

        } catch (Exception e) {
            log.info("UsersDao userUpdate => " + e.getMessage());
        }
        return userUpdate;

    }

    public int userUpdatePassword(Users users) {
        log.info("UsersDao userUpdatePassword start");
        int userUpdatePassword = 0;
        try {
            userUpdatePassword = session.update("userUpdatePassword", users);

            log.info("userUpdatePassword result"+ userUpdatePassword);

        } catch (Exception e) {
            log.info("UsersDao userUpdatePassword => " + e.getMessage());
        }
        return userUpdatePassword;

    }
}
