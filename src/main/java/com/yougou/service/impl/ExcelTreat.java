package com.yougou.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yougou.model.BookClassModel;
import com.yougou.model.BooksInfoModel;
import com.yougou.model.CourseInfoModel;
import com.yougou.model.UploadbooksModel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelTreat {
	
	
	protected static List<UploadbooksModel> bookUploadList(String excelPath) throws Exception{
		
		File file = new File(excelPath);
		Workbook book = Workbook.getWorkbook(file);
		List<UploadbooksModel> tempList= new ArrayList<UploadbooksModel>();
		Sheet[] sheets = book.getSheets();
		for (Sheet sheet : sheets) {
			//System.out.println("|||||||||||||||||||||||||||=" + sheet.getName());
			// 获得第一个工作表对象
			// Sheet sheet = book.getSheet(0);
			if (sheet != null) {

				int columnum = sheet.getColumns();// 得到列数
				int rownum = sheet.getRows();// 得到行数
				UploadbooksModel ul=null;
				for (int i = 1; i < rownum; i++) {

					ul=new UploadbooksModel();
					boolean bRet3=false;
					for (int j = 0; j < columnum; j++) {

						// 得到第一列第一行的单元格
						Cell cell1 = sheet.getCell(j, i);
						String result = cell1.getContents();
						
						//if(result==null||"".equals(result)){
							
							//System.out.println("NULL空数据========================================================");
							//break;
						//}
						result=result.trim();
						if(j==0){
							
							ul.setCourseName(result);
						}else if(j==1){
							
							//System.out.println(result + ","+i);
							if(result==null||"".equals(result)||"null".equals(result)){
								System.out.println("=========================null");
								bRet3=true;
								break;
							}
							
							ul.setBookName(result.trim());
						}else if(j==2){
							
							ul.setBookAuthor(result);
						}else if(j==3){
							
							ul.setPressTime(result);
						}else if(j==4){
							
							ul.setUserClass(result);
						}else if(j==5){
							
							ul.setUserClass(result);
						}else if(j==6){
							
							ul.setUserCount(result);
						}
					}
					
					if(!bRet3){
						
						tempList.add(ul);
					}
					//System.out.println("=====================");
				}
			}
		}

		book.close();
		file = null;
		return tempList;
	}
	
	/**
	 * 方法说明：生成保障计划的Excel
	 * 创建时间：2015-01-11 yinyf
	 * @param filePath
	 * @param bzjhList
	 * @return
	 */
	public static boolean writeExcelForBzjh(String filePath,List<UploadbooksModel> bzjhList,Map<String,BooksInfoModel> tempMap) {
		
		
		try {
			String[] title = { "教材或图名称", "主编", "出版社", "出版时间（版次）", "来源", "缺货数量","库存数量","使用数量" };
			// 获得开始时间
			long start = System.currentTimeMillis();
			// 输出的excel的路径
			// 创建Excel工作薄
			WritableWorkbook wwb;
			// 新建立一个jxl文件,即在d盘下生成testJXL.xls
			OutputStream os = new FileOutputStream(filePath);
			wwb = Workbook.createWorkbook(os);
			// 添加第一个工作表并设置第一个Sheet的名字
			WritableSheet sheet = wwb.createSheet("保障计划", 0);
			Label label;
            jxl.write.WritableFont wfont = new jxl.write.WritableFont(WritableFont.createFont("隶书"),18);  
            WritableCellFormat font = new WritableCellFormat(wfont);  
            
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
				// 在Label对象的子对象中指明单元格的位置和内容
				//label = new Label(i, 0, title[i]);
				label = new Label(i,0,title[i],font);  
				// label = new Label(i, 0, title[i], getHeader());
				// 将定义好的单元格添加到工作表中
				
				sheet.addCell(label);
				if(i==0){
					
					sheet.setColumnView(i, 	25);
				}else if(i==1){
					
					sheet.setColumnView(i, 	10);
				}else if(i==2){
					
					sheet.setColumnView(i, 	20);
				}else if(i==3){
					
					sheet.setColumnView(i, 	15);
				}else if(i==4){
					
					sheet.setColumnView(i, 	15);
				}else if(i==5){
					
					sheet.setColumnView(i, 	15);
				}else if(i==6){
					
					sheet.setColumnView(i, 	15);
				}else if(i==7){
					
					sheet.setColumnView(i, 	15);
				}
				
			}
			// 下面是填充数据

			if(bzjhList!=null&&bzjhList.size()>0){
				
				int i=1;
				for (UploadbooksModel ul:bzjhList) {

					label = new Label(0, i, ul.getBookName());
					sheet.addCell(label);
					label = new Label(1, i, ul.getBookAuthor());
					sheet.addCell(label);
					label = new Label(2, i, ul.getBookPress());
					sheet.addCell(label);
					label = new Label(3, i, ul.getPressTime());
					sheet.addCell(label);
					
					String bigType="";
					
					if("1".equals(ul.getBigType())){
						
						bigType="外购";
					}else if("2".equals(ul.getBigType())){
						
						bigType="印刷";
					}else if("3".equals(ul.getBigType())){
						
						bigType="配发";
					}else if("4".equals(ul.getBigType())){
						
						bigType="调拨";
					}
					
					label = new Label(4, i, bigType);
					sheet.addCell(label);
					
					String bookNameTemp= ul.getBookName();
					BooksInfoModel bl=null;
					
					if(tempMap!=null&&tempMap.size()>0){
						
						bl=tempMap.get(bookNameTemp);
					}
					
					String kcCountTemp = "0";
					
					if(bl!=null){
						
						kcCountTemp = bl.getKcCount();
					}
					
					int iKcCount=0;
					
					if(kcCountTemp!=null&&!"".equals(kcCountTemp)){
						
						iKcCount= Integer.parseInt(kcCountTemp);
					}else{
						
						kcCountTemp="0";
					}
					
					ul.setKcCount(kcCountTemp);
					String userCount=ul.getUserCount();
					int iUserCount=0;
					if(userCount!=null&&!"".equals(userCount)){
						
						iUserCount= Integer.parseInt(userCount);
					}
					
					int iQueCount =iUserCount-iKcCount;
					
					label = new Label(5, i, iQueCount+"");
					sheet.addCell(label);
					
					label = new Label(6, i, iKcCount+"");
					sheet.addCell(label);
					
					label = new Label(7, i, iUserCount+"");
					sheet.addCell(label);
					
					i++;
				}
			}

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			//System.out.println("writeExcelForBzjh---sucss---");
            return true;			
		} catch (Exception e) {
			System.out.println("writeExcelForBzjh---出现异常---");
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 方法说明：生成发放单Excel
	 * 创建时间：2015-01-11 yinyf
	 * @param filePath
	 * @param bzjhList
	 * @return
	 */
	public static boolean writeExcelForFfd(String filePath,List<BookClassModel> ffdList) {
		
		
		try {
			String[] title = { "编号","课程名称", "教材名称", "出版社", "作者", "出版时间", "领取人","领取时间" };
			// 输出的excel的路径
			// 创建Excel工作薄
			WritableWorkbook wwb;
			// 新建立一个jxl文件,即在d盘下生成testJXL.xls
			OutputStream os = new FileOutputStream(filePath);
			wwb = Workbook.createWorkbook(os);
			// 添加第一个工作表并设置第一个Sheet的名字
			WritableSheet sheet = wwb.createSheet("发放单", 0);
			//合并第一行     startColNum, startRowNum, endColNum, endRowNum
			sheet.mergeCells(0, 0, 7, 0);
			Label label;
            jxl.write.WritableFont wfont = new jxl.write.WritableFont(WritableFont.createFont("隶书"),18);  
            WritableCellFormat font = new WritableCellFormat(wfont);  
            font.setAlignment(Alignment.CENTRE);//把水平对齐方式指定为居中 
            String titleName = "发放单";
            
            if(ffdList!=null&&ffdList.size()>0){
            	
            	titleName= ffdList.get(0).getClassName()+"发放单";
            }
            
            label = new Label(0,0,titleName,font);  
            sheet.addCell(label);
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
				// 在Label对象的子对象中指明单元格的位置和内容
				//label = new Label(i, 0, title[i]);
				label = new Label(i,1,title[i],font);  
				// label = new Label(i, 0, title[i], getHeader());
				// 将定义好的单元格添加到工作表中
				
				sheet.addCell(label);
				if(i==0){
					
					sheet.setColumnView(i, 	10);
				}else if(i==1){
					
					sheet.setColumnView(i, 	20);
				}else if(i==2){
					
					sheet.setColumnView(i, 	20);
				}else if(i==3){
					
					sheet.setColumnView(i, 	25);
				}else if(i==4){
					
					sheet.setColumnView(i, 	20);
				}else if(i==5){
					
					sheet.setColumnView(i, 	20);
				}else if(i==6){
					
					sheet.setColumnView(i, 	20);
				}else if(i==7){
					
					sheet.setColumnView(i, 	20);
				}
			}
			//  { "编号","课程名称", "教材名称", "出版社", "作者", "出版时间", "领取人","领取时间" };

			if(ffdList!=null&&ffdList.size()>0){
				
				int i=2 ;
				for (BookClassModel ul:ffdList) {

					label = new Label(0, i, ""+(i-1));
					sheet.addCell(label);
					
					label = new Label(1, i, ul.getCourseName());
					sheet.addCell(label);
					label = new Label(2, i, ul.getBookName());
					sheet.addCell(label);
					label = new Label(3, i, ul.getBookPress());
					sheet.addCell(label);
					label = new Label(4, i, ul.getBookAuthor());
					sheet.addCell(label);
					label = new Label(5, i, ul.getPressTime());
					sheet.addCell(label);
					label = new Label(6, i,"");
					sheet.addCell(label);
					label = new Label(7, i, "");
					sheet.addCell(label);
				
					i++;
				}
			}

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			//System.out.println("writeExcelForBzjh---sucss---");
            return true;			
		} catch (Exception e) {
			System.out.println("writeExcelForFfd---出现异常---");
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 方法说明：生成教材信息Excel
	 * 创建时间：2015-01-11 yinyf
	 * @param filePath
	 * @param bzjhList
	 * @return
	 */
	public static boolean writeExcelForBook(String filePath,List<BooksInfoModel> bookList,Map<String,CourseInfoModel> tempMap) {
		
		
		try {
			String[] title = { "教材名称", "所属课程", "出版社", "作者", "出版时间", "来源","是否保密","是否回收","库号","架号","层号","库存数","添加时间" };
			// 获得开始时间
			long start = System.currentTimeMillis();
			// 输出的excel的路径
			// 创建Excel工作薄
			WritableWorkbook wwb;
			// 新建立一个jxl文件,即在d盘下生成testJXL.xls
			OutputStream os = new FileOutputStream(filePath);
			wwb = Workbook.createWorkbook(os);
			// 添加第一个工作表并设置第一个Sheet的名字
			WritableSheet sheet = wwb.createSheet("教材信息", 0);
			Label label;
            jxl.write.WritableFont wfont = new jxl.write.WritableFont(WritableFont.createFont("隶书"),18);  
            WritableCellFormat font = new WritableCellFormat(wfont);  
            
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
				// 在Label对象的子对象中指明单元格的位置和内容
				//label = new Label(i, 0, title[i]);
				label = new Label(i,0,title[i],font);  
				// label = new Label(i, 0, title[i], getHeader());
				// 将定义好的单元格添加到工作表中
				//String[] title = { "教材名称", "所属课程", "出版社", "作者", "出版时间", "来源","是否保密","是否回收","库号","架号","层号","库存数","添加时间" };
				sheet.addCell(label);
				if(i==0){
					
					sheet.setColumnView(i, 	25);
				}else if(i==1){
					
					sheet.setColumnView(i, 	10);
				}else if(i==2){
					
					sheet.setColumnView(i, 	20);
				}else if(i==3){
					
					sheet.setColumnView(i, 	15);
				}else if(i==4){
					
					sheet.setColumnView(i, 	10);
				}else if(i==5){
					
					sheet.setColumnView(i, 	10);
				}else if(i==6){
					
					sheet.setColumnView(i, 	10);
				}else if(i==7){
					
					sheet.setColumnView(i, 	10);
				}else if(i==8){
					
					sheet.setColumnView(i, 	10);
				}else if(i==9){
					
					sheet.setColumnView(i, 	10);
				}else if(i==10){
					
					sheet.setColumnView(i, 	10);
				}else if(i==11){
					
					sheet.setColumnView(i, 	10);
				}else if(i==12){
					
					sheet.setColumnView(i, 	10);
				}
			}
			//String[] title = { "教材名称", "所属课程", "出版社", "作者", "出版时间", "5来源","是否保密","7是否回收","8库号","架号","层号","库存数","添加时间" };

			if(bookList!=null&&bookList.size()>0){
				
				int i=1;
				for (BooksInfoModel ul:bookList) {

					label = new Label(0, i, ul.getBookName());
					sheet.addCell(label);
					
					if(tempMap!=null&&tempMap.size()>0){
						String cId = ul.getCourseId();
						CourseInfoModel cl = tempMap.get(cId);
						
						if(cl!=null){
							
							label = new Label(1, i, cl.getCourseName());
						}else{
							
							label = new Label(1, i, "");
						}
					}else{
						
						label = new Label(1, i, "");
					}
					
					
					sheet.addCell(label);
					label = new Label(2, i, ul.getBookPress());
					sheet.addCell(label);
					label = new Label(3, i, ul.getBookAuthor());
					sheet.addCell(label);
					label = new Label(4, i, ul.getPressTime());
					sheet.addCell(label);
					
					String bigType =ul.getBigType();
					if("1".equals(bigType)){
						
						label = new Label(5, i, "外购");
					}else if("2".equals(bigType)){
						
						label = new Label(5, i, "印刷");
					}else if("3".equals(bigType)){
						
						label = new Label(5, i, "配发");
					}else{
						
						label = new Label(5, i, "调拨");
					}
					
					sheet.addCell(label);
					
					String baoMi = ul.getBaoMi();
					
					if("1".equals(baoMi)){
						
						label = new Label(6, i, "保密");
					}else{
						
						label = new Label(6, i, "不保密");
					}
					
					
					sheet.addCell(label);
					
					String huiShou = ul.getHuiShou();
					
					if("1".equals(huiShou)){
						
						label = new Label(7, i, "是");
					}else{
						
						label = new Label(7, i, "否");
					}
					
					sheet.addCell(label);
					label = new Label(8, i, ul.getKuHao());
					sheet.addCell(label);
					label = new Label(9, i, ul.getJiaHao());
					sheet.addCell(label);
					label = new Label(10, i, ul.getChengHao());
					sheet.addCell(label);
					label = new Label(11, i, ul.getKcCount());
					sheet.addCell(label);
					label = new Label(12, i, ul.getInsertTime());
					sheet.addCell(label);
					i++;
				}
			}

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			//System.out.println("writeExcelForBzjh---sucss---");
            return true;			
		} catch (Exception e) {
			System.out.println("writeExcelForBook---出现异常---");
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 方法说明：生成教材信息Excel
	 * 创建时间：2015-01-11 yinyf
	 * @param filePath
	 * @param bzjhList
	 * @return
	 */
	public static boolean writeExcelForUseBook(String filePath,List<UploadbooksModel> useList) {
		
		
		try {
			String[] title = {"教材名称", "出版社", "作者", "出版时间", "来源","库存数","使用数","缺货数","使用班级","上传时间"};
			// 获得开始时间
			long start = System.currentTimeMillis();
			// 输出的excel的路径
			// 创建Excel工作薄
			WritableWorkbook wwb;
			// 新建立一个jxl文件,即在d盘下生成testJXL.xls
			OutputStream os = new FileOutputStream(filePath);
			wwb = Workbook.createWorkbook(os);
			// 添加第一个工作表并设置第一个Sheet的名字
			WritableSheet sheet = wwb.createSheet("使用计划", 0);
			Label label;
            jxl.write.WritableFont wfont = new jxl.write.WritableFont(WritableFont.createFont("隶书"),18);  
            WritableCellFormat font = new WritableCellFormat(wfont);  
            
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
				// 在Label对象的子对象中指明单元格的位置和内容
				//label = new Label(i, 0, title[i]);
				label = new Label(i,0,title[i],font);  
				// label = new Label(i, 0, title[i], getHeader());
				// 将定义好的单元格添加到工作表中
				//String[] title = { "教材名称", "所属课程", "出版社", "作者", "出版时间", "来源","是否保密","是否回收","库号","架号","层号","库存数","添加时间" };
				sheet.addCell(label);
				if(i==0){
					
					sheet.setColumnView(i, 	25);
				}else if(i==1){
					
					sheet.setColumnView(i, 	10);
				}else if(i==2){
					
					sheet.setColumnView(i, 	20);
				}else if(i==3){
					
					sheet.setColumnView(i, 	15);
				}else if(i==4){
					
					sheet.setColumnView(i, 	10);
				}else if(i==5){
					
					sheet.setColumnView(i, 	10);
				}else if(i==6){
					
					sheet.setColumnView(i, 	10);
				}else if(i==7){
					
					sheet.setColumnView(i, 	10);
				}else if(i==8){
					
					sheet.setColumnView(i, 	10);
				}else if(i==9){
					
					sheet.setColumnView(i, 	10);
				}
			}
			//String[] title = {"教材名称", "出版社", "作者", "出版时间", "来源","库存数","使用数","缺货数","使用班级","上传时间"};

			if(useList!=null&&useList.size()>0){
				
				int i=1;
				for (UploadbooksModel ul:useList) {

					label = new Label(0, i, ul.getBookName());
					sheet.addCell(label);
					label = new Label(1, i, ul.getBookPress());
					sheet.addCell(label);					
					label = new Label(2, i, ul.getBookAuthor());
					sheet.addCell(label);
					label = new Label(3, i, ul.getPressTime());
					sheet.addCell(label);
					
					String bigType =ul.getBigType();
					if("1".equals(bigType)){
						
						label = new Label(4, i, "外购");
					}else if("2".equals(bigType)){
						
						label = new Label(4, i, "印刷");
					}else if("3".equals(bigType)){
						
						label = new Label(4, i, "配发");
					}else{
						
						label = new Label(4, i, "调拨");
					}//String[] title = {"教材名称", "出版社", "作者", "出版时间", "来源","5库存数","使用数","缺货数","使用班级","上传时间"};
					sheet.addCell(label);
					label = new Label(5, i, ul.getKcCount());
					sheet.addCell(label);
					label = new Label(6, i, ul.getUserCount());
					sheet.addCell(label);
					label = new Label(7, i, ul.getQueCount());
					sheet.addCell(label);
					label = new Label(8, i, ul.getUserClass());
					sheet.addCell(label);
					label = new Label(9, i, ul.getInsertTime());
					sheet.addCell(label);
					
					i++;
				}
			}

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			//System.out.println("writeExcelForBzjh---sucss---");
            return true;			
		} catch (Exception e) {
			System.out.println("writeExcelForUseBook---出现异常---");
			e.printStackTrace();
		}
		
		return false;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			
			bookUploadList("D:\\tomcat-6\\webapps\\upload\\20150108231045.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
