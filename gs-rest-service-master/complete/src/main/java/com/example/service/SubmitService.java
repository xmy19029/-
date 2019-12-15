package com.example.service;

import com.example.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class SubmitService {
  @Autowired
  private SubmitService submitService;
  @Autowired
  private ExamService examService;
  @Autowired
  private ProblemService problemService;
  public int insertSubmit(SubmitItem submitItem){
    int eid = submitItem.getEid();
    Exam exam = examService.getOneExam(eid);

    return submitService.insertSubmit(submitItem);
  }
  public List<SubmitItem> getStudentSubmit(String username){
    return submitService.getStudentSubmit(username);
  }
}
