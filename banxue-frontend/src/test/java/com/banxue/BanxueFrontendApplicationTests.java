package com.banxue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.banxue.utils.HttpUtils;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BanxueFrontendApplicationTests {

	
	public void contextLoads() {
		try {
            FileReader read = new FileReader("C:\\Users\\fengchaseyou\\Desktop\\text.bson");
            BufferedReader br = new BufferedReader(read);
            String row;
 
            int rownum = 1;
 
            int fileNo = 1;
            FileWriter fw = new FileWriter("D:/mogondb/text"+fileNo +".txt");
            while ((row = br.readLine()) != null) {
                rownum ++;
                fw.append(row + "\r\n");
 
                if((rownum / 1544) > (fileNo - 1)){
                    fw.close();
                    fileNo ++ ;
                    fw = new FileWriter("D:/mogondb/text"+fileNo +".txt");
                }
            }
            fw.close();
            System.out.println("rownum="+rownum+";fileNo="+fileNo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public void FileRowCounts() {
		try {
			String filepath="C:\\Users\\fengchaseyou\\Desktop\\text.bson";
            FileReader read = new FileReader(filepath);
            BufferedReader br = new BufferedReader(read);
            String row;
 
            int rownum = 1;
            while ((row = br.readLine()) != null) {
                rownum ++;
            }

    		System.out.println(rownum);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void testMapToString() {
    	Map<String,Object> param=new HashMap<String,Object>();
		param.put("orderNo", "你好");
    	param.put("lastStatus", "1");
    	String s=param.toString();
    	System.out.println(s);
	}
	@Test
	public void addImageToWx() {
		JSONObject js=new JSONObject ();
		js.put("type", "image");
		js.put("offset", 0);
		js.put("count", 20);
		try {
			String s=HttpUtils.post("https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=18_evaGdEmT-6UWlO4oVaTn_OB24jVlO4fMl3o2FRph4NYbOPQPl7q9bYQSEcr5fjta63D5fg8O8bdnX2u-PTUfziH3Ptvid-Pzh5HDciJ-kWQUTVrbWNmHFRGmkafBoKLzIpAGAOC4Hh-7jiIxTDJiAIAEOU", js);
			System.out.println(s);
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
	}

}
