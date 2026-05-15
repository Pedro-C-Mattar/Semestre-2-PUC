package service;

import dao.VideoDAO;
import model.Video;
import java.util.List;

public class VideoService {
	private VideoDAO videoDAO;

	public VideoService() {
		videoDAO = new VideoDAO();
	}

	public List<Video> getAll() {
		return videoDAO.get();
	}

	public Video getById(int id) {
		return videoDAO.get(id);
	}

	public List<Video> searchByTitulo(String q) {
		if (q == null || q.trim().isEmpty()) return getAll();
		return videoDAO.findByTituloLike(q);
	}

	public List<Video> getByUsuarioId(int usuarioId) {
		return videoDAO.findByUsuarioId(usuarioId);
	}

	public List<Video> getByCategoriaId(int categoriaId) {
		return videoDAO.findByCategoriaId(categoriaId);
	}

	public boolean save(Video v) {
		if (v.getIdVideo() <= 0) {
			v.setIdVideo(videoDAO.getNextId());
		}
		return videoDAO.insert(v);
	}

	public boolean update(Video v) {
		return videoDAO.update(v);
	}

	public boolean delete(int id) {
		return videoDAO.delete(id);
	}

	/**
	 * Delete all videos posted by a given user. Returns true if all deletions succeeded.
	 */
	public boolean deleteByUsuarioId(int usuarioId) {
		boolean ok = true;
		java.util.List<Video> videos = getByUsuarioId(usuarioId);
		for (Video v : videos) {
			try {
				ok = ok && delete(v.getIdVideo());
			} catch (Exception e) {
				// log and continue trying to delete other videos
				System.err.println("Erro ao deletar vídeo id=" + v.getIdVideo() + ": " + e.getMessage());
				ok = false;
			}
		}
		return ok;
	}
}
