package me.markrose.recorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value="/upload/{filename}", decoders = BlobMessageDecoder.class, configurator = Configurator.class)
public class RecorderEndpoint {

	private static final Logger logger = LoggerFactory.getLogger(RecorderEndpoint.class);

	private OutputStream out;

	@OnOpen
	public void onOpen(Session session, @PathParam("filename") String filename,
			EndpointConfig config) throws IOException, EncodeException {

		logger.info("onOpen");
		File recordingDir = ContextListener.getRecordingDir();
		File f = new File(recordingDir, filename);
		recordingDir.mkdirs();
		out = new FileOutputStream(f);
	}

	@OnClose
	public void onClose(Session session) throws IOException, EncodeException {
		logger.info("onClose");
		if (out != null) {
			out.close();
		}
	}

	@OnMessage
	public void onMessage(Session session, BlobMessage message) throws IOException, EncodeException {
		logger.info("onSend - data length is " + message.getData().length);
		out.write(message.getData());
	}

}
