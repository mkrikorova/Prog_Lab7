package database.postgres;

import models.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Statement;

public class UserManager implements managers.UserManager {
    @Override
    public int addUser(String login, String password) {
        var hash = encryptPassword(password);

        var insert_user_sql = "INSERT INTO users(login, password_hash) VALUES (?, ?)";

        try (var conn = Database.connect();
             var pstmt = conn.prepareStatement(insert_user_sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, login);
            pstmt.setString(2, hash);

            int insertedRow = pstmt.executeUpdate();
            if (insertedRow > 0) {
                var rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new RuntimeException("Cannot insert user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(int user_id) {
        var insert_user_sql = "SELECT * FROM users WHERE user_id = ?";

        try (var conn = Database.connect();
             var pstmt = conn.prepareStatement(insert_user_sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, user_id);

            var rs = pstmt.executeQuery();
            if (rs.next()) {
                var login = rs.getString(2);
                var password_hash = rs.getString(3);
                return new User(user_id, login, password_hash);
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(String login, String password) {
        password = password == null ? "" : password;
        login = login == null ? "" : login;

        var hash = encryptPassword(password);

        var insert_user_sql = "SELECT * FROM users WHERE login = ? AND password_hash = ?";

        try (var conn = Database.connect();
             var pstmt = conn.prepareStatement(insert_user_sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, login);
            pstmt.setString(2, hash);

            var rs = pstmt.executeQuery();
            if (rs.next()) {
                var user_id = rs.getInt(1);
                return new User(user_id, login, hash);
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");

            byte[] messageDigest = md.digest(password.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hash = new StringBuilder(no.toString(16));

            while (hash.length() < 32) {
                hash.insert(0, "0");
            }

            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
