grails-required-parameters
==========================
Required Parameters plugin for the Grails framework.

This plugin simplifies working with Grails controller actions as it defines a simple annotation-style method to check whether all necessary parameters of an action are present.

## How to use

When you install the plugin, you get a new annotation-type: `de.bowstreet.grails.plugins.RequiredParameters`.
To use it, you simply add it to your desired action-method (note: only methods as actions are supported right now) like this:
```groovy

import de.bowstreet.grails.plugins.RequiredParameters

public class FooController {
	
	@Required('id', 'anotherParam')
	def bar() {
		// action logic...
	}
	
}
```