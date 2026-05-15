package service;

import dao.FavoritoDAO;
import model.Favorito;
import java.time.LocalDateTime;
import java.util.List;

public class FavoritoService {
	private FavoritoDAO favoritoDAO;

	public FavoritoService() {
		favoritoDAO = new FavoritoDAO();
	}

	public Favorito getByUserAndVideo(int userId, int videoId) {
		return favoritoDAO.getByUserAndVideo(userId, videoId);
	}

	public List<Favorito> getByUser(int userId) {
		return favoritoDAO.getByUser(userId);
	}

	public boolean addFavorite(int userId, int videoId) {
		Favorito existing = favoritoDAO.getByUserAndVideo(userId, videoId);
		if (existing != null) return false; // already favorited
		Favorito f = new Favorito();
		f.setIdFavorito(favoritoDAO.getNextId());
		f.setDataFavoritado(LocalDateTime.now());
		f.setIdUsuario(userId);
		f.setIdVideo(videoId);
		return favoritoDAO.insert(f);
	}

	public boolean removeFavoriteByUserAndVideo(int userId, int videoId) {
		Favorito existing = favoritoDAO.getByUserAndVideo(userId, videoId);
		if (existing == null) return false;
		return favoritoDAO.delete(existing.getIdFavorito());
	}
}
