package de.bowstreet.grails.plugins;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.groovy.grails.commons.GrailsClass;

public class RequiredParamsExtractor {
    
    public Set<String> getRequiredParametersForControllerAction(Class<GrailsClass> controllerClass, String actionName) {

        Set<String> resultSet = new HashSet<String>();
        
        for (Method method : controllerClass.getMethods()) {
            if (method.getName().equals(actionName)) {
                System.out.println("found method!");
                
                if (method.isAnnotationPresent(RequiredParameters.class)) {
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
