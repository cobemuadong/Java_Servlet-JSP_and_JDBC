package com.example.bt3.controller;

import java.io.*;
import java.sql.SQLException;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.example.bt3.dbHelpers.ReadQuery;
import com.example.bt3.models.Course;
import com.example.bt3.models.Score;
import com.example.bt3.models.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.jetbrains.annotations.NotNull;

@WebServlet(name = "studentServlet",
        urlPatterns = {"/add-student","/students","/students/*","/edit-student","/search-student/*","/delete-student","/student"})
public class StudentsController extends HttpServlet {
    private ReadQuery readQuery;
    public void init() {
        try {
            readQuery = new ReadQuery();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doGet(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String uri = request.getRequestURI();
        String pathInfo = request.getPathInfo();

        if(uri.startsWith("/add-student")){
            try {
                request.getRequestDispatcher("/jsp/add-student.jsp").forward(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }

        if(uri.startsWith("/edit-student")){
            try {
                String id = request.getParameter("id");
                String name = request.getParameter("name");
                String grade = request.getParameter("grade");
                String birthday = request.getParameter("birthday");
                String address = request.getParameter("address");
                String notes = request.getParameter("notes");
//                Student student = readQuery.getStudentById(Integer.parseInt(id));
                SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                Student student = new Student(Integer.parseInt(id), name, grade, sdf.parse(birthday), address, notes);
                request.setAttribute("student",student);
                request.getRequestDispatcher("/jsp/edit-student.jsp").forward(request, response);
            } catch (ServletException | ParseException e) {
                throw new RuntimeException(e);
            }
        }

        if(uri.startsWith("/students")){
            String typeOfSort = request.getParameter("sorted");
            if(typeOfSort != null){
                switch (typeOfSort){
                    case "a-z":{
                        List<Student> studentList = null;
                        try {
                            studentList = readQuery.getStudents();
                        } catch (SQLException | ParseException e) {
                            throw new RuntimeException(e);
                        }
                        List<Student> students = studentList.stream().sorted(new NameComparator()).collect(Collectors.toList());
                        request.setAttribute("students", students);
                        break;
                    }
                    case "z-a":{
                        List<Student> studentList = null;
                        try {
                            studentList = readQuery.getStudents();
                        } catch (SQLException | ParseException e) {
                            throw new RuntimeException(e);
                        }
                        List<Student> students = studentList.stream().sorted(new NameComparator()).collect(Collectors.toList());
                        Collections.reverse(students);
                        request.setAttribute("students", students);
                        break;
                    }
                    case "increasing-grade":{
                        List<Student> studentList = null;
                        try {
                            studentList = readQuery.getStudents();
                        } catch (SQLException | ParseException e) {
                            throw new RuntimeException(e);
                        }

                        List<Student> students = studentList.stream().sorted(new Comparator<Student>() {
                            @Override
                            public int compare(Student o1, Student o2) {
                                return o1.getGrade() - o2.getGrade();
                            }
                        }).collect(Collectors.toList());
                        request.setAttribute("students", students);
                        break;
                    }
                    case "decreasing-grade":{
                        List<Student> studentList = null;

                        try {
                            studentList = readQuery.getStudents();
                        } catch (SQLException | ParseException e) {
                            throw new RuntimeException(e);
                        }

                        List<Student> students = studentList.stream().sorted(new Comparator<Student>() {
                            @Override
                            public int compare(Student o1, Student o2) {
                                return o1.getGrade() - o2.getGrade();
                            }
                        }).collect(Collectors.toList());
                        Collections.reverse(students);
                        request.setAttribute("students", students);
                        break;
                    }
                }
            }
            else{
                try {
                    request.setAttribute("students", readQuery.getStudents());
                } catch (SQLException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                request.getRequestDispatcher("jsp/list-students.jsp").forward(request,response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        if(uri.startsWith("/search-student")){
            String name = request.getParameter("search");
            if(name != null){
                List<Student> students = null;
                try {
                    students = readQuery.getStudentsByName(name);
                } catch (SQLException | ParseException e) {
                    throw new RuntimeException(e);
                }

                request.setAttribute("students",students);
            }
            try {
                request.getRequestDispatcher("/jsp/search-student.jsp").forward(request,response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
        if(uri.startsWith("/student")) {
            int id = Integer.parseInt(request.getParameter("id"));
            int pid = Integer.parseInt(request.getParameter("pid"));
            String yearString = request.getParameter("year");
            if (pid == 0) {
                if (yearString != null) {
                    List<Course> courses = null;
                    try {
                        courses = readQuery.getCoursesOfStudent(id, Integer.parseInt(yearString));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    request.setAttribute("courses", courses);
                }
                try {
                    request.getRequestDispatcher("/jsp/courses-of-student.jsp").forward(request, response);
                } catch (ServletException e) {
                    throw new RuntimeException(e);
                }
            }
            if(pid == 1){
                if(yearString != null){
                    List<Score> scores = null;
                    try {
                        scores = readQuery.getScoresByStudentId(id, Integer.parseInt(yearString));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    request.setAttribute("scores", scores);

                }
                try {
                    request.getRequestDispatcher("/jsp/score-board.jsp").forward(request, response);
                } catch (ServletException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    protected void doPost(@NotNull HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(uri.equals("/add-student")){
            if (request.getParameter("submit") != null) {
                Student student = getStudentParam(request);
                try {
                    readQuery.postStudent(student);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                response.sendRedirect("add-student");
            }
        }

        if(uri.equals("/edit-student")){
            int oldId = Integer.parseInt(request.getParameter("old-id"));
            Student student = getStudentParam(request);
            try {
                readQuery.updateStudents(oldId, student);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.sendRedirect("/students");

        }

        if(uri.startsWith("/delete-student")){
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                readQuery.deleteStudent(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.sendRedirect("/students");
        }
    }

    private @NotNull Student getStudentParam(@NotNull HttpServletRequest request) {
        String id = request.getParameter("student-id");
        String name = request.getParameter("student-name");
        String grade = request.getParameter("student-grade");
        String birthday = request.getParameter("student-birthday");
        String address = request.getParameter("student-address");
        String notes = request.getParameter("student-notes");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return new Student(Integer.parseInt(id), name, grade, sdf.parse(birthday), address, notes);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
        try {
            readQuery.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static class NameComparator implements Comparator<Student> {

        @Override
        public int compare(@NotNull Student o1, @NotNull Student o2) {

            String name1 = o1.getName();
            String name2 = o2.getName();
            int lastSpaceIndex1 = name1.lastIndexOf(" ");
            int lastSpaceIndex2 = name2.lastIndexOf(" ");
            String firstPart1 = name1.substring(0, lastSpaceIndex1);
            String secondPart1 = name1.substring(lastSpaceIndex1 + 1);
            String firstPart2 = name2.substring(0, lastSpaceIndex2);
            String secondPart2 = name2.substring(lastSpaceIndex2 + 1);
            Collator collator = Collator.getInstance(new Locale.Builder()
                    .setRegion("VN")
                    .setLanguage("vi")
                    .build());
            int res = collator
                    .compare(secondPart1, secondPart2);
            if(res != 0){
                return  res;
            }
            else{
                res = collator.compare(firstPart1, firstPart2);
                return res;
            }
        }
    }
}
