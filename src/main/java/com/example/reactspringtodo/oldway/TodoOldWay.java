//package com.example.reactspringtodo.oldway;
//
//import com.fasterxml.jackson.databind.util.JSONPObject;
//import org.apache.catalina.connector.Request;
//
//import java.sql.*;
//import java.util.ArrayList;
//
//public class TodoOldWay {
//
//    public TodoOldWay() throws SQLException {
//    }
//
//    public String getTodo(Request request) {
//
//        if(request.userId == null) {
//            JSONOBjetc json = new JSONPObject();
//            json.put("error", "User not found");
//            return json.toString();
//        }
//    }
//
//    List<Todo> todos = new ArrayList<>();
//
//    //DB콜
//    String sqlSelectAllPersons =
//            "SELECT * FROM todo WHERE user_id = "
//            + request.getUserId();
//    String connectionUrl = "jdbc:sqlserver://localhost:3306/todo";
//
//    try( Connection conn = DriverManager.getConnection(connectionUrl, "username", "p@ssw0rd");
//        PreparedStatement ps = conn.prepareStatement(sqlSelectAllPersons);
//        ResultSet rs = ps.excuteQuery())
//
//    {
//
//        while (rs.next()) {
//            long id = rs.getLong("id");
//            String title = rs.getString("title");
//            Boolean isDone = rs.getBoolean("is_done");
//
//            todos.add(new Todo(id, title, isDone));
//
//        }
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//
//    //응답생성
//    JSONOBjetc json = new JSONPObject();
//    JSONArray arra = new JSONArray();
//
//    for(Todo todo : todos) {
//        JSONObject todoJson = new JSONObject();
//        jsonObj.put("id", todo.getId());
//        jsonObj.put("title", todo.getTitle());
//        obj.put("isDone", todo.getIsDone());
//        array.add(obj);
//    }
//    json.put("data", array);
//    return json.toString();
//}
