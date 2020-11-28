package com.raos.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.raos.model.ProductUpload;


public class CSVHelper {
	

	  public static String TYPE = "csv";
	  
	  public static boolean hasCSVFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getOriginalFilename().substring(file.getOriginalFilename().length()-3, file.getOriginalFilename().length()))) {
	      return false;
	    }

	    return true;
	  }

	public static List<ProductUpload> csvToProducts(InputStream is) {
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        CSVParser csvParser = new CSVParser(fileReader,
	        CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
	      List<ProductUpload> products = new ArrayList<ProductUpload>();
	      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

	      for (CSVRecord csvRecord : csvRecords) {
	    	  ProductUpload prod = new ProductUpload();
	    	  prod.setProduct_name(csvRecord.get("productname"));
	          prod.setSubcategory(Integer.parseInt(csvRecord.get("subcategory"))); 
	          prod.setCategory(Integer.parseInt(csvRecord.get("category")));
	          prod.setDiscount_flag(Integer.parseInt(csvRecord.get("discountflag")));
	          prod.setImage_url(csvRecord.get("imageurl"));
	          prod.setStatus(Integer.parseInt(csvRecord.get("status")));
	          prod.setUnit_metrics(Integer.parseInt(csvRecord.get("unitmetrics")));
	          products.add(prod);
	      }

	      return products;
	    } catch (Exception e) {
	      throw new RuntimeException("FAILED TO PARSE CSV FILE " + e.getMessage());
	    }
	  }
}