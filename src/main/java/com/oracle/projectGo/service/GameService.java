package com.oracle.projectGo.service;

import com.oracle.projectGo.dao.GameDao;
import com.oracle.projectGo.dto.GameContents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameDao gd;
//------------------------------------------------------

    // 게임콘텐츠 insert
    public int gameContentInsert(GameContents gameContents) {
        System.out.println("ContentService gameContentInsert Start !");
        int gameContentInsert = gd.gameContentInsert(gameContents);

        return gameContentInsert;
    }

    // 총 갯수(운영자 화면)
    public int gameContentsTotalCount() {
        System.out.println("ContentService gameContentsTotalCount Start !");
        int gameContentsTotalCount = gd.gameContentsTotalCount();

        return gameContentsTotalCount;
    }

    // 리스트 조회(운영자 화면)
    public List<GameContents> gameContentsList(GameContents gameContents) {
        System.out.println("ContentService gameContentsList Start !");
        List<GameContents> gameContentsList = gd.gameContentsList(gameContents);

        return gameContentsList;
    }

//------------------------------------------------------

    // 총 갯수(리스트에서 구독할 컨텐츠 조회 페이지)
    public int subscribeTotalCount() {
        System.out.println("ContentService subscribeTotalCount Start !");
        int subscribeTotalCount = gd.subscribeTotalCount();

        return subscribeTotalCount;
    }

    // 리스트 조회(리스트에서 구독할 컨텐츠 조회 페이지)
    public List<GameContents> subscribeGameList(GameContents gameContents) {
        System.out.println("ContentService subscribeGameList Start !");
        List<GameContents> subscribeGameList = gd.subscribeGameList(gameContents);

        return subscribeGameList;
    }

//------------------------------------------------------

    // 각 아이디의 리스트 조회
    public GameContents getGameContentsById(Integer gameId) {
        System.out.println("ContentService getGameContentsById Start !");
        GameContents getGameContentsById = gd.getGameContentsById(gameId);

        return getGameContentsById;
    }

    // 공개(0) -> 비공개(1)
    public int deleteYes(GameContents gameContents) {
        System.out.println("ContentService deleteYes Start !");
        int deleteYes = gd.deleteYes(gameContents);

        return deleteYes;
    }


    // 비공개(1) -> 공개(0)
    public int deleteNo(GameContents gameContents) {
        System.out.println("ContentService deleteNo Start !");
        int deleteNo = gd.deleteNo(gameContents);

        return deleteNo;
    }

    public  List<GameContents> getSubscribedGameContents(int userId) {
        return gd.getSubscribedGameContents(userId);
    }

}
