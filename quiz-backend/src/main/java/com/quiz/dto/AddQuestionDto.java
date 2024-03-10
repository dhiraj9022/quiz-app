package com.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddQuestionDto {

    @NotBlank @NotNull
    private String topic;

    @NotBlank @NotNull
    private String desc;

    @NotBlank @NotNull
    private String subject;

    @NotNull @Min(1) @Max(4)
    private int answer;

    @NotBlank @NotNull
    private String optionOne;

    @NotBlank @NotNull
    private String optionTwo;

    @NotBlank @NotNull
    private String optionThree;

    @NotBlank @NotNull
    private String optionFour;
}
