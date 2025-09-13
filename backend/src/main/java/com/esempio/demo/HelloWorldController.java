package com.esempio.demo;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @GetMapping("/")
    public String getUsers() {
        String output = null;
        // Database credentials
        String jdbcUrl = "jdbc:mysql://localhost:3306/miodb";
        String username = "root";
        String password = "";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Create a statement
            statement = connection.createStatement();

            // Execute the query
            String sql = "SELECT * FROM users"; // Assumes there is a table named 'users'
            resultSet = statement.executeQuery(sql);

            // Process the result set
            ArrayList<Map<String, Object>> userList = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", resultSet.getInt("id"));
                userMap.put("name", resultSet.getString("name"));
                userMap.put("email", resultSet.getString("email"));
                // Add other columns as needed
                userList.add(userMap);
            }

            // Convert the list to JSON
            Gson gson = new Gson();
            String jsonOutput = gson.toJson(userList);
            output = jsonOutput;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return output;
    }
}
