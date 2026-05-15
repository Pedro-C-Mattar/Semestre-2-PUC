package dao;

import model.Favorito;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoritoDAO extends DAO {
    public FavoritoDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(Favorito favorito) {
        boolean status = false;
        try {
            String sql = "INSERT INTO favorito (id_favorito, data_favoritado, id_usuario, id_video) VALUES (?, ?, ?, ?);";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, favorito.getIdFavorito());
            st.setTimestamp(2, Timestamp.valueOf(favorito.getDataFavoritado()));
            st.setInt(3, favorito.getIdUsuario());
            st.setInt(4, favorito.getIdVideo());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public Favorito get(int id) {
        Favorito favorito = null;
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM favorito WHERE id_favorito = " + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                favorito = new Favorito(
                    rs.getInt("id_favorito"),
                    rs.getTimestamp("data_favoritado").toLocalDateTime(),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_video")
                );
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return favorito;
    }

    public List<Favorito> get() {
        return get("");
    }

    public List<Favorito> getOrderByID() {
        return get("id_favorito");
    }

    private List<Favorito> get(String orderBy) {
        List<Favorito> favoritos = new ArrayList<>();
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM favorito" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Favorito f = new Favorito(
                    rs.getInt("id_favorito"),
                    rs.getTimestamp("data_favoritado").toLocalDateTime(),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_video")
                );
                favoritos.add(f);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return favoritos;
    }

    public boolean update(Favorito favorito) {
        boolean status = false;
        try {
            String sql = "UPDATE favorito SET data_favoritado = ?, id_usuario = ?, id_video = ? WHERE id_favorito = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setTimestamp(1, Timestamp.valueOf(favorito.getDataFavoritado()));
            st.setInt(2, favorito.getIdUsuario());
            st.setInt(3, favorito.getIdVideo());
            st.setInt(4, favorito.getIdFavorito());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean delete(int id) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM favorito WHERE id_favorito = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public int getNextId() {
        int next = 1;
        try {
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id_favorito) AS max_id FROM favorito");
            if (rs.next()) {
                int max = rs.getInt("max_id");
                next = max + 1;
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return next;
    }

    public Favorito getByUserAndVideo(int userId, int videoId) {
        Favorito f = null;
        try {
            String sql = "SELECT * FROM favorito WHERE id_usuario = ? AND id_video = ? LIMIT 1";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, videoId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                f = new Favorito(
                    rs.getInt("id_favorito"),
                    rs.getTimestamp("data_favoritado").toLocalDateTime(),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_video")
                );
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return f;
    }

    public List<Favorito> getByUser(int userId) {
        List<Favorito> favoritos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM favorito WHERE id_usuario = ? ORDER BY data_favoritado DESC";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Favorito f = new Favorito(
                    rs.getInt("id_favorito"),
                    rs.getTimestamp("data_favoritado").toLocalDateTime(),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_video")
                );
                favoritos.add(f);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return favoritos;
    }
}
