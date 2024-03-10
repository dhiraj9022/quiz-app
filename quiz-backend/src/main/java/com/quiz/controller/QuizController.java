package com.quiz.controller;

import com.quiz.dto.*;
import com.quiz.model.Question;
import com.quiz.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/quiz")
@Slf4j
@CrossOrigin("http://localhost:3000")
public class QuizController {

    private final QuestionService questionService;

    public QuizController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/add-question")
    public ResponseEntity<QuestionDetails> addQuestion(@RequestBody @Valid AddQuestionDto dto) {
        return ResponseEntity.ok(questionService.addQuestions(dto));
    }

    @PutMapping("/update-question/{qId}")
    public ResponseEntity<QuestionDetails> updateQuestion(@RequestBody @Valid UpdateQuestionDto dto, @PathVariable UUID qId) {
        return ResponseEntity.ok(questionService.updateQuestions(dto, qId));
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> findAllQuestion(@RequestParam("subject") Optional<String> subject,
                                                       @RequestParam("topic") Optional<String>  topic){
        return ResponseEntity.ok(questionService.getAllQuestion(subject, topic));
    }


    @GetMapping("/questions/{qId}")
    public ResponseEntity<Question> findQuestion(@PathVariable UUID qId){
        return ResponseEntity.ok(questionService.getQuestion(qId));
    }

    @DeleteMapping("/questions/delete/{qId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID qId){
        questionService.deleteQuestion(qId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/questions/answer")
    public ResponseEntity<Score> checkAnswer(@RequestBody @Valid List<Answer> answers) {
        return ResponseEntity.ok(questionService.getAnswer(answers));
    }

    @GetMapping("/questions/generate")
    public ResponseEntity<List<QuestionDetails>> generateQuestions(@RequestParam(name = "subject", required = false) String subject,
                                                                   @RequestParam(name = "topic", required = false) String topic,
                                                                   @RequestParam(name = "numberOfQues") int numberOfQues) {
        return ResponseEntity.ok(questionService.generateQuestion(subject, topic, numberOfQues));
    }

}
