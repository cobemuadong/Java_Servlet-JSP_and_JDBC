package com.example.bt3.controller;

import com.example.bt3.dbHelpers.ReadQuery;
import com.example.bt3.models.Course;
import com.example.bt3.models.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "courseServlet",urlPatterns = {"/add-course", "/courses/*","/find-course","/course","/edit-course","/delete-course"
,"/search-courses"})
public class CoursesController extends HttpServlet {
    private ReadQuery readQuery;
    @Override
    public void init() throws ServletException {
        try {
            readQuery = new ReadQuery();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(@NotNull HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(uri.startsWith("/add-course")){
            request.getRequestDispatcher("/jsp/add-course.jsp").forward(request, response);
        }
        if(uri.startsWith("/courses")){
            String sorted = request.getParameter("sorted");
            String idString = request.getParameter("id");
            if(idString == null){
                List<Course> courses = null;
                try {
                    courses = readQuery.getCourses();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if(sorted == null){
                    request.setAttribute("courses", courses);
                }
                else{
                    if(sorted.equals("a-z")){
                        courses = courses.stream().sorted(new Comparator<Course>() {
                            @Override
                            public int compare(Course o1, Course o2) {
                                Collator collator = Collator.getInstance(new Locale.Builder()
                                        .setRegion("VN")
                                        .setLanguage("vi")
                                        .build());
                                return collator.compare(o1.getName(), o2.getName());
                            }
                        }).collect(Collectors.toList());
                        request.setAttribute("courses", courses);
                    } else if (sorted.equals("z-a")) {
                        courses = courses.stream().sorted(new Comparator<Course>() {
                            @Override
                            public int compare(Course o1, Course o2) {
                                Collator collator = Collator.getInstance(new Locale.Builder()
                                        .setRegion("VN")
                                        .setLanguage("vi")
                                        .build());
                                return -collator.compare(o1.getName(), o2.getName());
                            }
                        }).collect(Collectors.toList());
                        request.setAttribute("courses", courses);
                    }
                }
                request.getRequestDispatcher("/jsp/list-courses.jsp").forward(request, response);
            }
            else{
                int id = Integer.parseInt(idString);
                String courseName = request.getParameter("name");
                String yearString = request.getParameter("year");
                int year = Integer.parseInt(yearString);
                request.setAttribute("courseName",courseName);
                request.setAttribute("courseId",id);
                request.setAttribute("courseYear", year);
                List<Student> students = null;
                try {
                    students = readQuery.getStudentsInCourse(id, year);
                } catch (SQLException | ParseException e) {
                    throw new RuntimeException(e);
                }
                request.setAttribute("students", students);

                request.getRequestDispatcher("/jsp/students-in-course.jsp").forward(request, response);
            }
        }

        if(uri.startsWith("/search-courses")){
            String search = request.getParameter("search");
            try {
                request.setAttribute("courses",readQuery.getCoursesByName(search));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.getRequestDispatcher("/jsp/search-courses.jsp").forward(request, response);
        }

        if(uri.startsWith("/edit-course")){
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String lecture = request.getParameter("lecture");
            int year = Integer.parseInt(request.getParameter("year"));
            String notes = request.getParameter("notes");

            Course course = new Course(id, name, lecture, year, notes);
            request.setAttribute("course",course);
            request.getRequestDispatcher("/jsp/edit-course.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String pathInfo = request.getPathInfo();
        if(uri.startsWith("/add-course")){
            if(request.getParameter("submit")!=null){
                String id = request.getParameter("course-id");
                String name = request.getParameter("course-name");
                String lecture = request.getParameter("course-lecture");
                String year = request.getParameter("course-year");
                String notes = request.getParameter("course-notes");
                Course course = new Course(Integer.parseInt(id), name, lecture,Integer.parseInt(year), notes);

                try {
                    readQuery.postCourse(course);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                request.setAttribute("msg", "Success");
                request.getRequestDispatcher("/jsp/add-course.jsp").forward(request, response);
            }
        }

        if(uri.startsWith("/delete-course")){
            int id = Integer.parseInt(request.getParameter("id"));

            try {
                readQuery.deleteCourse(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            response.sendRedirect("/courses");
        }

        if(uri.startsWith("/edit-course")){
            int id = Integer.parseInt(request.getParameter("course-id"));
            int oldId = Integer.parseInt(request.getParameter("old-id"));
            int oldYear = Integer.parseInt(request.getParameter("old-year"));
            String name = request.getParameter("course-name");
            String lecture = request.getParameter("course-lecture");
            String year = request.getParameter("course-year");
            String notes = request.getParameter("course-notes");
            Course course = new Course(id, name, lecture,Integer.parseInt(year), notes);

            try {
                readQuery.updateCourse(oldId, oldYear, course);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            response.sendRedirect("/courses");
        }

        if(uri.startsWith("/courses")){
            if(pathInfo.startsWith("/delete-student-from-course")){
                int studentId = Integer.parseInt(request.getParameter("student-id"));
                int courseId = Integer.parseInt(request.getParameter("course-id"));
                int year = Integer.parseInt(request.getParameter("year"));
                String courseName = request.getParameter("course-name");
                try {
                    readQuery.deleteEnrollmentsByKey(courseId, studentId, year);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                courseName =  URLEncoder.encode(courseName, StandardCharsets.UTF_8);
                String redirectUrl = "/courses?id="+courseId+"&name="+courseName+"&year="+year;
                response.sendRedirect(redirectUrl);
            }

            if(pathInfo.startsWith("/insert-into-course")){
                String queryString = request.getParameter("queryString");
                int studentId = Integer.parseInt(request.getParameter("student-id"));
                int courseId = Integer.parseInt(request.getParameter("course-id"));
                int year = Integer.parseInt(request.getParameter("year"));
                String scoreString = request.getParameter("student-score");
                double score = -1;
                if(!scoreString.equals("")){
                    score = Double.parseDouble(scoreString);
                }
                try {
                    readQuery.insertIntoCourse(studentId, courseId, year, score);
                    response.sendRedirect("/courses?"+queryString);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void destroy() {
        try {
            readQuery.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
