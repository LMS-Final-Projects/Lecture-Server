package com.example.lecture.service;

import com.example.global.exception.ClientException;
import com.example.global.exception.NotFoundException;
import com.example.lecture.domain.entity.Lecture;
import com.example.lecture.domain.entity.Status;
import com.example.lecture.domain.request.AdminLectureRequest;
import com.example.lecture.domain.request.ProfessorLectureCancelRequest;
import com.example.lecture.domain.request.ProfessorLectureRequest;
import com.example.lecture.domain.response.AllLectureRes;
import com.example.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LectureService {

    @Autowired
    private final LectureRepository lectureRepository;

    //강의 등록 요청(교수)
    @Transactional
    public Lecture requestLecture(ProfessorLectureRequest request) {
        try {
            //toEntity로 저장
            Lecture save = lectureRepository.save(request.toEntity());

            return save;
        } catch (Exception e) {
            e.printStackTrace();
           throw new RuntimeException("강의 저장 실패");
        }
    }


    // 강의 요청 목록 조회(교수)
    @Transactional
    public List<AllLectureRes> agreeLectureFindById(Long id) {
        List<Lecture> lectureById = lectureRepository.findLectureById(id);
        if (lectureById.size()==0) {
                throw new NotFoundException("승인대기 중인 과목이 없습니다.");
            }
            List<AllLectureRes> resultList = new ArrayList<>();
        
            for (Lecture lecture : lectureById) {
                AllLectureRes allLectureRes = new AllLectureRes(lecture);
                resultList.add(allLectureRes);
            } //stream 으로 나중에 변경 가능
            
            return resultList;
        }


    //강의 등록 요청 취소(교수)
    @Transactional
    public void cancelLecture(ProfessorLectureCancelRequest request) {
        try {
            if (request == null) {
                throw new ClientException("잘못된 요청입니다.");
            }
            lectureRepository.deleteByLectureId(request.getLectureIds());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    // 강의 요청 목록 전체 조회(어드민)
    @Transactional
    public List<AllLectureRes> findAllHoldingLecture() {
        List<Lecture> allHoldingList = lectureRepository.findAllHoldingList();

        if (allHoldingList.size()==0) {
               throw new NotFoundException("빈 리스트입니다.");
            }

            List<AllLectureRes> resultList = new ArrayList<>();

            for (Lecture lecture : allHoldingList) {
                resultList.add(new AllLectureRes(lecture));
            }

            return resultList;
    }

    // 강의 요청 목록 전체 조회(교수, 학생)
    @Transactional
    public List<AllLectureRes> findAllLecture() {
        List<Lecture> allList = lectureRepository.findAll();

        if (allList.size()==0) {
            throw new NotFoundException("빈 리스트입니다.");
        }

        List<AllLectureRes> resultList = new ArrayList<>();

        for (Lecture lecture : allList) {
            resultList.add(new AllLectureRes(lecture));
        }

        return resultList;
    }


    //강의 요청 거부(어드민)
    @Transactional // 수정 필요함
    public void denyLecture(AdminLectureRequest request) {
        try {
            Lecture lecture = lectureRepository.findById(request.toEntity().getId()).orElseThrow(() -> new NotFoundException("강의 요청이 없습니다."));

            if (lecture.getStatus().equals(Status.DENIED) || lecture.getStatus().equals(Status.ACCEPT)) {
               throw new NotFoundException("강의가 이미 처리 되었습니다.");
            }
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }



    //강의 요청 승인(어드민)
    @Transactional
    public void acceptLecture(AdminLectureRequest request) {
        try {
            Lecture lecture = lectureRepository.findById(request.toEntity().getId()).orElseThrow(() -> new NotFoundException("강의 요청이 없습니다."));
            if (lecture.getStatus().equals(Status.DENIED) || lecture.getStatus().equals(Status.ACCEPT)) {
                throw new NotFoundException("처리되지 않은 강의 목록이 없습니다.");
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}







