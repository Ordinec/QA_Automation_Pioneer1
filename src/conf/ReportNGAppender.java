package conf;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

public class ReportNGAppender extends AppenderSkeleton {

    @Override
    protected void append(LoggingEvent loggingEvent) {
        final Layout layout = getLayout();
        final String valueToLog;
        if (layout != null) {
            valueToLog = layout.format(loggingEvent);
        } else {
            valueToLog = loggingEvent.getMessage().toString();
        }

        CustomLogger.log(valueToLog);
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }
}
