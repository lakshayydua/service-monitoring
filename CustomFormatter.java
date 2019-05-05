import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        StringBuffer buffer = new StringBuffer();
        
        buffer.append(record.getLevel() + ":" + record.getSourceClassName() + ":" + record.getSourceMethodName() + ": "  + record.getMessage() + "\n");
        return buffer.toString();
    }

}