package com.example.versicherung.Postleitzahl;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.util.List;

public interface IPostleitzahlService {

     public List<Postleitzahl> uploadCsvFile(MultipartFile file);
    public List<Postleitzahl> readCSVFile(BufferedReader csv);
    public List<Postleitzahl> readCSVPath(String filePath);

}
