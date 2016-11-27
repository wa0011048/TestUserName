/**
 * 
 */
package com.test.username.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.test.username.service.UsernameServiceImpl;
/**
 * @author mbt
 *
 */
@Repository("usernameDAO")
public class UsernameDAOImpl implements UsernameDAO{
	
	// Logger to log the details
	private static Logger logger=Logger.getLogger(UsernameDAOImpl.class); 
	
	// Name of the class name used for logging	
	private final String CLASS_NAME = "UsernameDAOImpl";
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(
		NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	

	@Override
	public String getUserName(String username) {
		
		final String METHOD_NAME = "getUserName";
		logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : Starting the method- input: " + username);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", username);
		
		final String sql = "SELECT name FROM username WHERE name=:name";

		String usernameFromDB = null;
		try {
			usernameFromDB = namedParameterJdbcTemplate
                          .queryForObject(sql, params, new UserNameMapper());
		} catch (EmptyResultDataAccessException e) {
			// do nothing, return null
		}

		logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : Ending the method- output: " + usernameFromDB);
		return usernameFromDB;
	}


	@Override
	public List<String> getRestrictedWords() {
		final String METHOD_NAME = "getRestrictedWords";
		logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : Starting the method: " );
		
		final String sql = "SELECT name FROM restricted_words";

		List<String> restrictedWordList= namedParameterJdbcTemplate
                          .query(sql, new HashMap<String, Object>(), new RestrictedWordMapper());

		logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : Ending the method- output: " + restrictedWordList);
		return restrictedWordList;
	}	

	@Override
	public List<String> listUserName(String username) {
		final String METHOD_NAME = "listUserName";
		logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : Starting the method- input: " + username);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", username);
		
		final String sql = "SELECT name FROM username WHERE name like '%:name%' ";

		List<String>  usernameListFromDB  = namedParameterJdbcTemplate
                          .query(sql, params, new UserNameMapper());

		logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : Ending the method- output: " + usernameListFromDB.size());
		return usernameListFromDB;
	}
	
	
	/**
	 * Class for the mapping between SQL resultset and to the result string - username.
	 *
	 */
	private static final class UserNameMapper implements RowMapper<String> {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return (rs.getString("name"));
		}
	}
	
	/**
	 * Class for the mapping between SQL resultset and to the result string - restricted words.
	 *
	 */
	private static final class RestrictedWordMapper implements RowMapper<String> {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return (rs.getString("name"));
		}
	}
		
}
