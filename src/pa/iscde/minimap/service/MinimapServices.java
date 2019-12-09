package pa.iscde.minimap.service;

import java.io.File;

/**
 * Services offered by the Minimap component.
 */
public interface MinimapServices {

	/**
	 * Get the file that is opened in the minimap view.
	 * @return a reference to the opened file, or null if no file is opened
	 */
	File getOpenedFile();

	/**
	 * Opens a file in the minimap view.
	 * @param file (non-null) file to open
	 */
	void openFile(File file);

}