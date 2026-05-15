package dao;

import model.Funcionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class FuncionarioDAO extends DAO {	
	public FuncionarioDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Funcionario funcionario) {
		boolean status = false;
		try {
			String sql = "INSERT INTO funcionario (nome, idade, funcao, sexo) "
		               + "VALUES (?, ?, ?, ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setString(1, funcionario.getNome());
			st.setInt(2, funcionario.getIdade());
			st.setString(3, funcionario.getFuncao());
			st.setString(4, String.valueOf(funcionario.getSexo())); // se for char
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Funcionario get(int id) {
		Funcionario funcionario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM funcionario WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	funcionario = new Funcionario(
	        		rs.getInt("id"), 
	        		rs.getString("nome"), 
	        		rs.getInt("idade"), 
	        		rs.getString("funcao"), 
	        		rs.getString("sexo").charAt(0) // assumindo char
	        	);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return funcionario;
	}
	
	
	public List<Funcionario> get() {
		return get("");
	}

	
	public List<Funcionario> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Funcionario> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Funcionario> getOrderByIdade() {
		return get("idade");		
	}
	
	
	private List<Funcionario> get(String orderBy) {
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM funcionario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Funcionario f = new Funcionario(
	        		rs.getInt("id"), 
	        		rs.getString("nome"), 
	        		rs.getInt("idade"), 
	        		rs.getString("funcao"), 
	        		rs.getString("sexo").charAt(0)
	        	);
	            funcionarios.add(f);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return funcionarios;
	}
	
	
	public boolean update(Funcionario funcionario) {
		boolean status = false;
		try {  
			String sql = "UPDATE funcionario SET nome = ?, idade = ?, funcao = ?, sexo = ? WHERE id = ?";
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setString(1, funcionario.getNome());
			st.setInt(2, funcionario.getIdade());
			st.setString(3, funcionario.getFuncao());
			st.setString(4, String.valueOf(funcionario.getSexo()));
			st.setInt(5, funcionario.getId());
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
			st.executeUpdate("DELETE FROM funcionario WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}
