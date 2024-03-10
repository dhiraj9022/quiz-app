package com.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "Binary(16)")
    private UUID qid;

    private String topic;

    @Column(name = "description")
    private String desc;
    private String subject;
    private int answer;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;
}


