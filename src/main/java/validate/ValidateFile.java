package validate;

import javax.inject.Named;
import java.io.InputStream;
import java.util.List;

public interface ValidateFile {

    boolean validateHeader(InputStream file, List<String> nameColumns, int countColumns);
    boolean validateExtension(InputStream file);
    List<String> content(InputStream file,List<String> namesColumns, String separator);
    int countRow(InputStream file);
}
