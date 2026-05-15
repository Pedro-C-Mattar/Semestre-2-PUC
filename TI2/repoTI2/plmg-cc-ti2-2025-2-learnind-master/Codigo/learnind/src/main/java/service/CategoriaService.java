package service;

import dao.CategoriaDAO;
import dao.VideoDAO;
import model.Categoria;
import java.util.List;

public class CategoriaService {
	private CategoriaDAO categoriaDAO;

	public CategoriaService() {
		categoriaDAO = new CategoriaDAO();
	}

	public List<Categoria> getAll() {
		return categoriaDAO.get();
	}

	public Categoria getById(int id) {
		return categoriaDAO.get(id);
	}

	public boolean save(Categoria c) {
		if (c.getIdCategoria() <= 0) {
			c.setIdCategoria(categoriaDAO.getNextId());
		}
		return categoriaDAO.insert(c);
	}

	public boolean update(Categoria c) {
		return categoriaDAO.update(c);
	}

	public boolean delete(int id) {
		// nullify category reference on videos before deleting the category to avoid FK issues
		try {
			VideoDAO videoDAO = new VideoDAO();
			videoDAO.nullifyCategoria(id);
		} catch (Exception e) {
			// log and continue to attempt category delete; caller will receive false on failure
			System.err.println("Erro ao nullificar categoria em videos: " + e.getMessage());
		}
		return categoriaDAO.delete(id);
	}
}
