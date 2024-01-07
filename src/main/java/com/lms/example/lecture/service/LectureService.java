package com.lms.example.lecture.service;

import com.lms.example.global.exception.ClientException;
import com.lms.example.global.exception.DuplicateException;
import com.lms.example.global.exception.NotFoundException;
import com.lms.example.global.kafka.KafkaAction;
import com.lms.example.global.kafka.KafkaLecture;
import com.lms.example.global.kafka.KafkaProducer;
import com.lms.example.lecture.domain.entity.Lecture;
import com.lms.example.lecture.domain.entity.Status;
import com.lms.example.lecture.dto.request.AdminDenyLectureRequest;
import com.lms.example.lecture.dto.request.AdminLectureRequest;
import com.lms.example.lecture.dto.request.ProfessorLectureCancelRequest;
import com.lms.example.lecture.dto.request.ProfessorLectureRequest;
import com.lms.example.lecture.dto.response.AllLectureRes;
import com.lms.example.lecture.repository.LectureRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class LectureService {


    private final LectureRepository lectureRepository;

    private final KafkaProducer kafkaProducer;
    
    //시작값 1000부터 초기화
    @PostConstruct
    public void init() {
        lectureRepository.updateSequenceStartValue();
    }


    //강의 등록 요청(교수)
    @Transactional
    public Lecture requestLecture(ProfessorLectureRequest request) {
        try {
            List<Lecture> lectureByProfessorId = lectureRepository.findLectureByLectureName(request.getLectureName());
            if (lectureByProfessorId.size()!=0){
                throw new DuplicateException("이미 등록 요청한 강의 입니다.");
            }
            //toEntity로 저장
            Lecture save = lectureRepository.save(request.toEntity());

            return save;
        } catch (Exception e) {
            e.printStackTrace();
           throw new RuntimeException("강의 저장 실패");
        }
    }


    // 대기 요청 목록 조회(교수)
    @Transactional
    public List<AllLectureRes> holdingLectureFindById(String professorId) {
        List<Lecture> lectureById = lectureRepository.findHoldingLectureByProfessorId(professorId);
        if (lectureById.size()==0) {
                throw new NotFoundException("승인대기 중인 과목이 없습니다.");
            }
            List<AllLectureRes> resultList = new ArrayList<>();
        
            for (Lecture lecture : lectureById) {
                AllLectureRes allLectureRes = new AllLectureRes(lecture);
                resultList.add(allLectureRes);
            }
            
            return resultList;
        }

    // 모든 강의 목록 조회(교수)
    @Transactional
    public List<AllLectureRes> agreeLectureFindById(String professorId) {
        List<Lecture> lectureById = lectureRepository.findLectureByProfessorId(professorId);
        if (lectureById.size()==0) {
            throw new NotFoundException("강의 목록이 없습니다.");
        }
        List<AllLectureRes> resultList = new ArrayList<>();

        for (Lecture lecture : lectureById) {
            AllLectureRes allLectureRes = new AllLectureRes(lecture);
            resultList.add(allLectureRes);
        }

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
    @Transactional
    public boolean denyLecture(AdminDenyLectureRequest request) {
        try {
            List<Lecture> byIds = lectureRepository.findByIds(request.getLectureIds());

            if (byIds.isEmpty()) {
                throw new NotFoundException("강의가 없습니다.");
            }

            for (Lecture lecture : byIds) {
                lecture.setStatus(Status.DENIED);
            }

            List<Lecture> savedLectures = lectureRepository.saveAll(byIds);

            if (savedLectures.isEmpty()) {
                return false;
            }

        } catch (Exception e) {
            throw new RuntimeException("강의 거부 중 오류가 발생했습니다.", e);
        }
        return true;
    }



    //강의 요청 승인(어드민)
    @Transactional
    public boolean acceptLecture(AdminLectureRequest request) {
        try {

            List<Lecture> byIds = lectureRepository.findByIds(request.getLectureIds());

            if (byIds.isEmpty()) {
                throw new NotFoundException("강의가 없습니다.");
            }

            List<Lecture> savedLectures = lectureRepository.saveAll(byIds);

            for (Lecture lecture : byIds) {
                lecture.setStatus(Status.ACCEPT);

                List<Integer> classTimes = new ArrayList<>();
                int start = lecture.getStartTime();
                int end = start + lecture.getScore() - 1;

                classTimes.addAll(IntStream.rangeClosed(start, end)
                        .boxed()
                        .collect(Collectors.toList()));
                lecture.setClassTimes(classTimes);

                KafkaLecture build = KafkaLecture.builder()
                        .memberId(request.getMemberId())
                        .lectureId(lecture.getId())
                        .professorName(lecture.getProfessorName())
                        .lectureName(lecture.getLectureName())
                        .score(lecture.getScore())
                        .startTime(lecture.getStartTime())
                        .maximumNumber(lecture.getMaximumNumber())
                        .classTimes(classTimes)
                        .dayOfWeek(lecture.getDayOfWeek())
                        .semester(lecture.getSemester())
                        .year(lecture.getYear())
                        .contents(lecture.getLectureComment())
                        .kafkaAction(KafkaAction.CREATE)
                        .build();

                kafkaProducer.saveLecture("lecture2", build );
            }
            if (savedLectures.isEmpty()) {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return true;
    }

}







