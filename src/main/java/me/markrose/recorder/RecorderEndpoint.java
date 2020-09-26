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

@ServerEndpoint(value="/upload/{filename}", decoders = BlobMessageDecoder.class, configurator = Configurator.class)
public class RecorderEndpoint {

	private OutputStream out;

	private File getRecordingDir() {
		String path = ContextListener.getServletContext().getRealPath("/recordings");
		return new File(path);
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("filename") String filename,
			EndpointConfig config) throws IOException, EncodeException {

		System.out.println("onOpen");
		File recordingDir = getRecordingDir();
		File f = new File(getRecordingDir(), filename);
		recordingDir.mkdirs();
		out = new FileOutputStream(f);
	}

	@OnClose
	public void onClose(Session session) throws IOException, EncodeException {
		System.out.println("onClose");
		if (out != null) {
			out.close();
		}
	}

	@OnMessage
	public void onMessage(Session session, BlobMessage message) throws IOException, EncodeException {
		System.out.println("onSend - data length is " + message.getData().length);
		out.write(message.getData());
	}

}
