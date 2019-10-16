package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.service.InstructorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/instructors")
public class InstructorController
{
  @Autowired
  private InstructorService instructorService;

  @ApiOperation(value = "list all instructors", response = Instructor.class, responseContainer = "List")
  @GetMapping(value = "/instructors",
              produces = {"application/json"})
  public ResponseEntity<?> listAllInstructors()
  {
    return new ResponseEntity<>(instructorService.findAll(), HttpStatus.OK);
  }

  @ApiOperation(value = "add new instructor")
  @PostMapping(value = "/instructor",
               consumes = {"application/json"})
  public ResponseEntity<?> addInstructor(@Valid
                                         @RequestBody Instructor instructor)
  {
    instructorService.save(instructor);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @ApiOperation(value = "edit an instructor")
  @ApiResponses(value = {
      @ApiResponse(code = 200,
                   message = "Instructor Updated"),
      @ApiResponse(code = 404,
                   message = "Instructor Not Found",
                   response = ErrorDetail.class)
  })
  @PutMapping(value = "/instructor/{instructid}",
              consumes = {"application/json"})
  public ResponseEntity<?> editInstructor(@RequestBody Instructor instructor,
                                          @ApiParam(value = "Instructor Id",
                                                    required = true,
                                                    example = "1")
                                          @PathVariable long instructid)
  {
    instructorService.update(instructor, instructid);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ApiOperation(value = "delete an instructor")
  @ApiResponses(value = {
      @ApiResponse(code = 200,
                   message = "Instructor Deleted"),
      @ApiResponse(code = 404,
                   message = "Instructor Not Found",
                   response = ErrorDetail.class)
  })
  @DeleteMapping(value = "/instructor/{instructid}")
  public ResponseEntity<?> deleteInstructor(
      @ApiParam(value = "Instructor Id",
                required = true,
                example = "1")
      @PathVariable long instructid)
  {
    instructorService.delete(instructid);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
