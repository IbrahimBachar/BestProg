package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:postgresql://localhost:5432/ibra";
        String user = "postgres";
        String password = "IBRAHIM2525";
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "save":
                    saveStudent(request, response);
                    break;
                case "delete":
                    deleteStudent(request, response);
                    break;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException(ex);
        }
    }

    private void saveStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");

        String sql = "INSERT INTO student (id, fname, lname) VALUES (?, ?, ?) ON CONFLICT (id) DO UPDATE SET fname = EXCLUDED.fname, lname = EXCLUDED.lname";
        
        try (Connection conn = getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.setString(2, fname);
            pst.setString(3, lname);
            pst.executeUpdate();
        }

        response.sendRedirect("StudentServlet");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        String sql = "DELETE FROM student WHERE id = ?";
        
        try (Connection conn = getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }

        response.sendRedirect("StudentServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM student";
        
        try (Connection conn = getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql); 
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                students.add(new Student(id, fname, lname));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        request.setAttribute("students", students);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
