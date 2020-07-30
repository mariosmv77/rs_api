package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.exception.BusinessException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RestApiErrorController implements ErrorController {

    @RequestMapping("error")
    @ResponseBody
    public String handleErrorJobOffer(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        if (exception instanceof BusinessException)
        {
            return exception.getMessage();
        }

        return exception==null? "N/A": exception.getMessage();
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

