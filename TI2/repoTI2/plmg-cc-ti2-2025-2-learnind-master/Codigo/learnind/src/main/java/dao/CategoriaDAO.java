package dao;

import model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO extends DAO {
    public CategoriaDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(Categoria categoria) {
        boolean status = false;
        try {
            String sql = "INSERT INTO categoria (id_categoria, nome) VALUES (?, ?);";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, categoria.getIdCategoria());
            st.setString(2, categoria.getNome());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public Categoria get(int id) {
        Categoria categoria = null;
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM categoria WHERE id_categoria = " + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                categoria = new Categoria(rs.getInt("id_categoria"), rs.getString("nome"));
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return categoria;
    }

    public List<Categoria> get() {
        return get("");
    }

    public List<Categoria> getOrderByID() {
        return get("id_categoria");
    }

    public List<Categoria> getOrderByNome() {
        return get("nome");
    }

    private List<Categoria> get(String orderBy) {
        List<Categoria> categorias = new ArrayList<>();
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM categoria" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Categoria c = new Categoria(rs.getInt("id_categoria"), rs.getString("nome"));
                categorias.add(c);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return categorias;
    }

    public boolean update(Categoria categoria) {
        boolean status = false;
        try {
            String sql = "UPDATE categoria SET nome = ? WHERE id_categoria = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, categoria.getNome());
            st.setInt(2, categoria.getIdCategoria());
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
            st.executeUpdate("DELETE FROM categoria WHERE id_categoria = " + id);
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
            ResultSet rs = st.executeQuery("SELECT MAX(id_categoria) AS max_id FROM categoria");
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
}
