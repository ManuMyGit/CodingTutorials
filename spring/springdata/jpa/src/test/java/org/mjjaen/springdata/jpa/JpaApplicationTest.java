package org.mjjaen.springdata.jpa;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mjjaen.springdata.jpa.businessObject.*;
import org.mjjaen.springdata.jpa.service.AddressService;
import org.mjjaen.springdata.jpa.service.CourseService;
import org.mjjaen.springdata.jpa.service.StudentService;
import org.mjjaen.springdata.jpa.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class JpaApplicationTest {
    @Autowired
    private AddressService addressService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    //@Test
    @Order(1)
    public void testAddressInitialDataCreation() {
        List<Address> addressList = addressService.findAll();
        assertNotNull(addressList);
        assertEquals(addressList.size(), 10);
    }

    //@Test
    @Order(2)
    public void testCourseInitialDataCreation() {
        List<Course> courseList = courseService.findAll();
        assertNotNull(courseList);
        assertEquals(courseList.size(), 7);
    }

    //@Test
    @Order(3)
    public void testStudentInitialDataCreation() {
        List<Student> studentList = studentService.findAll();
        assertNotNull(studentList);
        assertEquals(studentList.size(), 10);
    }

    //@Test
    @Order(4)
    public void testTeacherInitialDataCreation() {
        List<Teacher> teacherList = teacherService.findAll();
        assertNotNull(teacherList);
        assertEquals(teacherList.size(), 5);
    }

    //@Test
    @Order(5)
    public void testStudentLazyFetch() {
        List<Student> studentList = studentService.findAll();
        assertNotNull(studentList);
        studentList.forEach(e -> {
            assertThrows(LazyInitializationException.class, () -> System.out.println(e.getAddress()));
            assertThrows(LazyInitializationException.class, () -> System.out.println(e.getCourses()));
        });
    }

    //@Test
    @Order(6)
    public void testTeacherEagerFetch() {
        List<Teacher> teacherList = teacherService.findAll();
        assertNotNull(teacherList);
        teacherList.forEach(e -> {
            assertNotNull(e.getCourses());
            assertTrue(e.getCourses().size() >= 1);
        });
    }

    //@Test
    @Order(7)
    public void testRepositoryMethods() {
        Teacher teacher = teacherService.getOne(1L);
        assertNotNull(teacher);
        long count = studentService.count();
        assertEquals(10, count);
        assertFalse(addressService.existsById(20L));
        Optional<Course> course = courseService.findById(50L);
        assertNotNull(course);
        assertFalse(course.isPresent());
        Iterable<Student> students = studentService.findAllIterable(Sort.by("firstName", "lastName").ascending());
        students.forEach(e -> assertNotNull(e.getId()));

        Address address = new Address();
        address.setStreet("5th Ave");
        address.setCity("New York City");
        address.setState("NY");
        address.setZipCode("01005");
        address.setCountry("US");
        //Insert new address
        address = addressService.save(address);
        assertEquals(11, addressService.count());
        address.setCountry("SP");
        //Update existing address
        addressService.save(address);
        assertEquals(11, addressService.count());
        addressService.deleteById(address.getId());
        assertEquals(10, addressService.count());
        address = addressService.save(address);
        assertEquals(11, addressService.count());
        addressService.delete(address);
        assertEquals(10, addressService.count());
    }

    //@Test
    @Order(8)
    public void testQueryMethods() {
        List<Student> studentList = studentService.findByFirstName("William");
        assertNotNull(studentList);
        assertEquals(1, studentList.size());
        studentList = studentService.findByFirstNameAndLastName("Miguel", "de Cervantes");
        assertNotNull(studentList);
        assertEquals(1, studentList.size());
        studentList = studentService.findByFirstNameOrLastName("Dante", "Hernandez");
        assertNotNull(studentList);
        assertEquals(2, studentList.size());
        studentList = studentService.findByAgeBetween(50, 55);
        assertNotNull(studentList);
        assertEquals(6, studentList.size());
        studentList = studentService.findByAgeLessThan(52);
        assertNotNull(studentList);
        assertEquals(2, studentList.size());
        studentList = studentService.findByAgeLessThanEqual(52);
        assertNotNull(studentList);
        assertEquals(3, studentList.size());
        studentList = studentService.findByAgeGreaterThan(56);
        assertNotNull(studentList);
        assertEquals(3, studentList.size());
        studentList = studentService.findByAgeGreaterThanEqual(56);
        assertNotNull(studentList);
        assertEquals(4, studentList.size());
        studentList = studentService.findByFirstNameIgnoreCaseLikeOrderByFirstNameAsc("%il%");
        assertNotNull(studentList);
        assertEquals(2, studentList.size());
        assertEquals("Emily", studentList.get(0).getFirstName());
        studentList = studentService.findByFirstNameNotLike("%il%");
        assertNotNull(studentList);
        assertEquals(8, studentList.size());
        studentList = studentService.findByFirstNameStartsWith("M");
        assertNotNull(studentList);
        assertEquals(4, studentList.size());
        studentList = studentService.findByLastNameEndsWith("s");
        assertNotNull(studentList);
        assertEquals(2, studentList.size());
        studentList = studentService.findByLastNameContaining("e");
        assertNotNull(studentList);
        assertEquals(6, studentList.size());
        List<Integer> ages = Arrays.asList(50, 55, 59);
        studentList = studentService.findByAgeIn(ages);
        assertNotNull(studentList);
        assertEquals(3, studentList.size());

        Optional<Teacher> teacher = teacherService.findOptionalByFirstName("Unmatched");
        assertNotNull(teacher);
        assertFalse(teacher.isPresent());
        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
        Page<Teacher> teacherPage = teacherService.findByFirstNameLike("%l%", firstPageWithTwoElements);
        assertEquals(3, teacherPage.getTotalElements());
        assertEquals(2, teacherPage.getNumberOfElements());
        assertEquals(2, teacherPage.getTotalPages());
        assertNotNull(teacherPage.getContent());
        assertEquals(2, teacherPage.getContent().size());
        assertTrue(teacherPage.isFirst());
        firstPageWithTwoElements = firstPageWithTwoElements.next();
        teacherPage = teacherService.findByFirstNameLike("%l%", firstPageWithTwoElements);
        assertEquals(3, teacherPage.getTotalElements());
        assertEquals(1, teacherPage.getNumberOfElements());
        assertEquals(2, teacherPage.getTotalPages());
        assertNotNull(teacherPage.getContent());
        assertEquals(1, teacherPage.getContent().size());
        assertFalse(teacherPage.isFirst());
        assertTrue(teacherPage.isLast());

        List<String> names = Arrays.asList("Marie", "Isaac", "Rosalind");
        Sort sort = Sort.by(Sort.Direction.ASC, "lastName");
        List<Teacher> teacherList = teacherService.findByFirstNameIn(names, sort);
        assertNotNull(teacherList);
        assertEquals(3, teacherList.size());
        assertEquals("Marie", teacherList.get(0).getFirstName());
        assertEquals("Rosalind", teacherList.get(1).getFirstName());
        assertEquals("Isaac", teacherList.get(2).getFirstName());

        teacherList = teacherService.findTop3ByLastNameLike("%");
        assertNotNull(teacherList);
        assertEquals(3, teacherList.size());
    }

    //@Test
    @Order(9)
    public void testAtQueryMethods() {
        List<Course> courseList = courseService.findByTitleSentAsParameter("Structure of the DNA");
        assertNotNull(courseList);
        assertEquals(1, courseList.size());
        courseList = courseService.findByTitleSentAsParameterParamName("The structure of the atom");
        assertNotNull(courseList);
        assertEquals(1, courseList.size());
        courseList = courseService.findByTitleSentAsParameterNativeQuery("Special relativity");
        assertNotNull(courseList);
        assertEquals(1, courseList.size());
        courseList = courseService.findByTitleSentAsParameterParamNameNativeQuery("Radioactivity");
        assertNotNull(courseList);
        assertEquals(1, courseList.size());
    }

    //@Test
    @Order(10)
    public void testNamedQueryMethods() {
        List<Student> studentList = studentService.findByGenderNamedQuery(Gender.MALE, StudentService.SPRING_DATA_REPOSITORY);
        assertNotNull(studentList);
        assertEquals(5, studentList.size());
        assertEquals("Antonio", studentList.get(0).getFirstName());
        studentList = studentService.findByGenderNamedQuery(Gender.FEMALE, StudentService.CUSTOM_REPOSITORY);
        assertNotNull(studentList);
        assertEquals(5, studentList.size());
        assertEquals("Emily", studentList.get(0).getFirstName());
    }

    //@Test
    @Order(11)
    public void testCustomRepositoryAndQueryDSLMethods() {
        List<Student> studentList = studentService.findByGenderNamedQuery(Gender.MALE, StudentService.CUSTOM_REPOSITORY);
        assertNotNull(studentList);
        assertEquals(5, studentList.size());
        assertEquals("Antonio", studentList.get(0).getFirstName());
        studentList = studentService.findByGenderNamedQuery(Gender.FEMALE, StudentService.CUSTOM_REPOSITORY_QUERYDSL);
        assertNotNull(studentList);
        assertEquals(5, studentList.size());
        assertEquals("Emily", studentList.get(0).getFirstName());
    }

    //@Test
    @Order(12)
    public void testAuditing() {
        Address address = new Address();
        address.setStreet("5th Ave");
        address.setCity("New York City");
        address.setState("NY");
        address.setZipCode("01005");
        address.setCountry("US");
        //Insert new address
        address = addressService.save(address);
        //Check auditing
        assertNotNull(address.getCreatedDate());
        assertNotNull(address.getLastModifiedDate());
        assertTrue(address.getCreatedDate().before(Date.from(Instant.now())));
        assertTrue(address.getLastModifiedDate().before(Date.from(Instant.now())));
        assertEquals(address.getCreatedDate(), address.getLastModifiedDate());
        address.setStreet("6th Ave");
        address = addressService.save(address);
        assertTrue(address.getCreatedDate().before(address.getLastModifiedDate()));
    }

    @Test
    @Order(13)
    public void testProjections() {
        List<AddressDto> addressDtoList = addressService.findByState("NY");
        assertEquals(addressDtoList.size(), 10);
        addressDtoList = addressService.findByStateOrderByStreet("NY");
        assertEquals(addressDtoList.size(), 10);
        List<AddressView> addressDtoList2 = addressService.findByStateOrderByCountry("NY");
        assertEquals(addressDtoList2.size(), 10);
    }
}
