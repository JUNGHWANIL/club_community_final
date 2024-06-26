package web.term.club.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.term.club.domain.Club;
import web.term.club.domain.ClubInfo;
import web.term.club.response.ClubInfoDto;
import web.term.club.service.ClubInfoService;

import java.time.LocalTime;

@RestController
@RequestMapping("/clubInfo")
public class ClubInfoController {
    @Autowired
    private ClubInfoService clubInfoService;

    // 클럽 정보 조회
    // 검증 o
    //http://localhost:8081/clubInfo/1
    @GetMapping("/{clubId}")
    public ResponseEntity<?> getClubInfo(@PathVariable Long clubId) {
        try {
            Club club = new Club();
            club.setId(clubId);
            ClubInfoDto clubInfoDto = clubInfoService.getClubInfo(club);
            return new ResponseEntity<>(clubInfoDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    // 클럽 정보 업데이트
    //
    @PostMapping("/update")
    public ResponseEntity<?> updateClubInfo(@RequestBody ClubInfoDto clubInfoDto) {
        try {
            ClubInfoDto updatedClubInfo = ClubInfoDto.of(clubInfoService.updateClubInfo(clubInfoDto));
            return new ResponseEntity<>(updatedClubInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createClubInfo(@RequestPart("clubName") String clubName,
                                            @RequestPart("clubInfo") String clubInfo,
                                            @RequestPart("clubMeetTime") String clubMeetTime,
                                            @RequestPart(value = "clubImg", required = false) MultipartFile clubImg,
                                            @RequestPart(value = "applicationFile", required = false) MultipartFile applicationFile) {
        try {
            Club club = clubInfoService.findFirstByName(clubName);
            ClubInfo newClubInfo = new ClubInfo();
            newClubInfo.setInfo(clubInfo);
            newClubInfo.setMeetingTime(LocalTime.parse(clubMeetTime.replaceAll("\"", ""))); // 따옴표 제거
            if (clubImg != null && !clubImg.isEmpty()) {
                newClubInfo.setImg(saveFile(clubImg));
            }
            if (applicationFile != null && !applicationFile.isEmpty()) {
                newClubInfo.setClubSignUpFile(saveFile(applicationFile));
            }
            newClubInfo.setClub(club);
            clubInfoService.saveClubInfo(newClubInfo);
            return new ResponseEntity<>("Club info created successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private String saveFile(MultipartFile file) {
        // 파일 저장 로직 구현
        return "C:\\Users\\sunni\\file-repository";
    }
}
