package de.bowstreet.grails.plugins

import javax.servlet.http.HttpServletResponse

class RequiredParametersFilters {

    def filters = {
        all(controller:'*', action:'*') {
            before = {
                log.debug "running required parameters check"

                // check whether the action has a @Required-Annotation
                def controller = controllerName ? grailsApplication.getArtefactByLogicalPropertyName("Controller", controllerName) : null
                def action = actionName ?: 'index'

                log.debug "target route: ${controller}/${action}"

                Set<String> requiredParameters

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

                if (missingParameters) {
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
