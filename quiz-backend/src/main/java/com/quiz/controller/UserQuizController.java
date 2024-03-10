package com.quiz.controller;

import com.quiz.dto.Answer;
import com.quiz.dto.QuestionDetails;
import com.quiz.dto.Score;
import com.quiz.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users/quiz")
@Slf4j
@CrossOrigin("http://localhost:3000")
public class UserQuizController {

    private final QuestionService questionService;

    public UserQuizController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/questions/answer")
    public ResponseEntity<Score> checkAnswer(@RequestBody @Valid List<Answer> answers) {
        return ResponseEntity.ok(questionService.getAnswer(answers));
    }

    @GetMapping("/questions/{numberOfQues}")
    public ResponseEntity<List<QuestionDetails>> generateQuestions(@RequestParam(name = "subject", required = false) String subject,
                                                                   @RequestParam(name = "topic", required = false) String topic,
                                                                   @PathVariable int numberOfQues) {
        return ResponseEntity.ok(questionService.generateQuestion(subject, topic, numberOfQues));
    }
}
