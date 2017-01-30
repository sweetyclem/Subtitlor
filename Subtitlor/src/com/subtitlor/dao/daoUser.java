package com.subtitlor.dao;

import java.util.List;
import com.subtitlor.beans.Subtitle;

public interface daoUser {
	void add(Subtitle sub, String type) throws DaoException;
	List<Subtitle> list(String type) throws DaoException;
}
