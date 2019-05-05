import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        StringBuffer buffer = new StringBuffer();
        
        buffer.append(record.getSourceClassName() + ":" + record.getSourceMethodName() + ":" + record.getLevel() + ":" + record.getMessage());
        return buffer.toString();
    }

}