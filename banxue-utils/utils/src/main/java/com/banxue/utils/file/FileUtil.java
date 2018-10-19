package com.banxue.utils.file;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

/**
 * 作者：fengchase 时间：2018年10月18日 文件：FileUtil.java 项目：utils
 */
public class FileUtil {
	
	@Value("${file.path}")
	private static String uploadPath;
	/**
	 * 工具类：上传文件：不改名字
	 */
	public static String saveFile2(HttpServletRequest request, MultipartFile file) { // 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				String picPath2 = File.separator+"data"+File.separator+"head"+File.separator;
				String newPicName = "";
				if (file.getSize() != 0) {
					String originalFileNameLeft = file.getOriginalFilename();
					// 新的图片名称
					int index = originalFileNameLeft.lastIndexOf(".");
					newPicName = UUID.randomUUID()+originalFileNameLeft.substring(index);
					// 新图片，写入磁盘
					File f = new File(uploadPath==null?"c:/data/head":uploadPath, newPicName);
					if (!f.exists()) {
						// 先创建文件所在的目录
						f.getParentFile().mkdirs();
					} else {
						f.delete();
					}
					file.transferTo(f);

				}
				return newPicName;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	public static String saveFile1( MultipartFile file) { // 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				String newPicName = "";
				if (file.getSize() != 0) {
					// 新的图片名称
					newPicName = UUID.randomUUID()+".jpg";
					// 新图片，写入磁盘
					File f = new File(uploadPath==null?"c:/data/head":uploadPath, newPicName);
					if (!f.exists()) {
						// 先创建文件所在的目录
						f.getParentFile().mkdirs();
					} else {
						f.delete();
					}
					file.transferTo(f);
					
				}
				return newPicName;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

}
