package com.oracle.projectGo.controller.admin;

import com.oracle.projectGo.dto.EducationalResources;
import com.oracle.projectGo.dto.GameContents;
import com.oracle.projectGo.dto.Payments;
import com.oracle.projectGo.dto.Users;
import com.oracle.projectGo.service.AdminResourceService;
import com.oracle.projectGo.service.GameService;
import com.oracle.projectGo.service.Paging;
import com.oracle.projectGo.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/admin/resource")
public class AdminResourceController {
    private final AdminResourceService adminResourceService;
    private final UsersService usersService;
    private final GameService gameService;
    @RequestMapping(value = "/uploadForm")
    public String educationUploadForm(Model model, String currentPage) {
        int gameContentsTotalCount = gameService.gameContentsTotalCount();

        GameContents gameContents = new GameContents();
        Paging page = new Paging(gameContentsTotalCount, currentPage);
        gameContents.setStart(page.getStart());
        gameContents.setEnd(page.getEnd());
        List<GameContents> gameContentsList = gameService.gameContentsList(gameContents);
        model.addAttribute("gameContentsList", gameContentsList);

        return "admin/resource/upload";
    }
    @PostMapping(value = "/upload")
    public String educationUpload(EducationalResources educationalResources,
                                  Model model,
                                  HttpServletRequest request,
                                  @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Users users = usersService.getLoggedInUserInfo();
        educationalResources.setUserId(users.getId());
        educationalResources.setContentId(educationalResources.getContentId());

        String uploadPath = request.getSession().getServletContext().getRealPath("/upload/educationalResources/");

        System.out.println("uploadPath->" + uploadPath);
        System.out.println("originalName : " + file.getOriginalFilename());
        System.out.println("fileByte : " + file.getBytes());
        System.out.println("fileSize : " + file.getSize());

        if ( file.getSize() > 0 ) {
            String saveName = uploadFile(file.getOriginalFilename(), file.getBytes(), uploadPath);
            educationalResources.setImage(saveName);
        } else {
            educationalResources.setImage("");
        }

        log.info(educationalResources.toString());

        int result = adminResourceService.educationUpload(educationalResources);
        log.info(String.valueOf(result));

        return "redirect:/lookup/board/listEdu";
    }

    @GetMapping(value = "/listEdu")
    public String educationList(Users users, Model model) {

        users = usersService.getLoggedInUserInfo();
        model.addAttribute("users", users);

        return "admin/resource/listEdu";
    }

    @GetMapping(value = "/api/listEdu")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> educationList(Users users, HttpServletRequest request) {
        String uploadPath = request.getSession().getServletContext().getRealPath("/upload/educationalResources/");
        users = usersService.getLoggedInUserInfo();

        // ajax로 교육자료 리스트 불러오기
        Map<String, Object> response = new HashMap<>();

        List<EducationalResources> listEdu = adminResourceService.listEdu(users);
        transNaming(listEdu);

        response.put("listEdu", listEdu);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/detailEdu")
    public String detailEdu(int id, Model model) {
        log.info(String.valueOf(id));
        Users users = usersService.getLoggedInUserInfo();

        EducationalResources edu = adminResourceService.detailEdu(id);
        adminResourceService.readCnt(id);

        model.addAttribute("id", id);
        model.addAttribute("edu", edu);
        model.addAttribute("users",users);

        return "lookup/resource/detailEdu";
    }

    @GetMapping(value = "api/detailEdu")
    @ResponseBody
    public ResponseEntity<Map<Object, String>> detailEdu(Users users) {
        Map<Object, String> response = new HashMap<>();
        users = usersService.getLoggedInUserInfo();


        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/updateEduForm")
    public String updateEduForm(int id, Model model, String currentPage) {

        EducationalResources edu = adminResourceService.detailEdu(id);

        int gameContentsTotalCount = gameService.gameContentsTotalCount();

        GameContents gameContents = new GameContents();
        Paging page = new Paging(gameContentsTotalCount, currentPage);
        gameContents.setStart(page.getStart());
        gameContents.setEnd(page.getEnd());
        List<GameContents> gameContentsList = gameService.gameContentsList(gameContents);

        log.info(edu.getGameTitle());
        log.info(String.valueOf(edu.getContentId()));

        model.addAttribute("gameContentsList", gameContentsList);
        model.addAttribute("edu",edu);

        return "admin/resource/updateEdu";
    }

    @PostMapping(value = "/updateEdu")
    public String updateEdu(EducationalResources edu,
                            HttpServletRequest request,
                            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        String uploadPath = request.getSession().getServletContext().getRealPath("/upload/educationalResources/");

        log.info(String.valueOf(edu.getContentId()));
        log.info(edu.getGameTitle());

        System.out.println("uploadPath->" + uploadPath);
        System.out.println("originalName : " + file.getOriginalFilename());
        System.out.println("fileByte : " + file.getBytes());
        System.out.println("fileSize : " + file.getSize());

        int delResult = 0;

        if (file.getSize() != 0 ) {
            String deleteFile = uploadPath + edu.getImage();
            delResult = fileDelete(deleteFile);
        }

        if (delResult != 0) {
            String saveName = uploadFile(file.getOriginalFilename(), file.getBytes(), uploadPath);
            edu.setImage(saveName);
        }
        int result = adminResourceService.updateEdu(edu);
        return "redirect:/admin/resource/detailEdu?id="+edu.getId();
    }

    @DeleteMapping(value = "/deleteEdu")
    public ResponseEntity<Map<Object, String>> deleteEdu(int id, HttpServletRequest request) {
        Map<Object, String> response = new HashMap<>();
        EducationalResources edu = adminResourceService.detailEdu(id);
        String img = edu.getImage();
        int result = adminResourceService.deleteEdu(id);

        if ( result > 0 ) {
            String uploadPath = request.getSession().getServletContext().getRealPath("/upload/educationalResources/");
            String deleteFile = uploadPath + img;
            int delResult = fileDelete(deleteFile);

            System.out.println("delResult -> " + delResult);
        }

        response.put("result", Integer.toString(result));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/api/listSearchEdu")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> listSearchEdu(String keyword, String category) {
        Map<String, Object> response = new HashMap<>();
        Users users = usersService.getLoggedInUserInfo();
        users.setKeyword(keyword);
        users.setSearchType(category);
        List<EducationalResources> listSearchEdu = adminResourceService.listSearchEdu(users);
        response.put("listSearchEdu", listSearchEdu);

        return ResponseEntity.ok(response);
    }






    private void transNaming(List<EducationalResources> list) {
        // 결제방법, 결제상태를 필요한 문자열로 변환
        for (EducationalResources item : list) {
            switch (item.getResourceType()) {
                case "1":
                    item.setResourceType("튜토리얼");
                    break;
                case "2":
                    item.setResourceType("교육영상");
                    break;
            }
        }

        for (EducationalResources item : list) {
            switch (item.getServiceType()) {
                case "1":
                    item.setServiceType("무료");
                    break;
                case "2":
                    item.setServiceType("유료");
                    break;
            }
        }
    }

    private String uploadFile(String originalName, byte[] fileData, String uploadPath) throws IOException {
        UUID uid = UUID.randomUUID();

        File fileDirectory = new File(uploadPath);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        String savedName = uid.toString() + "_" + originalName;
        File target = new File(uploadPath, savedName);
        return savedName;
    }

    private int fileDelete(String deleteFile) {
        int result = 0;
        File file = new File(deleteFile);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("1");
                result = 1;
            } else {
                System.out.println("2");
                result = 2;
            }
        } else {
            System.out.println("0");
            result = 0;
        }
        return result;
    }
}
