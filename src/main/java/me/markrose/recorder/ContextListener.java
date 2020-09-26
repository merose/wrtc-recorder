package me.markrose.recorder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class ContextListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

	private static ServletContext context;

	public static synchronized ServletContext getServletContext() {
		return context;
	}

	private static synchronized void setServletContext(ServletContext context) {
		ContextListener.context = context;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Servlet context initialized: recording path={}",
				sce.getServletContext().getRealPath("/recordings"));
		setServletContext(sce.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Servlet context destroyed");
	}

}
