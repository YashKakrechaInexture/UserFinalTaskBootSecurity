package com.inexture.UserFinalTaskBoot.Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController{
	
	@RequestMapping(value = "/error")
    public String error(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // display specific error page
            if (statusCode == HttpStatus.NOT_FOUND_404) {
                return "404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR_500) {
                return "500";
            } else if (statusCode == HttpStatus.FORBIDDEN_403) {
                return "403";
            }
        }

        return "error";
    }
}
