/**
 * 
 */
package com.test.username.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.username.Exception.UserNameException;
import com.test.username.dao.UsernameDAO;

/**
 * @author mbt
 *
 */
@Service("usernameService")
public class UsernameServiceImpl implements UsernameService{
	
	@Autowired
	UsernameDAO usernameDAO;
	
	//get the list of restricted words on class load to avoid multiple time of loading
	private static List<String> restrictedWordList;
	
	// Logger to log the details
	private static Logger logger=Logger.getLogger(UsernameServiceImpl.class); 
	
	// Name of the class name used for logging	
	private final String CLASS_NAME = "UsernameServiceImpl";

	/* (non-Javadoc)
	 * @see com.test.username.service.UsernameService#checkUsername(java.lang.String)
	 */
	@Override
	public Map<Boolean, Set<String>> checkUsername(String username) throws UserNameException {
		
		final String METHOD_NAME = "checkUserName";
		logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : Starting the method : " + username);
		
		//Check if the username size is greater than or equal to 6.
		if(username.length() < 6){
			logger.error(CLASS_NAME +" : " + METHOD_NAME  +  " : UserName is less than 6 characters" );
			throw new UserNameException("100","UserName should be atleast 6 characters");
		}
		
		//Check if the username contains any restricted words.
		if(isUserNameContainsRestrictedWord(username)){
			logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : Not a valid user name ");
			throw new UserNameException("101","Invalid UserName");
		}
		
		// check if the username exist in the database
		String usernameFromDB = usernameDAO.getUserName(username);
		
		// populate the results with the true or false
		Map<Boolean, Set<String>> resultMap = new HashMap<Boolean, Set<String>>();		
		if(StringUtils.isEmpty(usernameFromDB)){
			 resultMap.put(false , getAlternateUserNames(username));
		}else{			
			resultMap.put(true , null);			 
		}
		
		logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : ending the method : " + resultMap);
		return resultMap;
	}

	/**
	 * Method to generate alternateUserNames
	 * @param username
	 * returns the set of alternate user names in String
	 */
	private Set<String> getAlternateUserNames(String username) {
		
		Set<String> alternateUserNameSet = new TreeSet<String>();
		String alternateUserName = null;
		List<String> matchingUsernameList= listMatchingUserName(username);
		
		for(int i=0;i<=50;i++){
			alternateUserName = username+RandomStringUtils.random(5, true, true);
			if(!isUserNameContainsRestrictedWord(alternateUserName) // check if the username contains restricted words
					&& !matchingUsernameList.contains(alternateUserName)) // check if the username already exist in DB
				alternateUserNameSet.add(alternateUserName);
			if(alternateUserNameSet.size() == 14) 
				break; // exit the for loop if the list size is 14 as per the requirement
		}
		return alternateUserNameSet;
	}

	/**
	 * Method name used to check if the given username is valid
	 * @param username
	 * @return true if the given name contains the restricted words
	 */
	public boolean isUserNameContainsRestrictedWord(String username) {
		if(restrictedWordList==null){
			restrictedWordList = getRestrictedWords();			
		for(String restrictedWord : restrictedWordList){
			if(username!=null && username.contains(restrictedWord)){			
				return true;
			}
		}
		}
		return false;
	}
	
	/**
	 * Method to get the list of matching username
	 * @param username
	 * @return true if the given name contains the restricted words
	 */
	public List<String> listMatchingUserName(String username) {
		// check if the username exist in the database
		List<String> usernameListFromDB = usernameDAO.listUserName(username);
		return usernameListFromDB;
	}
	
	
	/**
	 * Method to get the list of restricted words
	 * @return list of string the restricted words
	 */
	private List<String> getRestrictedWords() {
		if(restrictedWordList==null){			
			if(usernameDAO!=null)
				restrictedWordList = usernameDAO.getRestrictedWords();
		}
		return restrictedWordList;
	}

}
