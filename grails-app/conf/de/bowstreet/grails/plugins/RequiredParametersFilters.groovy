package de.bowstreet.grails.plugins

import java.lang.annotation.Annotation
import java.lang.reflect.Method

import javax.servlet.http.HttpServletResponse;

import de.bowstreet.grails.plugins.*

class RequiredParametersFilters {
    
    def filters = {
        all(controller:'*', action:'*') {
            before = {
                log.debug "running required parameters check"
                
                // check whether the action has a @Required-Annotation
                def controller = grailsApplication.getArtefactByLogicalPropertyName("Controller", controllerName)
                def action = actionName ? actionName : 'index'

                log.debug "target route: ${controller}/${action}"
                
                Set<String> requiredParameters = null
                
                if (controller) {
                    Class controllerClass = controller.clazz
                    requiredParameters = new RequiredParamsExtractor().getRequiredParametersForControllerAction(controllerClass, action)
                }
                
                def missingParameters = []
                requiredParameters.each {
                    if (params[it] == null) {
                        missingParameters.add(it)
                    }
                }

                if (missingParameters.size() > 0) {
                    log.warn "Request has been blocked. The following required parameters were not passed: ${missingParameters}"
                    
                    // TODO define what to do in this case
                    render status: HttpServletResponse.SC_BAD_REQUEST, text: "Missing parameters: ${missingParameters}"
                    
                    return false
                }

                return true
            }
        }
    }
}
