package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/problems")
public class ProblemController {
  @Autowired
  private ProblemService problemService;

  @RequestMapping("addOne")
  public int addOneProblem(@RequestBody Problem problem){
    return problemService.addProblem(problem);
  }

  @RequestMapping("getAll")
  public List<Problem> getAllProblems(){
    List<Problem> p = problemService.getAllProblems();
    return problemService.getAllProblems();
  }


}
