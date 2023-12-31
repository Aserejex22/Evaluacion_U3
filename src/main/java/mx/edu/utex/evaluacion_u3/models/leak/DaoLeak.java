package mx.edu.utex.evaluacion_u3.models.leak;

import mx.edu.utex.evaluacion_u3.models.crud.DaoRepository;
import mx.edu.utex.evaluacion_u3.utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoLeak implements DaoRepository<Leak> {
    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;


    @Override
    public List<Leak> findAll() {
        List<Leak> leaks = new ArrayList<>();
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM leaks;";
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Leak leak = new Leak();
                leak.setId(rs.getLong("id"));
                leak.setName(rs.getString("name"));
                leak.setTitle(rs.getString("title"));
                leak.setDescription(rs.getString("description"));
                leak.setStatus(rs.getString("status"));
                leak.setTipo(rs.getString("tipo"));
                leaks.add(leak);
                System.out.println(leak);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoLeak.class.getName())
                    .log(Level.SEVERE, "Error findAll "
                            + e.getMessage());
        } finally {
            close();
        }
        return leaks;
    }

    @Override
    public List<Leak> findAllAdmin() {
        List<Leak> leaks = new ArrayList<>();
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM leaks where status= 'ACEPTADO';";
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Leak leak = new Leak();
                leak.setId(rs.getLong("id"));
                leak.setName(rs.getString("name"));
                leak.setTitle(rs.getString("title"));
                leak.setDescription(rs.getString("description"));
                leak.setStatus(rs.getString("status"));
                leak.setTipo(rs.getString("tipo"));
                leaks.add(leak);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoLeak.class.getName())
                    .log(Level.SEVERE, "Error findAll "
                            + e.getMessage());
        } finally {
            close();
        }
        return leaks;
    }

    @Override
    public Leak findOne(Long id) {
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM leaks WHERE id = ?;";
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            Leak leak = new Leak();
            if (rs.next()) {
                leak.setId(rs.getLong("id"));
                leak.setName(rs.getString("name"));
                leak.setTitle(rs.getString("title"));
                leak.setDescription(rs.getString("description"));
                leak.setStatus(rs.getString("status"));
            }
            return leak;
        } catch (SQLException e) {
            Logger.getLogger(DaoLeak.class.getName()).log(Level.SEVERE, "Error findOne " + e.getMessage());
        } finally {
            close();
        }
        return null;
    }

    @Override
    public boolean save(Leak object) {
        try {
            conn = new MySQLConnection().connect();
            String query = "INSERT INTO leaks (name, title, description, status, tipo)" +
                    " VALUES (?, ?, ?, ?, ?);";
            pstm = conn.prepareStatement(query);
            pstm.setString(1, object.getName());
            pstm.setString(2, object.getTitle());
            pstm.setString(3, object.getDescription());
            pstm.setString(4, object.getStatus());
            pstm.setString(5,object.getTipo());
            return pstm.executeUpdate() > 0; // == 1
        } catch (SQLException e) {
            Logger.getLogger(DaoLeak.class.getName())
                    .log(Level.SEVERE, "Error save " + e.getMessage());
        } finally {
            close();
        }
        return false;
    }

    @Override
    public boolean update(Leak object) {

        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    public void close() {
        try {
            if (conn != null) conn.close();
            if (pstm != null) pstm.close();
            if (rs != null) rs.close();
        } catch (SQLException e) {

        }
    }

    public boolean decline(Long id) {
        try {
            conn = new MySQLConnection().connect();
            String query = "update leaks set status = 'DECLINADO' WHERE id = ?;";
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, id);
            return pstm.executeUpdate() == 1;
        }catch (SQLException e) {
                Logger.getLogger(DaoLeak.class.getName())
                        .log(Level.SEVERE, "Error delete " + e.getMessage());
            } finally {
                close();
            }
            return false;
    }


    public boolean accept(Long id) {
        try {
            conn = new MySQLConnection().connect();
            String query = "update leaks set status = 'ACEPTADO' WHERE id = ?;";
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, id);
            return pstm.executeUpdate() == 1;
        }catch (SQLException e) {
            Logger.getLogger(DaoLeak.class.getName())
                    .log(Level.SEVERE, "Error delete " + e.getMessage());
        } finally {
            close();
        }
        return false;
    }

    public boolean aprove(Long id) {
        try {
            conn = new MySQLConnection().connect();
            String query = "update leaks set status = 'APROVADO' WHERE id = ?;";
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, id);
            return pstm.executeUpdate() == 1;
        }catch (SQLException e) {
            Logger.getLogger(DaoLeak.class.getName())
                    .log(Level.SEVERE, "Error delete " + e.getMessage());
        } finally {
            close();
        }
        return false;
    }
}
