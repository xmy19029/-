package com.example.mapper;

import com.example.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ProblemMapper {
  public int addProblem(Problem p);
  public int delProblem(int id);
  public int updateProblem(@Param("id") int id,@Param("p") Problem p);
  public List<Problem> getAllProblems();
  public Problem getOneProblem(int id);
}
