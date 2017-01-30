package com.subtitlor.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.subtitlor.beans.Subtitle;
import com.subtitlor.dao.DaoException;


public class daoUserImpl implements daoUser {
    private DaoFactory daoFactory;

    daoUserImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void add(Subtitle sub, String type) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;     
        
        try {
            connection = daoFactory.getConnection();
            if (type == "original")
            	preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO `subtitlor`.`original` (`name`) VALUES (?);");
            else if (type == "translated")
            	preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO `subtitlor`.`translated` (`name`) VALUES (?);");
            preparedStatement.setString(1, sub.getName());

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e2) {
            }
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }

    }

    @Override
    public List<Subtitle> list(String type) throws DaoException {
        List<Subtitle> subList = new ArrayList<Subtitle>();
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;

        try {
            connection = daoFactory.getConnection();
            statement = (Statement) connection.createStatement();
            if (type == "original")
            	result = statement.executeQuery("SELECT * FROM subtitlor.original;");
            else if (type == "translated")
            	result = statement.executeQuery("SELECT * FROM subtitlor.translated;");

            while (result.next()) {
                String name = result.getString("name");
                int id = result.getInt("id");

                Subtitle sub = new Subtitle();
                sub.setName(name);
                sub.setId(id);

                subList.add(sub);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de donn�es");
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de donn�es");
            }
        }
        return subList;
    }

}