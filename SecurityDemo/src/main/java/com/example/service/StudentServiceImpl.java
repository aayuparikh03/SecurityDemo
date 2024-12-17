package com.example.service;

import com.example.entity.Student;
import com.example.exception.StudentNotFoundException;
import com.example.repository.StudentRepository;
import com.example.specifications.StudentSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(int id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student update(int id, Student student) {
        Student existingStudent = findById(id);
        if (student.getName() != null) {
            existingStudent.setName(student.getName());
        }
        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteById(int id) {
        studentRepository.deleteById(id);
    }

    // New method to filter students dynamically
    public List<Student> findStudents(String name, Integer minId) {
        Specification<Student> spec = Specification.where(null);

        if (name != null && !name.isEmpty()) {
            spec = spec.and(StudentSpecifications.hasName(name));
        }
        if (minId != null) {
            spec = spec.and(StudentSpecifications.hasIdGreaterThan(minId));
        }

        return studentRepository.findAll(spec);
    }
}
