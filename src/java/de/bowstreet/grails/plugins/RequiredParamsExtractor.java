package de.bowstreet.grails.plugins;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.groovy.grails.commons.GrailsClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequiredParamsExtractor {
    
    public static final Logger log;
    
    static {
        log = LoggerFactory.getLogger(RequiredParamsExtractor.class);
    }
    
    public Set<String> getRequiredParametersForControllerAction(Class<GrailsClass> controllerClass, String actionName) {

        Set<String> resultSet = new HashSet<String>();
        
        for (Method method : controllerClass.getMethods()) {
            if (method.getName().equals(actionName)) {
                
                if (method.isAnnotationPresent(RequiredParameters.class)) {
                    log.debug("Annotation is present, return value()");
                    for (String string : method.getAnnotation(RequiredParameters.class).value()) {
                        resultSet.add(string);
                    }
                }
                break;
            }
        }
        
        return resultSet;
    }
    
}
