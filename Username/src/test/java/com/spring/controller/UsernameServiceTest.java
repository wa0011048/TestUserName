/**
 * 
 */
package com.spring.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mbt
 *
 */

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.username.Exception.UserNameException;
import com.test.username.service.UsernameService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class UsernameServiceTest {
	
	// Logger to log the details
	private static Logger logger=Logger.getLogger(UsernameServiceTest.class); 
	
	// Name of the class name used for logging	
	private final String CLASS_NAME = "UsernameServiceTest";
	
	   @Autowired 
	   private UsernameService usernameService;	   

		/**
		 * @return the pricePS
		 */
		public UsernameService getUsernameService() {
			return usernameService;
		}
	   
		/**
		 * @throws java.lang.Exception
		 */
		@Before
		public void setUp() throws Exception {			
		}

		/**
		 * @throws java.lang.Exception
		 */
		@After
		public void tearDown() throws Exception {
		}
		
		/**
		 * Test -1 check if UserName is already Exist in DB
		 */
		@Test
		public void checkUserNameAlreadyExist(){	
			final String METHOD_NAME = "checkUserNameAlreadyExist";
			logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : TestCase1 :check if UserName is already Exist in DB" );
			
			try {
				Map<Boolean, Set<String>> resultMap = getUsernameService().checkUsername("winston");				
				assertEquals(resultMap.containsKey(true), true);
			} catch (UserNameException e) {
				e.printStackTrace();
			}	
			logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : TestCase1 :success" );
		}
		
		/**
		 * Test -2 check if the service returns 14 alternate UserName when it does not exist in DB
		 */
		@Test
		public void checkAlternameUserNames(){	
			final String METHOD_NAME = "checkAlternameUserNames";
			logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : TestCase2 :check if the service returns 14 alternate UserName when it does not exist in DB" );
		
			try {
				Map<Boolean, Set<String>> resultMap = getUsernameService().checkUsername("testing");				
				assertEquals(resultMap.containsKey(false), true);	
				assertEquals(resultMap.get(false).size(),14);
				if(resultMap.get(false) !=null){
					Set<String> alternateUsernameSet =  resultMap.get(false);
					for(String alterUserName : alternateUsernameSet){
						// verifies if the alternate username contains testing keyword
						assertTrue(alterUserName.contains("testing")); 
					}
				}
				
			} catch (UserNameException e) {
				e.printStackTrace();
			}	
			logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : TestCase2 :success" );
		}
	 
}
