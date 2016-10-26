package Gate.Gate1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteXLS {
	
	public static void csvWriter(int totalFiles, ArrayList<ArrayList<String>> rows) 
			throws IOException{
		
		System.out.println("Writing output file..");
		long csvStartTime = System.currentTimeMillis();
				
		FileOutputStream xlsFileStream = new FileOutputStream(new File("src/output/output11043/output.xlsx"));

		
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Outputsheet");		
		
		
		//Create Header ROW
		Row row1 = sheet.createRow(0);
		
		//Create Header CELLS
		Cell cell0 = row1.createCell(0);
		cell0.setCellValue("Nr Comments");
		
		Cell cell1 = row1.createCell(1);
		cell1.setCellValue("Nr Positive Comments");
		
		Cell cell2 = row1.createCell(2);
		cell2.setCellValue("Nr Negative Comments");
		
		Cell cell3 = row1.createCell(3);
		cell3.setCellValue("Title");
		
		for(int y=4;y<12;y++){
			Cell cellC = row1.createCell(y);
			cellC.setCellValue("Comment " + "#" + (y-3));
		}
		
		//For each file complete the next row
		for(int x=1; x<=totalFiles; x++){
			Row row = sheet.createRow(x);
			for(int y=0;y<rows.get(x-1).size();y++){
				// create a cell
				Cell cell = row.createCell(y);
				cell.setCellValue(rows.get(x-1).get(y));
			}
		}
	
		// write sheet in file
		workbook.write(xlsFileStream);
		xlsFileStream.close();
		
		long csvEndTime = System.currentTimeMillis();
		long csvTime = csvEndTime-csvStartTime;
		System.out.println("Time to write all files: "+csvTime+"ms");
	}
}