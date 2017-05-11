package team05.chatserver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public Connection connection;
 // oke mem-be mar megy ezen nem ugy mysqlbe,amihez nincs leiras, de hogy irjuk at? 
    public void connect() {
        try {
            Class.forName("org.h2.Driver");
            System.out.println("Connecting to database ...");
            connection = DriverManager.getConnection("jdbc:h2:mem:~/test", "sa", "");
            initDatabase();
            System.out.println("Database connected!");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
 
    }

    private void initDatabase() throws Exception {
        String createQuery = "CREATE TABLE IF NOT EXISTS MESSAGE(message varchar(255), ID varchar(255))";

        connection.setAutoCommit(false);

        PreparedStatement createPreparedStatement;
        createPreparedStatement = connection.prepareStatement(createQuery);
        createPreparedStatement.executeUpdate();
        createPreparedStatement.close();

        connection.commit();

    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    //mi lenne ha objektum lenne az uzenet? string olyan snasz..
    public void saveMessage(String message, int ID) throws Exception {
        String InsertQuery = "INSERT INTO MESSAGE" + "(message, ID) values" + "(?,?)";
        PreparedStatement insertPreparedStatement;

        insertPreparedStatement = connection.prepareStatement(InsertQuery);
        insertPreparedStatement.setString(1, message);
        insertPreparedStatement.setString(2, String.valueOf(ID));
        insertPreparedStatement.executeUpdate();
        insertPreparedStatement.close();

        connection.commit();
    }

    public List<Message> getMessages() throws Exception {
        PreparedStatement selectPreparedStatement = connection.prepareStatement("SELECT * FROM MESSAGE");
        ResultSet rs = selectPreparedStatement.executeQuery();
        System.out.println("H2 Database inserted through PreparedStatement");

        List<Message> messages = new ArrayList<Message>();

        while (rs.next()) {
            messages.add(new Message(rs.getString("message"), rs.getString("ID")));
        }

        selectPreparedStatement.close();

        return messages;
    }

    //kezeljuk kulon filekent, de csak otlet.
    public static class Message {
        private String message;
        private String ID;

        public Message(String message, String ID) {
            this.message = message;
            this.ID = ID;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }
    }

}
