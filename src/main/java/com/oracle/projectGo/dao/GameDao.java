package com.oracle.projectGo.dao;

import com.oracle.projectGo.dto.GameContents;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GameDao {

    private final SqlSession session;

 // -----------------------------------------------------------

    // 게임콘텐츠 insert
    public int gameContentInsert(GameContents gameContents) {
        System.out.println("GameDao gameContentInsert Start !");

        int gameContentInsert = 0;
        try {
            gameContentInsert = session.insert("gameContentInsert", gameContents);
            System.out.println("GameDao gameContentInsert-> " + gameContentInsert);
        }catch(Exception e){
            System.out.println("GameDao gameContentInsert Exception-> " + e);
        }
        return gameContentInsert;
    }

    // 총 갯수(운영자 화면)
    public int gameContentsTotalCount() {
        System.out.println("GameDao gameContentsTotalCount Start !");

        int gameContentsTotalCount = 0;
        try {
            gameContentsTotalCount = session.selectOne("gameContentsTotalCount");
            System.out.println("GameDao gameContentsTotalCount-> " + gameContentsTotalCount);
        }catch(Exception e) {
            System.out.println("GameDao gameContentsTotalCount Exception-> " + e);
        }
        return gameContentsTotalCount;
    }

    // 리스트 조회(운영자 화면)
    public List<GameContents> gameContentsList(GameContents gameContents) {
        System.out.println("GameDao gameContentsList Start !");

        List<GameContents> gameContentsList = null;
        try{
            gameContentsList = session.selectList("gameContentsList", gameContents);
            System.out.println("GameDao gameContentsList.size()-> " + gameContentsList.size());
        }catch (Exception e){
            System.out.println("GameDao gameContentsList Exception-> " + e);
        }
        return  gameContentsList;
    }

// -----------------------------------------------------------

    // 총 갯수(리스트에서 구독할 컨텐츠 조회 페이지)
    public int subscribeTotalCount() {
        System.out.println("GameDao subscribeTotalCount Start !");

        int subscribeTotalCount = 0;
        try {
            subscribeTotalCount = session.selectOne("subscribeTotalCount");
            System.out.println("GameDao subscribeTotalCount-> " + subscribeTotalCount);
        }catch(Exception e) {
            System.out.println("GameDao subscribeTotalCount Exception-> " + e);
        }
        return subscribeTotalCount;
    }

    // 리스트 조회(리스트에서 구독할 컨텐츠 조회 페이지)
    public List<GameContents> subscribeGameList(GameContents gameContents) {
        System.out.println("GameDao subscribeGameList Start !");

        List<GameContents> subscribeGameList = null;
        try{
            subscribeGameList = session.selectList("subscribeGameList", gameContents);
            System.out.println("GameDao subscribeGameList.size()-> " + subscribeGameList.size());
        }catch (Exception e){
            System.out.println("GameDao subscribeGameList Exception-> " + e);
        }
        return  subscribeGameList;
    }

// -----------------------------------------------------------
//    버전1
//    public List<GameContents> gameContentsListByIds(List<Integer> gameIds) {
//        return session.selectList("gameContentsListByIds",gameIds);
//    }

    // 각 아이디의 리스트 조회
    public GameContents getGameContentsById(Integer gameId) {
        System.out.println("GameDao getGameContentsById Start !");

        GameContents getGameContentsById = null;
        try {
            getGameContentsById = session.selectOne("gameContentsById", gameId);
            System.out.println("GameDao getGameContentsById-> " + getGameContentsById);
        }catch (Exception e){
            System.out.println("GameDao gameContentsList Exception-> " + e);
        }
        return getGameContentsById;
    }

    // 공개(0) -> 비공개(1)
    public int deleteYes(GameContents gameContents) {
        System.out.println("GameDao deleteYes Start !");

        int deleteYes = 0;
        try {
            deleteYes = session.update("deleteYes", gameContents);
            System.out.println("GameDao deleteYes-> " + deleteYes);
        }catch (Exception e){
            System.out.println("GameDao deleteYes Exception-> " + e);
        }
        return deleteYes;
    }


    // 비공개(1) -> 공개(0)
    public int deleteNo(GameContents gameContents) {
        System.out.println("GameDao deleteNo Start !");

        int deleteNo = 0;
        try {
            deleteNo = session.update("deleteNo", gameContents);
            System.out.println("GameDao deleteNo-> " + deleteNo);
        }catch (Exception e){
            System.out.println("GameDao deleteNo Exception-> " + e);
        }
        return deleteNo;
    }

    public List<GameContents> getSubscribedGameContents(int userId){
        return session.selectList("getSubscribedGameContents", userId);
    }

}
