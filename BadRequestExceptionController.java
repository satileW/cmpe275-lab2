package edu.sjsu.cmpe275.lab2;

import javax.servlet.http.HttpServletResponse;

import org.aopalliance.intercept.Joinpoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.jasper.tagplugins.jstl.core.Catch;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sun.javafx.logging.Logger;

import sun.util.logging.resources.logging;

@ControllerAdvice
public class BadRequestExceptionController {
	
	//private static final Log logging = LogFactory.getLog(BadRequestExceptionController.class);
	@ExceptionHandler(BadRequestException.class)
	@ResponseBody
	public  BadRequest handleDeleteError(BadRequestException e,HttpServletResponse response){
		//logging.error("!!!!!!!!!!!!!!");
		response.setContentType("application/json;charset=UTF-8");
	    response.setHeader("Content-Type", "application/json; charset=utf-8");
	    response.setCharacterEncoding("UTF-8");
		BadRequest badRequest = null;
		//try {
		badRequest = new BadRequest(HttpServletResponse.SC_NOT_FOUND,e.getErrorMsg()+" "+e.getPassengerID()+" does not exist ");
//		} catch (BadRequestException e2) {
//			// TODO: handle exception
		///no need to catch manually
//		}
		return 	badRequest;//this is a xml format response and I don't know how to change it to json
	}
}
