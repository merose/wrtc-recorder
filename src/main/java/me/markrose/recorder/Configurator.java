package me.markrose.recorder;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class Configurator extends ServerEndpointConfig.Configurator {
	
	

    @Override
    public void modifyHandshake(ServerEndpointConfig conf,
                                HandshakeRequest req,
                                HandshakeResponse resp) {

    	HttpSession httpSession = (HttpSession) req.getHttpSession();
    	String uploadDir;
    	if (httpSession != null) {
    		uploadDir = httpSession.getServletContext().getRealPath("/uploads");
    	} else {
    		uploadDir = "/Users/merose/Downloads/tmp";
    	}
        conf.getUserProperties().put("uploadDir", uploadDir);
    }

}