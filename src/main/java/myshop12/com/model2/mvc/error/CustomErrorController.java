package myshop12.com.model2.mvc.error;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class CustomErrorController extends BasicErrorController {

    public CustomErrorController(ErrorAttributes errorAttributes,
                                 ServerProperties serverProperties,
                                 List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {

        HttpStatus hs = getStatus(request);
        ModelAndView mv = new ModelAndView("500.jsp");

        switch (hs){
            case UNAUTHORIZED:
                mv.setViewName("400.jsp");
                break;
            case NOT_FOUND:
                mv.setViewName("401.jsp");
                break;
            case INTERNAL_SERVER_ERROR:
                mv.setViewName("404.jsp");
                break;
            case SERVICE_UNAVAILABLE:
                mv.setViewName("500.jsp");
                break;
        }

        return mv;
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        return new ResponseEntity<>(body, status);
    }
}