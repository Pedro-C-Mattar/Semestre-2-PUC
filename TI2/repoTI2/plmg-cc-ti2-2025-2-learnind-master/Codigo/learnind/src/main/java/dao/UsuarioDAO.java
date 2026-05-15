package dao;

import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAO {
    public UsuarioDAO() {
        super();
        boolean connected = conectar();
        if (!connected) {
            throw new RuntimeException("Não foi possível conectar ao banco de dados (UsuarioDAO). Verifique as configurações em DAO.conectar().");
        }
    }

    public void finalize() {
        close();
    }

    public boolean insert(Usuario usuario) {
        boolean status = false;
        try {
            String sql = "INSERT INTO usuario (id_usuario, nome, email, senha, sexo, data_nascimento) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, usuario.getIdUsuario());
            st.setString(2, usuario.getNome());
            st.setString(3, usuario.getEmail());
            st.setString(4, usuario.getSenha());
            st.setString(5, String.valueOf(usuario.getSexo()));
            st.setDate(6, Date.valueOf(usuario.getDataNascimento()));
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public Usuario get(int id) {
        Usuario usuario = null;
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM usuario WHERE id_usuario = " + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getString("sexo").charAt(0),
                    rs.getDate("data_nascimento").toLocalDate()
                );
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return usuario;
    }

    public List<Usuario> get() {
        return get("");
    }

    public List<Usuario> getOrderByID() {
        return get("id_usuario");
    }

    public List<Usuario> getOrderByNome() {
        return get("nome");
    }

    private List<Usuario> get(String orderBy) {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM usuario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getString("sexo").charAt(0),
                    rs.getDate("data_nascimento").toLocalDate()
                );
                usuarios.add(u);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return usuarios;
    }

    public boolean update(Usuario usuario) {
        boolean status = false;
        try {
            String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, sexo = ?, data_nascimento = ? WHERE id_usuario = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, usuario.getNome());
            st.setString(2, usuario.getEmail());
            st.setString(3, usuario.getSenha());
            st.setString(4, String.valueOf(usuario.getSexo()));
            st.setDate(5, Date.valueOf(usuario.getDataNascimento()));
            st.setInt(6, usuario.getIdUsuario());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public Usuario getByEmail(String email) {
        Usuario usuario = null;
        try {
            String sql = "SELECT * FROM usuario WHERE email = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getString("sexo").charAt(0),
                    rs.getDate("data_nascimento").toLocalDate()
                );
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return usuario;
    }

    public int getNextId() {
        int next = 1;
        try {
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id_usuario) AS max_id FROM usuario");
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

    public boolean delete(int id) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM usuario WHERE id_usuario = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
}
