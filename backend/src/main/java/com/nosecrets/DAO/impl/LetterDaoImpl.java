package com.nosecrets.dao.impl;

import com.nosecrets.config.Database;
import com.nosecrets.dao.LetterDao;
import com.nosecrets.model.Letter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LetterDaoImpl implements LetterDao {

    private static final String COLUMNAS =
            "id, stat, message, reply, sender, replier, sent_at, replied_at";

    private final Database db;

    public LetterDaoImpl(Database db){
        this.db = db;
    }

    @Override
    public int guardar(Letter l) {
        String sql = "INSERT INTO letter (message, sender) VALUES (?, ?)";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, l.getMessage());
            ps.setInt(2, l.getSender());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                throw new RuntimeException("No se generó el id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar carta", e);
        }
    }

    @Override
    public Optional<Letter> buscarPorId(int id) {
        String sql = "SELECT " + COLUMNAS + " FROM letter WHERE id = ?";

        try(Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id);

            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(mapear(rs));
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Letter> listarPorSender(int senderId) {
        String sql = "SELECT " + COLUMNAS + " FROM letter WHERE sender = ? ORDER BY sent_at DESC";
        List<Letter> list = new ArrayList<>();

        try(Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,senderId);

            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    list.add(mapear(rs));
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Letter> listarSinResponder() {
        String sql = "SELECT " + COLUMNAS + " FROM letter WHERE stat = FALSE ORDER BY sent_at ASC";
        List<Letter> list = new ArrayList<>();

        try(Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                list.add(mapear(rs));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void responder(int id, String reply, int replierId) {
        String sql = "UPDATE letter SET stat = TRUE, reply = ?, replier = ?, replied_at = CURRENT_TIMESTAMP "
                   + "WHERE id = ? AND stat = FALSE";

        try(Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,reply);
            ps.setInt(2,replierId);
            ps.setInt(3,id);
            int filas = ps.executeUpdate();

            if(filas != 1) throw new IllegalArgumentException("No existe una carta con ese ID o ya fue respondida");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Convierte la fila actual del ResultSet en un Letter
    private Letter mapear(ResultSet rs) throws SQLException {
        Letter l = new Letter();
        l.setId(rs.getInt("id"));
        l.setStat(rs.getBoolean("stat"));
        l.setMessage(rs.getString("message"));
        l.setReply(rs.getString("reply"));
        l.setSender(rs.getInt("sender"));
        l.setReplier(rs.getObject("replier", Integer.class));
        l.setSentAt(rs.getTimestamp("sent_at").toLocalDateTime());

        Timestamp respondida = rs.getTimestamp("replied_at");
        l.setRepliedAt(respondida == null ? null : respondida.toLocalDateTime());
        return l;
    }
}
