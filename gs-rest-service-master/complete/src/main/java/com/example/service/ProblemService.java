package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ProblemService {
  @Autowired
  public ProblemMapper problemMapper;
  public int addProblem(Problem p){
    return problemMapper.addProblem(p);
  }
  public int delProblem(int id){
    return problemMapper.delProblem(id);
  }
  public int updateProblem(int id,Problem p){
    return problemMapper.updateProblem(id,p);
  }
  public List<Problem> getAllProblems(){
    return problemMapper.getAllProblems();
  }
  public Problem getOneProblem(int id){
    return problemMapper.getOneProblem(id);
  }
}
