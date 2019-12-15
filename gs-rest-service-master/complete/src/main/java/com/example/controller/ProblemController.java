package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
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

  @RequestMapping("getOne")
  public ResponseEntity<Problem> getOneProblem(int id){
    Problem res = problemService.getOneProblem(id);
    HttpStatus status = res != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    return new ResponseEntity<Problem>(res,status);
  }

  @RequestMapping("delOne")
  public int delOneProblem(int id){
    return problemService.delProblem(id);

  }

  @RequestMapping("updOne")
  public int updateProblem(@RequestParam("id") int id,@RequestBody Problem p){
    return problemService.updateProblem(id,p);
  }
}
