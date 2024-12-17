package com.example.service;

import com.example.entity.Student;

import java.util.List;

public interface StudentService {
    public List<Student> findAll();
    public Student findById(int id);
    public Student save(Student student);
    public Student update(int id,Student student);
    public void deleteById(int id);
}
