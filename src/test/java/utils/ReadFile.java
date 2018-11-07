package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import au.com.bytecode.opencsv.CSVReader;

public class ReadFile {
	/**
	 * ��Excel���ļ���ȡ��������
	 * 
	 * @param filePath
	 * @param fileName
	 * @param sheetName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Object[][] getTestDataFromExcel(String filePath, // �ļ�·��
			String fileName, // �ļ�����
			String sheetName // ������
	) {
		try {
			// ��ȡ�ļ���
			File file = new File(filePath + "\\" + fileName);
			FileInputStream inputStream = new FileInputStream(file);
			// ��ȡ�ļ���׺����
			String fileExtName = fileName.substring(fileName.indexOf("."));
			// �жϺ�׺������WorkBook����
			Workbook workbook = null;
			if (fileExtName.equals(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (fileName.equals(".xls")) {
				workbook = new HSSFWorkbook(inputStream);
			}
			// ��ñ�
			Sheet sheet = workbook.getSheet(sheetName);
			// ��������������һ�У�������
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			int colCount = sheet.getRow(0).getLastCellNum();
			// �������е�Ԫ��
			List<Object[]> records = new ArrayList<Object[]>();
			for (int i = 1; i < rowCount + 1; i++) {
				Row row = sheet.getRow(i);
				String fields[] = new String[colCount];
				for (int j = 0; j < colCount; j++) {
					Cell cell = row.getCell(j);
					// ��ǰ��û�б��Ϊj���д��ڣ��͸�ֵΪ�մ�
					if (j >= row.getLastCellNum() || cell == null) {
						fields[j] = "";
					} else {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						fields[j] = cell.getStringCellValue();
					}
				}
				records.add(fields);
			}
			// ת�����ݸ�ʽ
			Object[][] results = new Object[records.size()][];
			for (int i = 0; i < records.size(); i++) {
				results[i] = records.get(i);
			}
			return results;

		} catch (IOException e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			return null;
		}
	}
	/**
	 * ��CSV�ļ��ж�ȡ��������
	 * @return
	 */
	public static Object[][] getTestDataFromCSVFile(
			String filePath,
			String fileName
			){
		CSVReader csvReader;
		List<String[]> records =  new ArrayList<String[]>();
		try{
		FileInputStream fins = new FileInputStream(filePath + "\\" + fileName);
		//��װGBK�ĸ�ʽȥ��ȡ�ļ��� ----�ַ������뷽ʽ
		InputStreamReader gbReader = new InputStreamReader(fins,"UTF-8");
		
//		FileReader file = new FileReader(filePath + "\\" + fileName);
		csvReader = new CSVReader(gbReader);
		records = csvReader.readAll();
		csvReader.close();
		} catch(Exception e){
			e.printStackTrace();
			Log.error("Can not read csv file");
			
		}
		//���شӵڶ��п�ʼ������
		Object[][] results = new Object[records.size()-1][];
		for(int i=1;i<records.size();i++){
			results[i-1]=records.get(i);
		}
		return results;
	}
}


















