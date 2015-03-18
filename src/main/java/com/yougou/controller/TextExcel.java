package com.yougou.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class TextExcel {

	public static void main2(String[] args) {
		// TODO Auto-generated method stub

		try {
			
			File file=new File("D:\\tomcat-6\\webapps\\upload\\20150108231045.xls");
			Workbook book = Workbook.getWorkbook(file);
		   
			Sheet[] sheets = book.getSheets(); 
			for(Sheet sheet : sheets){ 
				System.out.println("|||||||||||||||||||||||||||="+sheet.getName());
				// 获得第一个工作表对象
				//Sheet sheet = book.getSheet(0);
				if(sheet!=null){
					
			        int columnum = sheet.getColumns();// 得到列数
			        int rownum = sheet.getRows();// 得到行数
					for(int i=1;i<rownum;i++){
						
						for(int j=0;j<columnum;j++){
							
							// 得到第一列第一行的单元格
							Cell cell1 = sheet.getCell(j, i);
							String result = cell1.getContents();
							System.out.println(result+",");
						}
						System.out.println("=====================");
					}
				}
			}
			
			book.close();
			file=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
		String filePath = "d:\\testJXL.xls";
		writeExcelForBzjh(filePath);
	}
	
	public static void writeExcel(){
		   String[] title = {"编号","产品名称","产品价格","产品数量","生产日期","产地","是否出口"};  
		         try {  
		             // 获得开始时间  
		             long start = System.currentTimeMillis();  
		             // 输出的excel的路径  
		             String filePath = "d:\\testJXL.xls";  
		              // 创建Excel工作薄  
		             WritableWorkbook wwb;  
		              // 新建立一个jxl文件,即在d盘下生成testJXL.xls  
		             OutputStream os = new FileOutputStream(filePath);  
		             wwb=Workbook.createWorkbook(os);   
		             // 添加第一个工作表并设置第一个Sheet的名字  
		             WritableSheet sheet = wwb.createSheet("产品清单", 0);  
		             Label label;  
		             for(int i=0;i<title.length;i++){  
		                 // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z  
		                 // 在Label对象的子对象中指明单元格的位置和内容  
		                 label = new Label(i,0,title[i]);
		                 //label = new Label(i, 0, title[i], getHeader());
		                 // 将定义好的单元格添加到工作表中  
		                 sheet.addCell(label);  
		             }  
		             // 下面是填充数据  
		              /*  
		               * 保存数字到单元格，需要使用jxl.write.Number
		               * 必须使用其完整路径，否则会出现错误
		              * */
		            // 填充产品编号  
		             jxl.write.Number number = new jxl.write.Number(0,1,20071001);  
		             sheet.addCell(number);  
		             // 填充产品名称  
		              label = new Label(1,1,"金鸽瓜子");  
		             sheet.addCell(label);  
		              /*
		             * 定义对于显示金额的公共格式
		               * jxl会自动实现四舍五入
		              * 例如 2.456会被格式化为2.46,2.454会被格式化为2.45
		              * */
		             jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#,###.00");  
		             jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(nf);  
		              // 填充产品价格  
		             jxl.write.Number nb = new jxl.write.Number(2,1,200000.45,wcf);  
		             sheet.addCell(nb);  
		             // 填充产品数量  
		              jxl.write.Number numb = new jxl.write.Number(3,1,200);  
		             sheet.addCell(numb);  
		             /*
		               * 定义显示日期的公共格式
		             * 如:yyyy-MM-dd hh:mm
		              * */
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		             String newdate = sdf.format(new Date());  
		             // 填充出产日期  
		             label = new Label(4,1,newdate);  
		             sheet.addCell(label);  
		             // 填充产地  
		              label = new Label(5,1,"陕西西安");  
		             sheet.addCell(label);  
		             /*
		              * 显示布尔值
		              * */
		             jxl.write.Boolean bool = new jxl.write.Boolean(6,1,true);  
		             sheet.addCell(bool);  
		              /*
		              * 合并单元格
		              * 通过writablesheet.mergeCells(int x,int y,int m,int n);来实现的
		               * 表示将从第x+1列，y+1行到m+1列，n+1行合并
		              *  
		              * */
		             sheet.mergeCells(0,3,2,3);  
		             label = new Label(0,3,"合并了三个单元格");  
		             sheet.addCell(label);  
		             /*
		               *  
		               * 定义公共字体格式
		               * 通过获取一个字体的样式来作为模板
		              * 首先通过web.getSheet(0)获得第一个sheet
		              * 然后取得第一个sheet的第二列，第一行也就是"产品名称"的字体  
		             * */
		             CellFormat cf = wwb.getSheet(0).getCell(1, 0).getCellFormat();  
		             WritableCellFormat wc = new WritableCellFormat();  
		             // 设置居中  
		              wc.setAlignment(Alignment.CENTRE);  
		              // 设置边框线  
		            wc.setBorder(Border.ALL, BorderLineStyle.THIN);  
		             // 设置单元格的背景颜色  
		            wc.setBackground(jxl.format.Colour.RED);  
		             label = new Label(1,5,"字体",wc);  
		            sheet.addCell(label);  
		 
		             // 设置字体  
		            jxl.write.WritableFont wfont = new jxl.write.WritableFont(WritableFont.createFont("隶书"),20);  
		             WritableCellFormat font = new WritableCellFormat(wfont);  
		             label = new Label(2,6,"隶书",font);  
		              sheet.addCell(label);  
		               
		           // 写入数据  
		            wwb.write();  
		             // 关闭文件  
		             wwb.close();  
		            long end = System.currentTimeMillis();  
		             System.out.println("----完成该操作共用的时间是:"+(end-start)/1000);  
		         } catch (Exception e) {  
		             System.out.println("---出现异常---");  
		              e.printStackTrace();  
		        }  
		 }
	
	public static void writeExcelForBzjh(String filePath) {
		String[] title = { "教材或图名称", "主编", "出版社", "出版时间（版次）", "来源", "数量" };
		try {
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
			}
			// 下面是填充数据

			for (int i = 1; i <= 20; i++) {

				label = new Label(0, i, "金鸽瓜子");
				sheet.addCell(label);
				label = new Label(1, i, "金鸽瓜子");
				sheet.addCell(label);
				label = new Label(2, i, "金鸽瓜子");
				sheet.addCell(label);
				label = new Label(3, i, "金鸽瓜子");
				sheet.addCell(label);
				label = new Label(4, i, "金鸽瓜子");
				sheet.addCell(label);
				label = new Label(5, i, "金鸽瓜子");
				sheet.addCell(label);
			}

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			long end = System.currentTimeMillis();
			System.out.println("----完成该操作共用的时间是:" + (end - start) / 1000);
		} catch (Exception e) {
			System.out.println("---出现异常---");
			e.printStackTrace();
		}
	}
}
