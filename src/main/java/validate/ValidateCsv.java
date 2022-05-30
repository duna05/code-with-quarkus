package validate;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named("csv-file")
public class ValidateCsv implements  ValidateFile{
    @Override
    public boolean validateHeader(InputStream file, List<String> namesColumns, int countColumns) {
        boolean valid = true;
        Reader reader = new InputStreamReader(new BufferedInputStream(file), StandardCharsets.UTF_8);
        CSVParser csvParser = null;
        try {
            int i = 0;
            csvParser = new CSVParser(reader, CSVFormat.RFC4180.withFirstRecordAsHeader());
            if(namesColumns.size() == 0 || namesColumns.isEmpty()){
                valid = false;
            }else {
                for(String name: namesColumns){
                    if(!csvParser.getHeaderNames().get(i++).contentEquals(name) ){
                        valid = false;
                    }
                }

                if((csvParser.getHeaderNames().size() < countColumns) ||
                        (csvParser.getHeaderNames().size() > countColumns)){
                    valid = false;
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                csvParser.close();
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return valid;
    }

    @Override
    public boolean validateExtension(InputStream file) {
        boolean valid = true;
        try(Reader reader = new InputStreamReader(new BufferedInputStream(file))){

            reader.close();
            return valid;

        } catch (Exception e){
            valid = false;
            return valid;
        }
    }

    @Override
    public List<String> content(InputStream file,List<String> namesColumns, String separator) {
        Reader reader = new InputStreamReader(new BufferedInputStream(file), StandardCharsets.UTF_8);
        List<String> list = new ArrayList<>();
        CSVParser csvParser = null;
        try {
            csvParser = new CSVParser(reader, CSVFormat.RFC4180.withFirstRecordAsHeader());

            for (CSVRecord record : csvParser) {
                int i = 0;
                StringBuilder line = new StringBuilder();
                for(String name: namesColumns){
                    line.append(record.get(namesColumns.get(i++)));
                    if(namesColumns.size() > i+1)
                        line.append(separator);
                }


                list.add(line.toString());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(csvParser!=null) csvParser.close();
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    @Override
    public int countRow(InputStream file) {
        int countRow = 0;
        Reader reader = new InputStreamReader(new BufferedInputStream(file), StandardCharsets.UTF_8);
        CSVParser csvParser = null;
        List<CSVRecord> listRecords = new ArrayList<>();
        try {
            csvParser = new CSVParser(reader, CSVFormat.RFC4180.withFirstRecordAsHeader());
            countRow = csvParser.getRecords().size();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                csvParser.close();
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return countRow;
    }
}
