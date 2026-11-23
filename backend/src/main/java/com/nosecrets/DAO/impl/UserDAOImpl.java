package com.nosecrets.dao.impl;

import com.nosecrets.config.Database;
import com.nosecrets.dao.UserDAO;
import com.nosecrets.model.User;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private final Database db;

    public UserDAOImpl(Database db){
        this.db = db;
    }

    @Override
    public int guardar(User u) {
        String sql = "INSERT INTO user (user, password) VALUES (?, ?)";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getUser());
            ps.setString(2, u.getPassword());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                throw new RuntimeException("No se generó el id", null);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar usuario", e);
        }
    }


    @Override
    public Optional<User> buscarPorId(int id) {

        String sql = "SELECT * FROM user WHERE id = ?";

        try(Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id);

            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setUser(rs.getString("user"));
                    u.setPassword(rs.getString("password"));
                    u.setStat(rs.getString("stat"));
                    return Optional.of(u);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> buscarPorUser(String user) {
        String sql = "SELECT * FROM user WHERE user = ?";

        try(Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,user);

            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setUser(rs.getString("user"));
                    u.setPassword(rs.getString("password"));
                    u.setStat(rs.getString("stat"));
                    return Optional.of(u);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> listarTodos() {

        String sql = "SELECT * FROM user";
        List<User> list = new ArrayList<>();

        try(Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){


            while(rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUser(rs.getString("user"));
                u.setPassword(rs.getString("password"));
                u.setStat(rs.getString("stat"));
                list.add(u);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }


        return list;
    }

    @Override
    public void actualizar(User u) {
        String sql = "UPDATE user SET user = ?, password = ? WHERE id = ? ";

        try(Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,u.getUser());
            ps.setString(2,u.getPassword());
            ps.setInt(3,u.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void cambiarEstado(int id, String stat) {
        String sql = "UPDATE user SET stat = ? WHERE id = ?";
        try(Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,stat);
            ps.setInt(2,id);
            int state = ps.executeUpdate();

            if(state != 1) throw new IllegalArgumentException("No existe usuario con ese ID ");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean existePorUser(String user) {
        String sql = "SELECT 1 FROM user WHERE user = ? LIMIT 1";

        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, user);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
