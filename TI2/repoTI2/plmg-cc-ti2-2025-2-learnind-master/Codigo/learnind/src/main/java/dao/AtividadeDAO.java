package dao;

import model.Atividade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtividadeDAO extends DAO {
    public AtividadeDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(Atividade atividade) {
        boolean status = false;
        try {
            String sql = "INSERT INTO atividade (id_atividade, conteudo) VALUES (?, ?);";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, atividade.getIdAtividade());
            st.setString(2, atividade.getConteudo());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public Atividade get(int id) {
        Atividade atividade = null;
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM atividade WHERE id_atividade = " + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                atividade = new Atividade(rs.getInt("id_atividade"), rs.getString("conteudo"));
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return atividade;
    }

    public List<Atividade> get() {
        return get("");
    }

    public List<Atividade> getOrderByID() {
        return get("id_atividade");
    }

    private List<Atividade> get(String orderBy) {
        List<Atividade> atividades = new ArrayList<>();
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM atividade" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Atividade a = new Atividade(rs.getInt("id_atividade"), rs.getString("conteudo"));
                atividades.add(a);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return atividades;
    }

    public boolean update(Atividade atividade) {
        boolean status = false;
        try {
            String sql = "UPDATE atividade SET conteudo = ? WHERE id_atividade = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, atividade.getConteudo());
            st.setInt(2, atividade.getIdAtividade());
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
            st.executeUpdate("DELETE FROM atividade WHERE id_atividade = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
}
