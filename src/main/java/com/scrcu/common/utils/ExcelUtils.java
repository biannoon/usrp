
package com.scrcu.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述: EXCEL工具类
 *
 * @创建人: hepengfei
 * @创建时间: 2019-9-4
 */

public class ExcelUtils {
	public  static Logger log = LoggerFactory.getLogger(SftpUtil.class);

	//默认单元格为数字时格式  
	private static final DecimalFormat df = new DecimalFormat("0");
	//格式化日期  
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	//格式化数字
	private static final DecimalFormat nf = new DecimalFormat("0.00");
	
	public static final boolean createExcel(File file, List<List<?>> listList)
			throws FileNotFoundException, IOException, Exception {
		boolean bool = false;
		if(file.getName().toLowerCase().endsWith(".xls")){
			bool = createXLS(file, listList);
		}else if(file.getName().toLowerCase().endsWith(".xlsx")){
			bool = createXLSX(file, listList);
		}
		return bool;
	}
	
	public static final List<List<Object>> readExcel(File file, int ignoreRows)
			throws FileNotFoundException, IOException {
		List<List<Object>> dataList = null;
		if(file.getName().toLowerCase().endsWith(".xls")){
			dataList = readXLS(file, ignoreRows);
		}else if(file.getName().toLowerCase().endsWith(".xlsx")){
			dataList = readXLSX(file, ignoreRows);
		}
		return dataList;
	}
	/**
	 * 读取后缀为.xls的Excel文件
	 * @param file 读取数据的源Excel
	 * @param ignoreRows 读取数据忽略的行头数
	 * @return 读出的Excel中数据的内容
	 * @throws Exception
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @创建人: hepengfei
	 * @创建时间: 2019-9-4
	 */

	public static final List<List<Object>> readXLS(File file, int ignoreRows)
			throws FileNotFoundException, IOException {
		List<List<Object>> dataList = new ArrayList<List<Object>>();
		List<Object> colList = null;
		Object value = null;
		int rowSize = 0;
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		POIFSFileSystem fs = new POIFSFileSystem(bis);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFCell cell = null;
		for(int i = ignoreRows; i < sheet.getPhysicalNumberOfRows(); i++){
			HSSFRow row = sheet.getRow(i);
			colList = new ArrayList<Object>();
			if(row == null){
				if(i != sheet.getPhysicalNumberOfRows()){//判断是否是最后一行
					dataList.add(colList);
				}
				continue;
			}else{
				rowSize ++;
			}
			for(int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++){
				cell = row.getCell(j);
				if(cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){//当该单元格为空
					if(j != row.getLastCellNum()){//判断是否是该行中最后一个单元格
						colList.add("");
					}
					continue;
				}
				switch(cell.getCellType()){
					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						if ("@".equals(cell.getCellStyle().getDataFormatString())){
							value = df.format(cell.getNumericCellValue());
						}else if("General".equals(cell.getCellStyle().getDataFormatString())){
							value = nf.format(cell.getNumericCellValue());
						}else{
							value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						}
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						if(!cell.getStringCellValue().equals("")){
							value = cell.getStringCellValue();
						}else{
							value = cell.getStringCellValue() + "";
						}
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						value = Boolean.valueOf(cell.getBooleanCellValue());
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						value = "";
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					default:
						value = cell.toString();
				}
				colList.add(value);
			}
			dataList.add(colList);
		}
		return dataList;
	}
	/**
	 * 生成后缀为.xlsx的Excel文件
	 * @param file
	 * @return
	 * @throws Exception
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @创建人: hepengfei
	 * @创建时间: 2019-9-4
	 */

	public static final boolean createXLSX(File file, List<List<?>> listList)
			throws Exception, FileNotFoundException, IOException {
		boolean bool = false;
		if(file.exists()){
			file.delete();
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		int index = 0;
		for(List<?> list : listList){
			XSSFRow row = sheet.createRow(index);
			for(int i = 0; i < list.size(); i++){
				XSSFCell cell = row.createCell(i);
				cell.setCellValue(list.get(i).toString());
			}
			index ++;
		}
		try{
			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);
			fos.close();
			wb.close();
			bool = true;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return bool;
	}
	/**
	 * 读取后缀为.xlsx的Excel文件
	 * @param file 读取数据的源Excel
	 * @param ignoreRows 读取数据忽略的行头数
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @创建人: hepengfei
	 * @创建时间: 2019-9-4
	 */

	public static final List<List<Object>> readXLSX(File file, int ignoreRows)
			throws FileNotFoundException, IOException {
		List<List<Object>> dataList = new ArrayList<List<Object>>();
		List<Object> colList = null;
		Object value = null;
		int rowSize = 0;
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		XSSFWorkbook wb = new XSSFWorkbook(bis);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFCell cell = null;
		for(int i = ignoreRows; i < sheet.getPhysicalNumberOfRows(); i++){
			XSSFRow row = sheet.getRow(i);
			colList = new ArrayList<Object>();
			if(row == null){
				if(i != sheet.getPhysicalNumberOfRows()){//判断是否是最后一行
					dataList.add(colList);
				}
				continue;
			}else{
				rowSize ++;
			}
			for(int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++){
				cell = row.getCell(j);
				if(cell == null || cell.getCellType() == XSSFCell.CELL_TYPE_BLANK){//当该单元格为空
					if(j != row.getLastCellNum()){//判断是否是该行中最后一个单元格
						colList.add("");
					}
					continue;
				}
				switch(cell.getCellType()){
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if ("@".equals(cell.getCellStyle().getDataFormatString())){
							value = df.format(cell.getNumericCellValue());
						}else if("General".equals(cell.getCellStyle().getDataFormatString())){
							value = nf.format(cell.getNumericCellValue());
						}else{
							value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						}
						break;
					case XSSFCell.CELL_TYPE_FORMULA:
						if(!cell.getStringCellValue().equals("")){
							value = cell.getStringCellValue();
						}else{
							value = cell.getStringCellValue() + "";
						}
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						value = Boolean.valueOf(cell.getBooleanCellValue());
						break;
					case XSSFCell.CELL_TYPE_ERROR:
						value = "";
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					default:
						value = cell.toString();
				}
				colList.add(value);
			}
			dataList.add(colList);
		}
		return dataList;
	}
	/**
	 * 生成后缀为.xls的Excel文件
	 * @param file
	 * @return
	 * @throws Exception
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @创建人: hepengfei
	 * @创建时间: 2019-9-4
	 */
	public static final boolean createXLS(File file, List<List<?>> listList)
			throws Exception, FileNotFoundException, IOException {
		boolean bool = false;
		if(file.exists()){
			file.delete();
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		int index = 0;
		for(List<?> list : listList){
			HSSFRow row = sheet.createRow(index);
			for(int i = 0; i < list.size(); i++){
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(list.get(i).toString());
			}
			index ++;
		}
		try{
			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);
			fos.close();
			bool = true;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return bool;
	}
}
