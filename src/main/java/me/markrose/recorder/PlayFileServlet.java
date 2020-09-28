package me.markrose.recorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
@WebServlet("/play/*")
public class PlayFileServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(PlayFileServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		File requestedFile = new File(ContextListener.getRecordingDir(), path);

		logger.info("Serving file {} of size {}", requestedFile.getPath(),
				requestedFile.length());
		try (FileInputStream in = new FileInputStream(requestedFile);
				ServletOutputStream out = resp.getOutputStream()) {

			resp.setContentType("audio/webm");
			resp.setStatus(HttpServletResponse.SC_OK);
			for (;;) {
				byte[] buf = new byte[10000];
				int nread = in.read(buf);
				if (nread < 0) {
					break;
				}

				logger.info("Writing {} bytes", nread);
				out.write(buf, 0, nread);
			}
		}
	}

}
