package ru.springuser.utils;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class TimeJtwigFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "calctime";
    }

    @Override
    public Object execute(FunctionRequest functionRequest) {
        String result = "time";
        if (functionRequest.getNumberOfArguments() == 1){
            if (functionRequest.get(0) instanceof Date){
                long seconds = (new Date().getTime() - ((Date) functionRequest.get(0)).getTime()) / 1000;
                if (seconds < 60){
                    result = seconds + " секунд назад";
                } else if (seconds < 3600){
                    result = seconds / 60 + " минут назад";
                } else {
                    result = "более часа назад";
                }
            }
        }
        return result;
    }
}
