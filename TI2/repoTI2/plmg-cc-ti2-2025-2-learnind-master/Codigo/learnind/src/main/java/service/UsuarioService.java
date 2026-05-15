package service;

import dao.UsuarioDAO;
import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioService {
	private UsuarioDAO usuarioDAO;

	public UsuarioService() {
		usuarioDAO = new UsuarioDAO();
	}

	public boolean register(Usuario usuario) {
		// assign id if not set
		if (usuario.getIdUsuario() <= 0) {
			usuario.setIdUsuario(usuarioDAO.getNextId());
		}
		// hash password if not already hashed
		if (usuario.getSenha() != null && !isBCryptHash(usuario.getSenha())) {
			String hashed = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
			usuario.setSenha(hashed);
		}
		return usuarioDAO.insert(usuario);
	}

	public Usuario authenticate(String email, String senha) {
		Usuario u = usuarioDAO.getByEmail(email);
		if (u == null) {
			System.out.println("authenticate: usuário não encontrado para email=" + email);
			return null;
		}
		System.out.println("authenticate: encontrado id=" + u.getIdUsuario() + " nome=" + u.getNome());
		String stored = u.getSenha();
		if (stored == null) {
			System.out.println("authenticate: senha não definida para email=" + email);
			return null;
		}
		try {
			if (isBCryptHash(stored)) {
				if (BCrypt.checkpw(senha, stored)) {
					System.out.println("authenticate: senha batida para email=" + email);
					return u;
				} else {
					System.out.println("authenticate: senha inválida para email=" + email);
					return null;
				}
			} else {
				// legacy plaintext password: compare directly and migrate to BCrypt
				if (stored.equals(senha)) {
					System.out.println("authenticate: senha em texto puro validada; migrando para BCrypt para email=" + email);
					String hashed = BCrypt.hashpw(senha, BCrypt.gensalt());
					u.setSenha(hashed);
					try {
						usuarioDAO.update(u);
					} catch (Exception e) {
						System.err.println("Falha ao migrar senha para BCrypt: " + e.getMessage());
					}
					return u;
				} else {
					System.out.println("authenticate: senha inválida para email=" + email);
					return null;
				}
			}
		} catch (IllegalArgumentException ex) {
			System.err.println("Erro ao verificar senha BCrypt: " + ex.getMessage());
			return null;
		}
	}

	public Usuario getById(int id) {
		return usuarioDAO.get(id);
	}

	public Usuario getByEmail(String email) {
		return usuarioDAO.getByEmail(email);
	}

	public boolean update(Usuario usuario) {
		// ensure password is hashed before update (if provided)
		if (usuario.getSenha() != null && !isBCryptHash(usuario.getSenha())) {
			String hashed = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
			usuario.setSenha(hashed);
		}
		return usuarioDAO.update(usuario);
	}

	private boolean isBCryptHash(String s) {
		return s != null && s.startsWith("$2");
	}

	public boolean delete(int id) {
		return usuarioDAO.delete(id);
	}
}
