/**
 *  LocalRestController
 *
 *  Copyright 2017 David Norris
 *
 */
definition(
    name: "LocalRestController",
    namespace: "Hillrunner2008",
    author: "David Norris",
    description: "Hoping to make calls to a local network from hub on contact open/closed events.",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Call rest api when open or closed") {
        input "magnetsCallingRest", "capability.contactSensor", required: true, title: "Test?"
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    subscribe(magnetsCallingRest, "contact.closed", contactClosedHandler)
    subscribe(magnetsCallingRest, "contact.open", contactOpenHandler)
}


// TODO: implement event handlers
def contactOpenHandler(evt) {
    log.debug "opened fired"
     sendHubCommand(new physicalgraph.device.HubAction("""GET /opened HTTP/1.1\r\nHOST: 192.168.1.142:8080\r\n\r\n""", physicalgraph.device.Protocol.LAN))
}
def contactClosedHandler(evt) {
    log.debug "closed fired"
    sendHubCommand(new physicalgraph.device.HubAction("""GET /closed HTTP/1.1\r\nHOST: 192.168.1.142:8080\r\n\r\n""", physicalgraph.device.Protocol.LAN))
  
}