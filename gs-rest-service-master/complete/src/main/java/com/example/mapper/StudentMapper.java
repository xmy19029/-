package com.example.mapper;

import com.example.entity.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface StudentMapper {
  Student getOneStudent(int sid);
  List<Student> getAllStudent();
  Student getStudentByMajor(String major);
  Student GetStudentByAcadmy(String acadmy);
  Student GetStudentByGrade(String grade);
  void insertStudent(int sid,String name,String acadmy,String major,String grade);
  void updateStudent(int sid,String name,String acadmy,String major,String grade);
  void deleteStudent(int sid);
}
