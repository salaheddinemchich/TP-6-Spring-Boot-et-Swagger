package com.example.student_management;

import com.example.student_management.controllers.StudentController;
import com.example.student_management.entities.Student;
import com.example.student_management.services.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void testSaveStudent() {
        Student student = new Student();
        student.setId(1);
        student.setNom("Mido");
        student.setPrenom("Ali");
        student.setDateNaissance(new Date());

        when(studentService.save(any(Student.class))).thenReturn(student);

        ResponseEntity<Student> response = studentController.save(student);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Mido", response.getBody().getNom());
    }

    @Test
    void testDeleteStudent() {
        when(studentService.delete(1)).thenReturn(true);
        ResponseEntity<Void> response = studentController.delete(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testFindAllStudents() {
        Student s1 = new Student();
        Student s2 = new Student();
        when(studentService.findAll()).thenReturn(Arrays.asList(s1, s2));

        ResponseEntity<List<Student>> response = studentController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testCountStudents() {
        when(studentService.countStudents()).thenReturn(10L);
        ResponseEntity<Long> response = studentController.countStudent();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10L, response.getBody());
    }

    @Test
    void testFindByYear() {
        Collection<Object[]> fake = Arrays.asList(
                new Object[]{1985, 2L},
                new Object[]{1990, 1L}
        );
        when(studentService.findNbrStudentByYear()).thenReturn(fake); // ✅ cohérent

        ResponseEntity<Collection<?>> response = studentController.findByYear();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
}
