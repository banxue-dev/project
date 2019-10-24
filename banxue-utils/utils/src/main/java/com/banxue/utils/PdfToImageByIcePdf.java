package com.banxue.utils;


import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import javax.imageio.ImageIO;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.GraphicsRenderingHints;

import com.itextpdf.text.pdf.PdfReader;

import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.rendering.PDFRenderer;


 
/**
作者：fengchase
时间：2019年7月2日
文件：PdfToImageByIcePdf.java
项目：utils
*/
public class PdfToImageByIcePdf {
    public static void main(final String[] args) {
		pdf2Image("C:\\Users\\fengchaseyou\\Desktop\\pdftoimg\\test.pdf", "C:\\Users\\fengchaseyou\\Desktop\\pdftoimg", 80);
       /* String filePath = "C:\\Users\\fengchaseyou\\Desktop\\pdftoimg\\test2.pdf";
        List<String> imageList = getPdfToImage(filePath);
        Iterator<String> iterator = imageList.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }*/
    }
    //返回生成图片的路径
    public static List<String> getPdfToImage(String filePath) {
        String fileName = filePath.substring(0,filePath.lastIndexOf("."));//获取去除后缀的文件路径
        List<String> list = new ArrayList<>();
        String imagePath;
        Document document = new Document();
        try{
            document.setFile(filePath);
        }catch (Exception e){
            e.printStackTrace();
        }
 
        float scale = 5f;// 缩放比例
        float rotation = 0f;// 旋转角度
 
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage image = (BufferedImage) document.getPageImage(i,
                    GraphicsRenderingHints.SCREEN,
                    org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation,
                    scale);
            RenderedImage rendImage = image;
            try {
                int n = i + 1;
                File f = new File(fileName);
                if(!f.exists()){
                    f.mkdir();
                }
                imagePath = fileName + "/image"+ n + ".png";//生成图片的路径
                File file = new File(imagePath);
//                ImageIO.write(rendImage, "jpg", file);
                // 这里png作用是：格式是jpg但有png清晰度
                 ImageIO.write(rendImage, "png", file);
                list.add(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.flush();
        }
        document.dispose();
        return list;
    }
    /***
	 * PDF文件转PNG图片，全部页数
	 * 
	 * @param PdfFilePath pdf完整路径
	 * @param imgFilePath 图片存放的文件夹
	 * @param dpi dpi越大转换后越清晰，相对转换速度越慢
	 * @return
	 */
 
	public static void pdf2Image(String PdfFilePath, String dstImgFolder, int dpi) {
 
		File file = new File(PdfFilePath);
 
		PDDocument pdDocument;
 
		try {
 
			String imgPDFPath = file.getParent();
 
			int dot = file.getName().lastIndexOf('.');
 
			String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
 
			String imgFolderPath = null;
 
			if (dstImgFolder.equals("")) {
 
				imgFolderPath = imgPDFPath + File.separator + imagePDFName;// 获取图片存放的文件夹路径
 
			} else {
 
				imgFolderPath = dstImgFolder + File.separator + imagePDFName;
 
			}
 
 
 
			if (createDirectory(imgFolderPath)) {
 
 
 
				pdDocument = PDDocument.load(file);
 
				PDFRenderer renderer = new PDFRenderer(pdDocument);
 
				/* dpi越大转换后越清晰，相对转换速度越慢 */
 
				PdfReader reader = new PdfReader(PdfFilePath);
 
				int pages = reader.getNumberOfPages();
 
				StringBuffer imgFilePath = null;
 
				for (int i = 0; i < pages; i++) {
 
					String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
 
					imgFilePath = new StringBuffer();
 
					imgFilePath.append(imgFilePathPrefix);
 
					imgFilePath.append("_");
 
					imgFilePath.append(String.valueOf(i + 1));
 
					imgFilePath.append(".png");
 
					File dstFile = new File(imgFilePath.toString());
 
					BufferedImage image = renderer.renderImageWithDPI(i, dpi);
 
					ImageIO.write(image, "png", dstFile);
 
				}
 
				System.out.println("PDF文档转PNG图片成功！");
 
 
 
			} else {
 
				System.out.println("PDF文档转PNG图片失败：" + "创建" + imgFolderPath + "失败");
 
			}
 
 
 
		} catch (IOException e) {
 
			e.printStackTrace();
 
		}
 
	}
 
 
 
	private static boolean createDirectory(String folder) {
 
		File dir = new File(folder);
 
		if (dir.exists()) {
 
			return true;
 
		} else {
 
			return dir.mkdirs();
 
		}
 
	}
    
}