package com.example.mapper;

import com.example.entity.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ProblemMapper {
  public int addProblem(Problem p);
  public int delProblem(int id);
  public int updateProblem(int id,Problem p);
  public List<Problem> getAllProblems();
  public Problem getOneProblem();
}
