package pa.iscde.minimap.internal;

import java.io.BufferedWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;

public class SettingsManager {

	private static final Logger LOGGER = Logger.getLogger(SettingsManager.class);

	/** path in which the settings file is stored */
	private static final Path SETTINGS_PATH = Paths.get("minimap.ini");

	private static final Gson GSON = new Gson();
	private static final Type SETTINGS_TYPE = new TypeToken<Map<String, Boolean>>(){}.getType();

	/** the state that new extension rules will have */
	private static final boolean DEFAULT_STATE = true;

	private static Map<String, Boolean> settings = new HashMap<>();

	/** flag used to check if there were any changes to the settings */
	private static boolean dirty = false;

	public static void load() {
		if(!Files.exists(SETTINGS_PATH, LinkOption.NOFOLLOW_LINKS)) {
			LOGGER.debug("Settings file does not exist: " + SETTINGS_PATH.toAbsolutePath().toString());
			return;
		}

		try {
			String json = Files.lines(SETTINGS_PATH, StandardCharsets.UTF_8).collect(Collectors.joining());
			settings = GSON.fromJson(json, SETTINGS_TYPE);
		} catch (Exception e) {
			LOGGER.error("Error loading settings file", e);
		} finally {
			LOGGER.info("Loaded settings for " + settings.size() + " rules");
		}
	}

	public static void save() {
		if (!dirty) {
			LOGGER.debug("Settings unchanged");
			return;
		}

		try {
			String json = GSON.toJson(settings, SETTINGS_TYPE);

			try (BufferedWriter writer = Files.newBufferedWriter(SETTINGS_PATH, StandardCharsets.UTF_8)) {
				writer.write(json);
			}
		} catch (Exception e) {
			LOGGER.error("Error writing settings file", e);
		} finally {
			dirty = false;
			LOGGER.info("Saved settings for " + settings.size() + " rules");
		}
	}

	public static boolean isEnabled(String ruleId) {
		return settings.computeIfAbsent(ruleId, key -> {
			dirty = true;
			return DEFAULT_STATE;
		});
	}

	public static void setEnabled(String ruleId, boolean enabled) {
		settings.put(ruleId, enabled);
		dirty = true;
	}
}
