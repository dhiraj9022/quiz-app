package com.quiz.service;

import com.quiz.dto.*;
import com.quiz.model.Question;
import com.quiz.repo.QuestionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepo questionRepo;

    private ModelMapper modelMapper;

    public QuestionService(QuestionRepo questionRepo, ModelMapper modelMapper) {
        this.questionRepo = questionRepo;
        this.modelMapper = modelMapper;
    }

    public QuestionDetails addQuestions(@Valid AddQuestionDto dto) {

        Question ques = new Question();

        ques.setDesc(dto.getDesc());
        ques.setSubject(dto.getSubject().toUpperCase().trim());
        ques.setAnswer(dto.getAnswer());
        ques.setTopic(dto.getTopic().toUpperCase().trim());
        ques.setOptionOne(dto.getOptionOne().toUpperCase().trim());
        ques.setOptionTwo(dto.getOptionTwo().toUpperCase().trim());
        ques.setOptionThree(dto.getOptionThree().toUpperCase().trim());
        ques.setOptionFour(dto.getOptionFour().toUpperCase().trim());

        questionRepo.save(ques);

        QuestionDetails questionDetails = modelMapper.map(ques, QuestionDetails.class);

        return questionDetails;
    }

    public Question getQuestion(UUID questionId){
        return questionRepo.findByQid(questionId).orElseThrow( () -> new NotFoundException("Question id not found"));
    }

    public List<Question> getAllQuestion(Optional<String> subject, Optional<String> topic){
        if (subject.isPresent() && topic.isPresent()) return questionRepo.findTop10BySubjectAndTopic(subject.get(), topic.get());
        if (subject.isPresent()) return questionRepo.findTop10BySubject(subject.get());
        if (topic.isPresent()) return questionRepo.findTop10ByTopic(topic.get());
        return questionRepo.findTop10ByOrderByQidDesc();
    }

    public QuestionDetails updateQuestions(UpdateQuestionDto dto, UUID qId) {

        Question question = getQuestion(qId);

        question.setDesc(dto.getDesc());
        question.setSubject(dto.getSubject().toUpperCase().trim());
        question.setAnswer(dto.getAnswer());
        question.setTopic(dto.getTopic().toUpperCase().trim());
        question.setOptionOne(dto.getOptionOne().toUpperCase().trim());
        question.setOptionTwo(dto.getOptionTwo().toUpperCase().trim());
        question.setOptionThree(dto.getOptionThree().toUpperCase().trim());
        question.setOptionFour(dto.getOptionFour().toUpperCase().trim());
        questionRepo.save(question);

        QuestionDetails questionDetails = modelMapper.map(question, QuestionDetails.class);

        return questionDetails;
    }

    public Score getAnswer(@Valid List<Answer> answers) {

        int corrects = 0;
        for (Answer answer : answers) {

            Question question = getQuestion(answer.getQid());

            if (question != null && question.getAnswer() == answer.getOption()) corrects++;
        }

        Score score = new Score();
        score.setQuestionCorrect(corrects);
        score.setTotal(answers.size());
        return score;
    }

    public List<QuestionDetails> generateQuestion(String subject, String topic, @Min(0) int numberOfQues) {

        List<UUID> ids = questionRepo.findQidBySubjectAndTopic(subject.toLowerCase().trim(), topic.toLowerCase().trim());

        if (ids.isEmpty()) throw new NotFoundException("No questions found");

        Collections.shuffle(ids);
        if (ids.size() < numberOfQues || numberOfQues < 0) throw new ValidationException("number of question are not available");

        List<Question> questions = questionRepo.findByQidIn(ids.subList(0, numberOfQues));

        return  questions.stream()
                .map(q -> modelMapper.map(q, QuestionDetails.class))
                .collect(Collectors.toList());
    }

    public void deleteQuestion(UUID qId) {

        Question question = getQuestion(qId);
        questionRepo.delete(question);
    }
}
