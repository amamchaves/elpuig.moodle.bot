package elpuig.moodle.bot.utils;


import elpuig.moodle.bot.model.Usuario;

import java.sql.*;

public class Database {
    static final String url = "jdbc:sqlite:database.db";
    static final int DATABASE_VERSION = 7;

    static Database instance;
    static Connection conn;

    public static Database get(){
        if(instance == null){
            instance = new Database();

            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println("Connection FAILEDDDDDDD");
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
            stmt.execute("CREATE TABLE IF NOT EXISTS usuarios (telegramName text, token text, email text, id text);");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertUsuario(String nombre, String token, String email, String id) {
        String sql = "INSERT INTO usuarios(telegramName, token, email, id) VALUES(?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, token);
            pstmt.setString(3, email);
            pstmt.setString(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Usuario selectUsuarioPorTelegramId(String telegramName){
        System.out.println("USDERNQAMEEE " + telegramName);
        String sql = "SELECT * FROM usuarios WHERE telegramName = ?";

        Usuario usuario = new Usuario();

        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setString(1, telegramName);
            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                usuario.telegramUser = telegramName;
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
