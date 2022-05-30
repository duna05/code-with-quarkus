package service;

import validate.ValidateFile;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CargaCsvService {

    @Inject
    @Named("csv-file")
    ValidateFile validateCsv;

    public String cargaCsv(){
        BufferedReader bufferLectura = null;
        boolean validHeader;
        boolean validExtension;
        List<String> listLines = new ArrayList<>();
        int countRow = 0;
        try {
            InputStream inputStream = new FileInputStream("C:\\Drive\\test.csv");
            List<String> nameColumns = new ArrayList<>();
            nameColumns.add("baID");
            nameColumns.add("fileName");
            nameColumns.add("documentTypeId");
            nameColumns.add("version");
            validHeader = validateCsv.validateHeader(inputStream,nameColumns,4);
            inputStream = new FileInputStream("C:\\Drive\\test.csv");
            validExtension = validateCsv.validateExtension(inputStream);
            inputStream = new FileInputStream("C:\\Drive\\test.csv");
            listLines = validateCsv.content(inputStream, nameColumns,"|");
            inputStream = new FileInputStream("C:\\Drive\\test.csv");
            countRow = validateCsv.countRow(inputStream);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Cabecera valida: "+validHeader+" Extension valida: "+validExtension+" content line: "+listLines.get(0)
                +" cantidad de filas: "+countRow;
    }
}
