package service;

import dao.FeedbackDAO;
import model.Feedback;
import java.time.LocalDateTime;
import java.util.List;

public class FeedbackService {
	private FeedbackDAO feedbackDAO;

	public FeedbackService() {
		feedbackDAO = new FeedbackDAO();
	}

	public boolean save(Feedback f) {
		if (f.getIdFeedback() <= 0) {
			f.setIdFeedback(feedbackDAO.getNextId());
		}
		if (f.getDataFeedback() == null) {
			f.setDataFeedback(LocalDateTime.now());
		}
		return feedbackDAO.insert(f);
	}

	public List<Feedback> getByVideo(int idVideo) {
		return feedbackDAO.getByVideo(idVideo);
	}

	public boolean deleteByVideo(int idVideo) {
		// delete each feedback related to the video
		List<Feedback> list = feedbackDAO.getByVideo(idVideo);
		boolean ok = true;
		for (Feedback f : list) {
			ok = ok && feedbackDAO.delete(f.getIdFeedback());
		}
		return ok;
	}

	public int getNextId() {
		return feedbackDAO.getNextId();
	}
}
