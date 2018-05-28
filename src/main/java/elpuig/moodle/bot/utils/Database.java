package elpuig.moodle.bot.utils;


import elpuig.moodle.bot.model.Usuario;

import java.sql.*;

public class Database {
    static final String url = "jdbc:sqlite:database.db";
    static final int DATABASE_VERSION = 11;

    static Database instance;
    static Connection conn;

    public static Database get(){
        if(instance == null){
            instance = new Database();

            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println("Connection FAILED");
                System.out.println(e.getMessage());
            }

            if(instance.getVersion() != DATABASE_VERSION){
                System.out.println("UPGRADING DATABASE VERSION = " + instance.getVersion());
                instance.upgradeDatabase();
                instance.setVersion();
            }
        }
        return instance;
    }

    public int getVersion(){
        try (Statement stmt  = conn.createStatement()){
            ResultSet rs  = stmt.executeQuery("PRAGMA user_version");
            while (rs.next()) {
                return rs.getInt("user_version");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public void setVersion(){
        try (Statement stmt  = conn.createStatement()){
            stmt.execute("PRAGMA user_version = " + DATABASE_VERSION);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void upgradeDatabase(){
        deleteTables();
        createTables();
    }

    void deleteTables(){
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS usuarios;");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void createTables(){
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS usuarios (telegramId text, username text, token text, email text, id text);");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertUsuario(int telegramId, String username, String token, String email, String id) {
        String sql = "INSERT INTO usuarios(telegramId, username, token, email, id) VALUES(?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, telegramId);
            pstmt.setString(2, username);
            pstmt.setString(3, token);
            pstmt.setString(4, email);
            pstmt.setString(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUsuario(String username) {
        String sql = "DELETE FROM usuarios WHERE username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //pstmt.setString(1, username);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Usuario selectUsuarioPorTelegramId(int telegramId){
        String sql = "SELECT * FROM usuarios WHERE telegramId = ?";

        Usuario usuario = new Usuario();

        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setInt(1, telegramId);
            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                usuario.telegramId = telegramId;
                usuario.token = rs.getString("token");
                usuario.email = rs.getString("email");
                usuario.id = rs.getString("id");
                usuario.username = rs.getString("username");

                System.out.println("RESULTADO CONSULTA " + usuario.username);
                return usuario;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
