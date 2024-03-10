package com.quiz.repo;

import com.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepo extends JpaRepository<Question, UUID> {

    @Query("select q.qid from Question q where q.subject = :subject and q.topic = :topic")
    List<UUID> findQidBySubjectAndTopic(String subject, String topic);

    List<Question> findByQidIn(List<UUID> QId);

    Optional<Question> findByQid(UUID qId);

    List<Question> findTop10BySubject(String subject);

    List<Question> findTop10ByTopic(String topic);

    List<Question> findTop10BySubjectAndTopic(String subject, String topic);

    List<Question> findTop10ByOrderByQidDesc();

}
