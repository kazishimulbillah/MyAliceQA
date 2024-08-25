package mangaApp.Helper.Resource;

import org.apache.log4j.Logger;

import mangaApp.Helper.Logger.LoggerHelper;

public class ResourceHelper {

	private static Logger log = LoggerHelper.getLogger(ResourceHelper.class);

	public static String getResourcePath(String path) {
		String basePath = System.getProperty("/AutomationExercise/Log4j/");
		log.info(basePath + "/" + path);
		return basePath + "/" + path;
	}

}
