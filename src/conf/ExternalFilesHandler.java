package conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.lf5.viewer.configure.ConfigurationManager;
import tests.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sergii Moroz
 */
public class ExternalFilesHandler {

    private Log log = LogFactory.getLog(this.getClass());

    public ExternalFilesHandler selectAndAddFile(FileData files) {
        if (Main.OS.contains("win")) {
            final List<String> args = new ArrayList(Arrays.asList(ExternalFilesHandler.class.getResource("/autoitscript/upload_file_using_browse_button.exe").getFile(), "Open", files.getFolder().getAbsolutePath()));
            log.info(files.getFolder().getAbsolutePath());
            args.addAll(Arrays.asList(files.getFiles()));
            final String[] stringArgs = args.toArray(new String[args.size()]);
            log.info("Attempt to execute file '/autoitscript/upload_file_using_browse_button.exe'");
            try {
                Runtime.getRuntime().exec(stringArgs);
            } catch (IOException e) {
                log.error("Fail! Error occurred while attempt to execute file '/autoitscript/upload_file_using_browse_button.exe'");
                e.printStackTrace();
            }
            log.info("- - Ok! File executed successfully");
        } else {
            log.info("Current OS does not support .exe files, so method 'UploadFilesStep2Steps.selectAndAddFiles' is skipped");
        }
        return this;
    }

    public ExternalFilesHandler waitForFinishUploading(String fileName, int secondsToWait) {
        SomePage somePage = new SomePage();
        log.info("Waiting for file '" + fileName + "' uploading...");
        somePage.doneIconForFile(fileName).waitForElementToBeVisible();
        log.info("- - Ok! File '" + fileName + "' has been uploaded.");
        return this;
    }

    public ExternalFilesHandler dragNDropFiles(FileData fileData) {
        if (Main.OS.contains("win")) {
            String tempFolder = fileData.getFolder().getAbsolutePath();
            int numberOfFilesToDragNDrop = fileData.getFiles().length;
//
            String[] newFileWindowParams = new String[]{ExternalFilesHandler.class.getResource("/autoitscript/create_window_by_path.exe").getFile(), tempFolder};
            String[] dragNDropParams = new String[]{ExternalFilesHandler.class.getResource("/autoitscript/select_files_and_dragndrop_v2.exe").getFile(), Integer.toString(numberOfFilesToDragNDrop)};

            log.info("Attempt to execute .exe for drag'n'drop files");
            try {
                Runtime.getRuntime().exec(newFileWindowParams);
                Runtime.getRuntime().exec(dragNDropParams);
            } catch (IOException e) {
                log.error("Fail! Error occurred while attempt to execute .exe for drag'n'drop files");
                e.printStackTrace();
            }
            log.info("- - Ok! File executed successfully");
        } else {
            log.info("Current OS does not support .exe files, so method 'UploadFilesStep2Steps.dragNDropFiles' is skipped");
        }
        return this;
    }
}
