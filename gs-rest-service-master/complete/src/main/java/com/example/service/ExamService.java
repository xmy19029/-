package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import com.example.util.*;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ExamService {
  @Autowired
  private ExamMapper examMapper;
  @Autowired
  private ProblemMapper problemMapper;
  public int addExam(Exam exam){
    return examMapper.addExam(exam);
  }
  public int delExam(int eid){
    return examMapper.delExam(eid);
  }
  public int updateExam(int eid,Exam exam){
    return examMapper.updateExam(eid,exam);
  }
  public Exam getOneExam(int eid){
    return examMapper.getOneExam(eid);
  }
  public List<Exam> getAllExam(){
    return examMapper.getAllExam();
  }
  public List<Problem> getProblemDetail(int eid){
    Exam exam = examMapper.getOneExam(eid);
    String t = exam.getProblemList();
    List<Integer> plist = ListAdaptor.toList(t);
    List<Problem> res = new ArrayList<>();
    for(Integer tmp:plist){
      res.add(problemMapper.getOneProblem(tmp));
    }
    return res;
  }
}
