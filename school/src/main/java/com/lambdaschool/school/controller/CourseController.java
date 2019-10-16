package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.service.CourseService;
import com.lambdaschool.school.view.CountStudentsInCourses;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    @Autowired
    private CourseService courseService;

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @ApiOperation(value = "list all courses", response = Course.class, responseContainer = "List")
    @ApiImplicitParams({
                           @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                                             value = "Results page you want to retrieve (1..N)"),
                           @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                                             value = "Number of records per page."),
                           @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                                             value = "Sorting criteria in the format: property(,asc|desc). " +
                                                 "Default sort order is ascending. " +
                                                 "Multiple sort criteria are supported.")})
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses(HttpServletRequest request, @PageableDefault(page = 0, size = 3)
        Pageable pageable)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " just accessed!");

        ArrayList<Course> myCourses = courseService.findAll(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @ApiOperation(value = "list all courses with student counts", response = CountStudentsInCourses.class, responseContainer = "List")
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses(HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " just accessed!");

        return new ResponseEntity<>(courseService.getCountStudentsInCourse(), HttpStatus.OK);
    }

    @ApiOperation(value = "delete a course using courseid")
    @ApiResponses(value = {@ApiResponse(code = 200,
                                        message = "Course Deleted"), @ApiResponse(code = 404,
                                                                                   message = "Course Not Found",
                                                                                   response = ErrorDetail.class)})
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(@ApiParam(value = "Course Id",  required = true, example = "1")
                                              @PathVariable long courseid, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " just accessed! Parameter: " + courseid);

        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
