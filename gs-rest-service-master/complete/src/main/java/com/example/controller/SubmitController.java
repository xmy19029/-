package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import com.example.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/submit")
public class SubmitController {
  @Autowired
  private SubmitService submitService;
  @Autowired
  private ExamService examService;
  @RequestMapping("addOne")
  public int insertSubmit(@RequestBody SubmitItem submitItem){
    int eid = submitItem.getEid();
    List<Problem> problemList = examService.getProblemDetail(eid);
    List<Integer> chs = ListAdaptor.toList(submitItem.getChosen());
    List<Integer> result = new ArrayList<>();
    int score=0;
    for(int i = 0;i < problemList.size();i++){
      Problem now = problemList.get(i);
      int a= now.isA()?1:0;
      int b= now.isB()?1:0;
      int c= now.isC()?1:0;
      int d= now.isD()?1:0;
      if(a==chs.get(4*i)&&b==chs.get(4*i+1)&&c==chs.get(4*i+2)&&d==chs.get(4*i+3)){
        score++;
        result.add(1);
      }else{
        result.add(0);
      }
    }
    submitItem.setScore(score);
    submitItem.setResult(ListAdaptor.fromList(result));
    submitItem.setTimeStamp(DateUtils.dateToStr(new Date()));
    return submitService.insertSubmit(submitItem);
  }
  @RequestMapping("getByUsername")
  public List<SubmitItem> getStudentSubmit(String username){
    return submitService.getStudentSubmit(username);
  }
}
