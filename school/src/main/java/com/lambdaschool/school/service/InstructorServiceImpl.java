package com.lambdaschool.school.service;

import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.repository.CourseRepository;
import com.lambdaschool.school.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Service(value = "instructorService")
public class InstructorServiceImpl implements InstructorService
{
  @Autowired
  private InstructorRepository instructrepos;

  @Override
  public ArrayList<Instructor> findAll()
  {
    ArrayList<Instructor> rtnList = new ArrayList<>();
    instructrepos.findAll().iterator().forEachRemaining(rtnList::add);
    return rtnList;
  }

  @Transactional
  @Override
  public Instructor save(Instructor instructor)
  {
    Instructor newInstructor = new Instructor();

    newInstructor.setInstructname(instructor.getInstructname());

    return instructrepos.save(newInstructor);
  }

  @Transactional
  @Override
  public Instructor update(Instructor instructor, long id) throws ResourceNotFoundException
  {
    Instructor currentInstructor = instructrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));

    if (instructor.getInstructname() != null)
    {
        currentInstructor.setInstructname(instructor.getInstructname());
    }

    return instructrepos.save(currentInstructor);
  }

  @Transactional
  @Override
  public void delete(long id) throws ResourceNotFoundException
  {
    Instructor currentInstructor = instructrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));

      for (Course c : currentInstructor.getCourses())
    {
        c.setInstructor(null);
    }

    instructrepos.deleteById(id);
  }
}
