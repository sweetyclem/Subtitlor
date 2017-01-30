package com.subtitlor.dao;

import java.util.List;
import com.subtitlor.utilities.SubtitlesHandler;

public interface daoUser {
	void add(SubtitlesHandler sub, String type) throws DaoException;
	List<SubtitlesHandler> list(String type) throws DaoException;
}
