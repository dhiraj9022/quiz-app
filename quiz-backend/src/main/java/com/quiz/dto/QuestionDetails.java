package com.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDetails {

    private UUID qId;
    private String desc;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;
}
