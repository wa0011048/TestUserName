/**
 * 
 */
package com.test.username.service;

import java.util.Map;
import java.util.Set;

import com.test.username.Exception.UserNameException;

/**
 * @author winston
 *
 */
public interface UsernameService {
	
	/**
	 * Method to check if the username exists in DB. If not, returns the alternate usernames
	 * And also validates if it contains any restricted workds
	 * @param username
	 * @return
	 * @throws UserNameException
	 */
	public Map<Boolean, Set<String>> checkUsername(String username) throws UserNameException;
	
	/**
	 * Method to check if the given username contains any restricted words
	 * @param username
	 * @return
	 */
	public boolean isUserNameContainsRestrictedWord(String username);

}
