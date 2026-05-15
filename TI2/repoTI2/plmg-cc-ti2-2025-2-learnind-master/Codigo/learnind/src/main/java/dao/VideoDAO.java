package dao;

import model.Video;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoDAO extends DAO {
    public VideoDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(Video video) {
        boolean status = false;
        try {
            String sql = "INSERT INTO video (id_video, titulo, descricao, url, id_categoria, id_atividade, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, video.getIdVideo());
            st.setString(2, video.getTitulo());
            st.setString(3, video.getDescricao());
            st.setString(4, video.getUrl());
            if (video.getIdCategoria() == null || video.getIdCategoria() <= 0) st.setNull(5, java.sql.Types.INTEGER); else st.setInt(5, video.getIdCategoria());
            if (video.getIdAtividade() == null || video.getIdAtividade() <= 0) st.setNull(6, java.sql.Types.INTEGER); else st.setInt(6, video.getIdAtividade());
            st.setInt(7, video.getIdUsuario());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public Video get(int id) {
        Video video = null;
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM video WHERE id_video = " + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                video = new Video(
                    rs.getInt("id_video"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("url"),
                    (rs.getObject("id_categoria") != null ? rs.getInt("id_categoria") : null),
                    (rs.getObject("id_atividade") != null ? rs.getInt("id_atividade") : null),
                    (rs.getObject("id_usuario") != null ? rs.getInt("id_usuario") : null)
                );
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return video;
    }

    public List<Video> get() {
        return get("");
    }

    public List<Video> getOrderByID() {
        return get("id_video");
    }

    public List<Video> getOrderByTitulo() {
        return get("titulo");
    }

    private List<Video> get(String orderBy) {
        List<Video> videos = new ArrayList<>();
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM video" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Video v = new Video(
                    rs.getInt("id_video"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("url"),
                    (rs.getObject("id_categoria") != null ? rs.getInt("id_categoria") : null),
                    (rs.getObject("id_atividade") != null ? rs.getInt("id_atividade") : null),
                    (rs.getObject("id_usuario") != null ? rs.getInt("id_usuario") : null)
                );
                videos.add(v);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return videos;
    }

    public boolean update(Video video) {
        boolean status = false;
        try {
            String sql = "UPDATE video SET titulo = ?, descricao = ?, url = ?, id_categoria = ?, id_atividade = ?, id_usuario = ? WHERE id_video = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, video.getTitulo());
            st.setString(2, video.getDescricao());
            st.setString(3, video.getUrl());
            if (video.getIdCategoria() == null || video.getIdCategoria() <= 0) st.setNull(4, java.sql.Types.INTEGER); else st.setInt(4, video.getIdCategoria());
            if (video.getIdAtividade() == null || video.getIdAtividade() <= 0) st.setNull(5, java.sql.Types.INTEGER); else st.setInt(5, video.getIdAtividade());
            st.setInt(6, video.getIdUsuario());
            st.setInt(7, video.getIdVideo());
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
            // remove favoritos que referenciam o vídeo antes de apagar o vídeo para evitar violação de FK
            st.executeUpdate("DELETE FROM favorito WHERE id_video = " + id);
            // remove feedbacks relacionados ao vídeo
            st.executeUpdate("DELETE FROM feedback WHERE id_video = " + id);
            st.executeUpdate("DELETE FROM video WHERE id_video = " + id);
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
            ResultSet rs = st.executeQuery("SELECT MAX(id_video) AS max_id FROM video");
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

    public List<Video> findByTituloLike(String q) {
        List<Video> videos = new ArrayList<>();
        try {
            // search both in title and description for better precision
            String sql = "SELECT * FROM video WHERE titulo ILIKE ? OR descricao ILIKE ? ORDER BY id_video";
            PreparedStatement st = conexao.prepareStatement(sql);
            String pattern = "%" + q + "%";
            st.setString(1, pattern);
            st.setString(2, pattern);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Video v = new Video(
                    rs.getInt("id_video"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("url"),
                    (rs.getObject("id_categoria") != null ? rs.getInt("id_categoria") : null),
                    (rs.getObject("id_atividade") != null ? rs.getInt("id_atividade") : null),
                    (rs.getObject("id_usuario") != null ? rs.getInt("id_usuario") : null)
                );
                videos.add(v);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return videos;
    }

    public List<Video> findByUsuarioId(int usuarioId) {
        List<Video> videos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM video WHERE id_usuario = ? ORDER BY id_video";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, usuarioId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Video v = new Video(
                    rs.getInt("id_video"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("url"),
                    (rs.getObject("id_categoria") != null ? rs.getInt("id_categoria") : null),
                    (rs.getObject("id_atividade") != null ? rs.getInt("id_atividade") : null),
                    (rs.getObject("id_usuario") != null ? rs.getInt("id_usuario") : null)
                );
                videos.add(v);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return videos;
    }

    public List<Video> findByCategoriaId(int categoriaId) {
        List<Video> videos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM video WHERE id_categoria = ? ORDER BY id_video";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, categoriaId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Video v = new Video(
                    rs.getInt("id_video"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("url"),
                    (rs.getObject("id_categoria") != null ? rs.getInt("id_categoria") : null),
                    (rs.getObject("id_atividade") != null ? rs.getInt("id_atividade") : null),
                    (rs.getObject("id_usuario") != null ? rs.getInt("id_usuario") : null)
                );
                videos.add(v);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return videos;
    }

    /**
     * Set id_categoria to NULL for all videos that reference the given categoriaId.
     * Returns true if the update executed without throwing an exception.
     */
    public boolean nullifyCategoria(int categoriaId) {
        boolean status = false;
        try {
            String sql = "UPDATE video SET id_categoria = NULL WHERE id_categoria = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, categoriaId);
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }
}
