package com.test.username.controller;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.username.Exception.UserNameException;
import com.test.username.service.UsernameService;

@Controller
public class UsernameController {
	
	@Autowired
	UsernameService usernameService;
	
	// Logger to log the details
	private static Logger logger=Logger.getLogger(UsernameController.class); 
	
	// Name of the class name used for logging	
	private final String CLASS_NAME = "UsernameController";
	
	@RequestMapping(value = "/username", method = RequestMethod.GET)
	public String init(Model model) {
		model.addAttribute("msg", "Please Enter Your user name");
		return "username";
	}

	@RequestMapping(value = "/username", method = RequestMethod.POST)
	public String checkUserName(Model model, @ModelAttribute("userBean") UserBean userBean) {
		final String METHOD_NAME = "checkUserName";
		logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : Starting the method : " + userBean);
		
		try {
			Map<Boolean, Set<String>> resultMap = usernameService.checkUsername(userBean.getUserName());
			System.out.println(resultMap);
			for(Map.Entry<Boolean, Set<String>> mapEntry :resultMap.entrySet()){
				if(mapEntry.getKey() == true){
					model.addAttribute("userNameExist", true); // the entered username already exist in DB
				}else if (mapEntry.getKey() == false){
					model.addAttribute("userNameExist", false); // the entered username not exist in DB
					model.addAttribute("alternateUserNameSet", mapEntry.getValue()); // load 14 alternate username into the model attributes
				}else{
					model.addAttribute("error", "Technical error occured please retry");
				}
			}			
		} catch (UserNameException e) {
			if("100".equalsIgnoreCase(e.getErrorCode())){
				model.addAttribute("error", "UserName should be atleast 6 characters");
				return "username";
			}
			else if("101".equalsIgnoreCase(e.getErrorCode())){
				model.addAttribute("error", "It is not a valid username. Please enter valid one");
				return "username";
			}else{
				e.printStackTrace();
				model.addAttribute("error", "Technical error occured please retry");
			}
		}
		//model.addAttribute("username", username);
		logger.info(CLASS_NAME +" : " + METHOD_NAME  +  " : ending the method : ");
		return "username";
	}
}
