package pt.iscte.pidesco.minimap.ext.demo;

import java.io.File;

import org.apache.log4j.Logger;
import pa.iscde.minimap.service.MinimapListener;

public class DemoListener implements MinimapListener {

	private static final Logger LOG = Logger.getLogger(DemoListener.class);

	@Override
	public void fileParsed(File file) {
		LOG.info("EVENT! File parsed: " + file);
	}

	@Override
	public void lineSelected(File file, int line, String lineContent) {
		LOG.info("EVENT! Line selected: " + file.getName() + " L" + line + ": " + lineContent);
	}
}
