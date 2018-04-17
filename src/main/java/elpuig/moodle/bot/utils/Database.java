package elpuig.moodle.bot.utils;


import elpuig.moodle.bot.Usuario;

import java.sql.*;

public class Database {
    static final String url = "jdbc:sqlite:database.db";
    static final int DATABASE_VERSION = 5;

    static Database instance;
    static Connection conn;

    public static Database get(){
        if(instance == null){
            instance = new Database();

            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
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
            stmt.execute("CREATE TABLE IF NOT EXISTS usuarios (telegramName text, token text);");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertUsuario(String nombre, String token) {
        String sql = "INSERT INTO usuarios(nombre, token) VALUES(?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, token);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Usuario selectUsuarioPorTelegramName(String telegramName){
        String sql = "SELECT * FROM usuarios WHERE telegramName = ?";

        Usuario usuario = new Usuario();

        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setString(1, telegramName);
            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                usuario.telegramUser = telegramName;
                usuario.token = rs.getString("token");

                return usuario;

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
