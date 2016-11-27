/**
 * 
 */
package com.test.username.dao;

import java.util.List;

/**
 * @author mbt
 *
 */
public interface UsernameDAO {

	public String getUserName(String username);

	public List<String> getRestrictedWords();

	public List<String> listUserName(String username);

}
