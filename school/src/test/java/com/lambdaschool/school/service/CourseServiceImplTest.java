package com.lambdaschool.school.service;

import com.lambdaschool.school.SchoolApplication;
import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolApplication.class)
public class CourseServiceImplTest
{
  @Autowired
  private CourseService courseService;

  @Before
  public void setUp() throws Exception
  {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public void deleteFound()
  {
    courseService.delete(2L);
    assertEquals(5, courseService.findAll().size());
  }

  @Test(expected = ResourceNotFoundException.class)
  public void deleteNotFound()
  {
    courseService.delete(313L);
    assertEquals(5, courseService.findAll().size());
  }

  @Test
  public void findCourseById()
  {
    assertEquals("Data Science", courseService.findCourseById(1L).getCoursename());
  }
}