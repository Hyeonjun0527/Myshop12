package myshop12.com.model2.mvc.common.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToIntegerConverter implements Converter<String,Integer> {

    @Override
    public Integer convert(String source){
        if(source==null || source.isEmpty()){
            return 0;
        }
        try{
            return Integer.parseInt(source);
        } catch (NumberFormatException e){
            return 0;
        }
    }

}
