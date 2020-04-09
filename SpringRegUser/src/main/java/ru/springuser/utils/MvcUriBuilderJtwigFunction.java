package ru.springuser.utils;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import ru.springuser.config.RootConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;



@Component
public class MvcUriBuilderJtwigFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "url";
    }

    @Override
    public Object execute(FunctionRequest functionRequest) {
        String result = "";
        if(functionRequest.getNumberOfArguments() == 1){
            if (functionRequest.get(0) instanceof String){
                String controllerName = ((String) functionRequest.get(0)).split("#")[0];
                String methodName = ((String) functionRequest.get(0)).split("#")[1];
                ComponentScan annotation = RootConfig.class.getAnnotation(ComponentScan.class);
                String basePackage = annotation.basePackages()[0];
                try {
                    List<File> classes = Files.walk(Paths.get(Thread.currentThread().getContextClassLoader().getResource(basePackage.replace(".","\\")).toURI()))
                            .filter(Files::isRegularFile)
                            .map(Path::toFile)
                            .collect(Collectors.toList());
                    for(File file: classes){
                        String symbols = file.getName().chars()
                                .filter(c -> !Character.isLowerCase(c))
                                .mapToObj(c -> String.valueOf((char) c))
                                .collect(Collectors.joining());
                        if (symbols.equals(controllerName)){
                            Method[] methods = Class.forName(file.getName()).getMethods();
                            for(Method method: methods){
                                if(method.getName().equals(methodName)){
                                    RequestMapping anno = method.getAnnotation(RequestMapping.class);
                                    result = anno.path()[0];
                                }
                            }
                        }
                    }
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return result;
    }
}
