package com.cd.cdwoo.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.ResourceBundle;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * 将文件转成swf格式
 * 
 * @author Administrator
 * 
 */
public class ConvertSwf {

	/**
	 * 入口方法-通过此方法转换文件至swf格式
	 * @param filePath	上传文件所在文件夹的绝对路径
	 * @param dirName 转换工具所在路径信息
	 * @param fileName	文件名称
	 * @return			生成swf文件名
	 */
	public String beginConvert(String filePath, String dirName, String fileName) {
		final String DOC = ".doc";
		final String DOCX = ".docx";
		final String XLS = ".xls";
		final String XLSX = ".xlsx";
		final String PDF = ".pdf";
		final String SWF = ".swf";
		
		//windows下的写法
		final String TOOL = "pdf2swf.exe";
		
		//linux下的写法
		//final String TOOL = "";
		
		String outFile = "";
		String fileNameOnly = "";
		String fileExt = "";
		if (null != fileName && fileName.indexOf(".") > 0) {
			int index = fileName.indexOf(".");
			fileNameOnly = fileName.substring(0, index);
			fileExt = fileName.substring(index).toLowerCase();
		}
		String inputFile = filePath + File.separator + fileName;
		String outputFile = "";

		//如果是office文档，先转为pdf文件
		if (fileExt.equals(DOC) || fileExt.equals(DOCX) || fileExt.equals(XLS)
				|| fileExt.equals(XLSX)) {
			outputFile = filePath + File.separator + fileNameOnly + PDF;
			office2PDF(inputFile, outputFile);
			inputFile = outputFile;
			fileExt = PDF;
		}

		if (fileExt.equals(PDF)) {
			String toolFile = dirName + File.separator + TOOL;
			outputFile = filePath + File.separator + fileNameOnly + SWF;
			convertPdf2Swf(inputFile, outputFile, toolFile);
			outFile = outputFile;
		}
		return outFile;
	}

	/**
	 * 将pdf文件转换成swf文件
	 * @param sourceFile pdf文件绝对路径
	 * @param outFile	 swf文件绝对路径
	 * @param toolFile	 转换工具绝对路径
	 */
	private void convertPdf2Swf(String sourceFile, String outFile,
			String toolFile) {
		String command = toolFile + " \"" + sourceFile + "\" -o  \"" + outFile
				+ "\" -s flashversion=9 ";
		String commandLinux = "/usr/local/swftools/bin/pdf2swf -s languagedir=/usr/local/xpdf-chinese-simplified -T 9 -s poly2bitmap -s zoom=150 -s flashversion=9 "+sourceFile+" -o "+outFile;
		try {
			Process process = Runtime.getRuntime().exec(commandLinux);
			System.out.println(loadStream(process.getInputStream()));
			System.err.println(loadStream(process.getErrorStream()));
			System.out.println(loadStream(process.getInputStream()));
			System.out.println("###--Msg: swf 转换成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * office文档转pdf文件
	 * @param sourceFile	office文档绝对路径
	 * @param destFile		pdf文件绝对路径
	 * @return
	 */
	private static int office2PDF(String sourceFile, String destFile) {
		/*ResourceBundle rb = ResourceBundle.getBundle("com/cd/cdwoo/conf/OpenOfficeService.properties");
		String OpenOffice_HOME = rb.getString("OO_HOME");
		String host_Str = rb.getString("oo_host");
		String port_Str = rb.getString("oo_port");*/
	  String OpenOffice_HOME = PropertiesReaderUtils.getProperties("com/cd/cdwoo/conf/OpenOfficeService.properties", "OO_HOME");
		String host_Str = PropertiesReaderUtils.getProperties("com/cd/cdwoo/conf/OpenOfficeService.properties", "oo_host");
		String port_Str = PropertiesReaderUtils.getProperties("com/cd/cdwoo/conf/OpenOfficeService.properties", "oo_port");
	  try {
			File inputFile = new File(sourceFile);
			if (!inputFile.exists()) {
				return -1; // 找不到源文件 
			}
			// 如果目标路径不存在, 则新建该路径  
			File outputFile = new File(destFile);
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}
			// 启动OpenOffice的服务  
			String command = OpenOffice_HOME
					+ "/program/soffice -headless -accept=\"socket,host="
					+ host_Str + ",port=" + port_Str + ";urp;\"";
			System.out.println("###\n" + command);
			Process pro = Runtime.getRuntime().exec(command);
			// 连接openoffice服务
			OpenOfficeConnection connection = new SocketOpenOfficeConnection(
					host_Str, Integer.parseInt(port_Str));
			connection.connect();
			// 转换 
			DocumentConverter converter = new OpenOfficeDocumentConverter(
					connection);
			converter.convert(inputFile, outputFile);

			// 关闭连接和服务
			connection.disconnect();
			pro.destroy();

			return 0;
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到！");
			e.printStackTrace();
			return -1;
		} catch (ConnectException e) {
			System.out.println("OpenOffice服务监听异常！");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	static String loadStream(InputStream in) throws IOException{
		int ptr = 0;
		in = new BufferedInputStream(in);
		StringBuffer buffer = new StringBuffer();
		
		while ((ptr=in.read())!= -1){
			buffer.append((char)ptr);
		}
		return buffer.toString();
	}

  public static void main(String[] args) {
    office2PDF("E:\\cdwooVirtualPath\\document\\0a73fb7c-7dc7-4ff7-bb23-ee41aa6c0798.docx", "E:\\cdwooVirtualPath\\document\\0a73fb7c-7dc7-4ff7-bb23-ee41aa6c0798.pdf");
  }
}
