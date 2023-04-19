package com.example.bt3.dbHelpers;

import com.example.bt3.models.Course;
import com.example.bt3.models.Score;
import com.example.bt3.models.Student;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReadQuery {
    private Connection connection;
    private SimpleDateFormat sdf;
    private PreparedStatement fkChecked;
    private PreparedStatement fkUnchecked;
    /**
     * Create jdbc connection for {@code connection}
     */
    public ReadQuery() throws IOException, ClassNotFoundException, SQLException {
        //Get resources from doConn.properties
        Properties props = new Properties();
        InputStream instr = getClass().getClassLoader().getResourceAsStream("dbConn.properties");
        props.load(instr);
        if(instr != null){
            instr.close();
        }

        String driver = props.getProperty("driver.name");
        String url = props.getProperty("server.name");
        String username = props.getProperty("user.name");
        String password = props.getProperty("user.password");

        //Connection with database through jdbc drive
        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        fkChecked = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=1");
        fkUnchecked = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
    }
    public void postStudent(@NotNull Student student) throws SQLException {
        String sql = "insert into students(id,name,grade,birthday,address,notes) values (" +
                student.getId() + "," +
                "'" + student.getName() + "'," +
                student.getGrade() + "," +
                "'" + sdf.format(student.getBirthday()) + "'," +
                "'" + student.getAddress() + "'," +
                "'" + student.getNotes() + "'" +")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void deleteStudent(int id) throws SQLException {
        String sql = "delete from students where id = " + id;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public List<Student> getStudents() throws SQLException, ParseException {
        String query = "select * from students";
        List<Student> students = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        retrieveStudentsFromResultSet(students, preparedStatement);
        preparedStatement.close();
        return students;
    }

    public Student getStudentById(int studentId) throws ParseException, SQLException {
        String query = "select * from students where id = "+ studentId;
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        String grade = resultSet.getString("grade");
        String birthday = resultSet.getString("birthday");
        String address = resultSet.getString("address");
        String notes = resultSet.getString("notes");
        preparedStatement.close();
        java.util.Date date = sdf.parse(birthday);
        return new Student(Integer.parseInt(id),name,grade,date,address,notes);
    }
    public void updateStudents(int id , @NotNull Student student) throws SQLException {

        String sql = "update students set id=?, name=?,grade=?,birthday=?,address=?,notes=? where id ="+id;
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,student.getId());
        preparedStatement.setString(2, student.getName());
        preparedStatement.setInt(3,student.getGrade());
        preparedStatement.setString(4,sdf.format(student.getBirthday()));
        preparedStatement.setString(5,student.getAddress());
        preparedStatement.setString(6,student.getNotes());
        if(student.getId() != id){
            fkUnchecked.executeUpdate();
            preparedStatement.executeUpdate();
            updateEnrollmentsStudentRef(id, student.getId());
            fkChecked.executeUpdate();
        }
        else{
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();

    }

    public List<Student> getStudentsByName(String studentName) throws SQLException, ParseException {
        String query = "select * from students where name like ?";
        List<Student> students = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "%"+studentName+"%");
        retrieveStudentsFromResultSet(students, preparedStatement);
        preparedStatement.close();
        return students;
    }

    private void retrieveStudentsFromResultSet(List<Student> students, @NotNull PreparedStatement preparedStatement) throws SQLException, ParseException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        String grade = resultSet.getString("grade");
        String birthday = resultSet.getString("birthday");
        String address = resultSet.getString("address");
        String notes = resultSet.getString("notes");
        students.add(new Student(Integer.parseInt(id),name,grade,sdf.parse(birthday),address,notes));

        }
    }

    public void postCourse(@NotNull Course course) throws SQLException {
        String sql = "insert into courses(id,name,lecture,year,notes) values (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, course.getId());
        preparedStatement.setString(2,course.getName());
        preparedStatement.setString(3, course.getLecture());
        preparedStatement.setInt(4, course.getYear());
        preparedStatement.setString(5, course.getNotes());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    public void updateCourse(int id, int year, @NotNull Course course) throws SQLException {
        String sql = "update courses set id=?, name=?,lecture=?,year=?,notes=? where id =? and year = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, course.getId());
        preparedStatement.setString(2, course.getName());
        preparedStatement.setString(3,course.getLecture());
        preparedStatement.setInt(4,course.getYear());
        preparedStatement.setString(5, course.getNotes());
        preparedStatement.setInt(6, id);
        preparedStatement.setInt(7, year);
        if(course.getId() != id || course.getYear() != year){
            fkUnchecked.executeUpdate();
            preparedStatement.executeUpdate();
            updateEnrollmentsCourseRef(id, year, course.getId(), course.getYear());
            fkChecked.executeUpdate();
        }
        else{
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();
    }
    public void deleteCourse(int id) throws SQLException {
        String sql = "delete from courses where id = " + id;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    public List<Course> getCoursesByName(String courseName) throws SQLException {
        String query = "select * from courses where name like ?";
        List<Course> courses = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "%"+courseName+"%");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String lecture = resultSet.getString("lecture");
            String year = resultSet.getString("year");
            String notes = resultSet.getString("notes");
            courses.add(new Course(Integer.parseInt(id),name,lecture,Integer.parseInt(year),notes));
        }
        preparedStatement.close();
        return courses;
    }
    public List<Course> getCourses() throws SQLException {
        String query = "select * from courses";
        List<Course> courses = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String lecture = resultSet.getString("lecture");
            String year = resultSet.getString("year");
            String notes = resultSet.getString("notes");
            courses.add(new Course(Integer.parseInt(id),name,lecture,Integer.parseInt(year),notes));
        }
        preparedStatement.close();
        return courses;
    }
    public List<Student> getStudentsInCourse(int courseId, int year) throws SQLException, ParseException {
        String query = "select * " +
                "from course_enrollments " +
                "join students " +
                "on id = student_id " +
                "where course_id=? and year = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, courseId);
        preparedStatement.setInt(2, year);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Student> students = new ArrayList<>();
        while (resultSet.next()){
            String id = resultSet.getString("student_id");
            String name = resultSet.getString("name");
            String grade = resultSet.getString("grade");
            String birthday = resultSet.getString("birthday");
            String address = resultSet.getString("address");
            String notes = resultSet.getString("notes");
            students.add(new Student(Integer.parseInt(id),name,grade,sdf.parse(birthday),address,notes));
        }
        preparedStatement.close();
        return students;
    }

    public void deleteEnrollmentsByKey(int courseID, int studentId, int year) throws SQLException {
        String query = "delete from course_enrollments where course_id=? and student_id = ? and year = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, courseID);
        preparedStatement.setInt(2, studentId);
        preparedStatement.setInt(3, year);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void insertIntoCourse(int studentId, int courseId, int year) throws SQLException {
        String sql = "insert into course_enrollments(student_id, course_id, year) values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, studentId);
        preparedStatement.setInt(2, courseId);
        preparedStatement.setInt(3, year);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public List<Course> getCoursesOfStudent(int studentId, int courseYear) throws SQLException {
        List<Course> courses = new ArrayList<>();
        String query = "select c.* from courses as c join course_enrollments as ce on c.id = ce.course_id and c.year = ce.year where student_id = ? and ce.year = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, studentId);
        preparedStatement.setInt(2, courseYear);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String lecture = resultSet.getString("lecture");
            String year = resultSet.getString("year");
            String notes = resultSet.getString("notes");
            courses.add(new Course(Integer.parseInt(id),name,lecture,Integer.parseInt(year),notes));
        }
        preparedStatement.close();
        return courses;
    }

    public List<Score> getScoresByStudentId(int studentId, int year) throws SQLException {
        String query = "select ce.course_id course_id, c.name course_name, c.year year, ce.score score from course_enrollments as ce join courses as c on ce.course_id = c.id and ce.year = c.year where ce.student_id =? and ce.year = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,studentId);
        preparedStatement.setInt(2, year);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Score> scores = new ArrayList<>();
        while(resultSet.next()){
            int id = Integer.parseInt(resultSet.getString("course_id"));
            String name = resultSet.getString("course_name");
            int courseYear = Integer.parseInt(resultSet.getString("year"));
            String scoreString = resultSet.getString("score");
            double score = -1;
            if(scoreString != null){
                score = Double.parseDouble(resultSet.getString("score"));
            }
            scores.add(new Score(studentId ,id, name, courseYear, score));
        }
        preparedStatement.close();
        return scores;
    }

    public void updateEnrollmentsStudentRef(int oldStudentId, int studentId) throws SQLException {
        String sql = "update course_enrollments set student_id="+studentId+" where student_id="+oldStudentId;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void updateEnrollmentsCourseRef(int oldCourseId, int oldCourseYear, int newCourseId, int newCourseYear) throws SQLException {
        String sql = "update course_enrollments set course_id =?, year = ? where course_id = ? and year = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, newCourseId);
        preparedStatement.setInt(2, newCourseYear);
        preparedStatement.setInt(3,  oldCourseId);
        preparedStatement.setInt(4, oldCourseYear);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void close() throws SQLException {
        fkChecked.close();
        fkUnchecked.close();
        connection.close();
    }
}
