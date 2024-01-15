package com.lms.example.lecture.repository;

import com.lms.example.lecture.domain.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface LectureRepository
        extends JpaRepository<Lecture, Integer> {

    @Modifying
    @Query(nativeQuery = true, value = "ALTER TABLE lecture.lecture AUTO_INCREMENT = 1000")
    @Transactional
    void updateSequenceStartValue();

    @Query("SELECT l FROM Lecture l where l.id IN :ids")
    List<Lecture> findByIds(@Param("ids")List<Integer> lectureIds);


    @Query("SELECT l FROM Lecture as l WHERE l.lectureName = :lectureName")
    List<Lecture> findLectureByLectureName(@Param("lectureName") String lectureName);
    @Query("SELECT l FROM Lecture as l WHERE l.status != com.lms.example.lecture.domain.entity.Status.ACCEPT and l.professorId = :professorId")
    List<Lecture> findLectureByProfessorId(@Param("professorId") String professorId);

    @Query("SELECT l FROM Lecture as l WHERE l.status != com.lms.example.lecture.domain.entity.Status.ACCEPT and l.professorId = :professorId")
    List<Lecture> findHoldingLectureByProfessorId(@Param("professorId") String professorId);

    @Query("select l FROM Lecture as l where l.status != com.lms.example.lecture.domain.entity.Status.ACCEPT")
    List<Lecture> findAllHoldingList();


    @Modifying
    @Query("DELETE FROM Lecture l WHERE l.id IN :ids")
    void deleteByLectureId(@Param("ids") List<Integer> lectureIds);

    @Query("select l from Lecture as l WHERE l.lectureName = :lectureName")
    Lecture findByLectureName(@Param("lectureName")String lectureName);



}
