package dao;

import model.Feedback;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO extends DAO {
    
    public FeedbackDAO() {
        super();
        boolean connected = conectar();
        if (!connected) {
            throw new RuntimeException("Não foi possível conectar ao banco de dados (FeedbackDAO). Verifique as configurações em DAO.conectar().");
        }
    }

    public void finalize() {
        close();
    }

    // Inserir feedback
    public boolean insert(Feedback feedback) {
        boolean status = false;
        try {
            String sql = "INSERT INTO feedback (id_feedback, id_video, conteudo, data_feedback) VALUES (?, ?, ?, ?);";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, feedback.getIdFeedback());
            st.setInt(2, feedback.getIdVideo());
            st.setString(3, feedback.getConteudo());
            st.setTimestamp(4, Timestamp.valueOf(feedback.getDataFeedback()));
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir feedback: " + e.getMessage());
        }
        return status;
    }

    // Buscar um feedback pelo ID
    public Feedback get(int id) {
        Feedback feedback = null;
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM feedback WHERE id_feedback = " + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                feedback = new Feedback(
                    rs.getInt("id_feedback"),
                    rs.getInt("id_video"),
                    rs.getString("conteudo"),
                    rs.getTimestamp("data_feedback").toLocalDateTime()
                );
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Erro ao buscar feedback: " + e.getMessage());
        }
        return feedback;
    }

    // Buscar todos os feedbacks
    public List<Feedback> get() {
        return get("");
    }

    // Buscar feedbacks ordenados por ID
    public List<Feedback> getOrderById() {
        return get("id_feedback");
    }

    // Buscar feedbacks ordenados por data
    public List<Feedback> getOrderByData() {
        return get("data_feedback DESC");
    }

    // Método interno de listagem
    private List<Feedback> get(String orderBy) {
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM feedback" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Feedback f = new Feedback(
                    rs.getInt("id_feedback"),
                    rs.getInt("id_video"),
                    rs.getString("conteudo"),
                    rs.getTimestamp("data_feedback").toLocalDateTime()
                );
                feedbacks.add(f);
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Erro ao listar feedbacks: " + e.getMessage());
        }
        return feedbacks;
    }

    // Atualizar feedback
    public boolean update(Feedback feedback) {
        boolean status = false;
        try {
            String sql = "UPDATE feedback SET id_video = ?, conteudo = ?, data_feedback = ? WHERE id_feedback = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, feedback.getIdVideo());
            st.setString(2, feedback.getConteudo());
            st.setTimestamp(3, Timestamp.valueOf(feedback.getDataFeedback()));
            st.setInt(4, feedback.getIdFeedback());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar feedback: " + e.getMessage());
        }
        return status;
    }

    // Excluir feedback
    public boolean delete(int id) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM feedback WHERE id_feedback = " + id);
            st.close();
            status = true;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar feedback: " + e.getMessage());
        }
        return status;
    }

    // Pegar próximo ID
    public int getNextId() {
        int next = 1;
        try {
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id_feedback) AS max_id FROM feedback");
            if (rs.next()) {
                int max = rs.getInt("max_id");
                next = max + 1;
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Erro ao obter próximo ID de feedback: " + e.getMessage());
        }
        return next;
    }

    // Buscar feedbacks de um vídeo específico
    public List<Feedback> getByVideo(int idVideo) {
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            String sql = "SELECT * FROM feedback WHERE id_video = ? ORDER BY data_feedback DESC";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, idVideo);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Feedback f = new Feedback(
                    rs.getInt("id_feedback"),
                    rs.getInt("id_video"),
                    rs.getString("conteudo"),
                    rs.getTimestamp("data_feedback").toLocalDateTime()
                );
                feedbacks.add(f);
            }
            st.close();
        } catch (SQLException e) {
            System.err.println("Erro ao buscar feedbacks por vídeo: " + e.getMessage());
        }
        return feedbacks;
    }
}
