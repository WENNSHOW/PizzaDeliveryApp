package yarosh.vlad.pizzaapp.web;

import yarosh.vlad.pizzaapp.exception.ObjectNotFoundException;
import yarosh.vlad.pizzaapp.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import static yarosh.vlad.pizzaapp.constant.ControllerConstants.*;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView stringNotFound(ResourceNotFoundException resourceNotFoundException) {
        ModelAndView modelAndView = new ModelAndView("string-not-found");
        modelAndView.addObject(RESOURCE_NAME, resourceNotFoundException.getResourceName());
        modelAndView.addObject(FIELD_NAME, resourceNotFoundException.getFieldName());
        modelAndView.addObject(FIELD_VALUE_STRING, resourceNotFoundException.getFieldValueString());
        return modelAndView;
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView idNotFound(ObjectNotFoundException objectNotFoundException) {
        ModelAndView modelAndView = new ModelAndView("id-not-found");
        modelAndView.addObject(RESOURCE_NAME, objectNotFoundException.getResourceName());
        modelAndView.addObject(FIELD_NAME, objectNotFoundException.getFieldName());
        modelAndView.addObject(FIELD_VALUE, objectNotFoundException.getFieldValue());
        return modelAndView;
    }
}
