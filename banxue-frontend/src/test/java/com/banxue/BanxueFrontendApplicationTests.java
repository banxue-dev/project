package com.banxue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.banxue.onlinemail.entity.BxNuoWuliu;
import com.banxue.utils.DateUtils;
import com.banxue.utils.HttpUtils;
import com.banxue.utils.R;
import com.banxue.utils.wx.WxUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.graphbuilder.struc.LinkedList;

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

	public void testMapToString(int len) {
		//?type=shunfeng&postid=708546710852&temp=456&phone=4089
		int now=0;
		try {
			String orderCode="101CKBQ190300005";
			String[] codes=orderCode.split(",");
			int suc=0;
			List<String> errs=new ArrayList<String>();
//			for(int i=0;i<1;i++) {
			for(int i=len;i<codes.length;i++) {
				String code=codes[i];
//				code="101CAEO190100001";
				System.out.println("正在处理第"+i+"单，订单号是："+code);
				JSONObject jb=new JSONObject();
				jb.put("orderCode", code);
				jb.put("orderStatus", "1");
				jb.put("ID", "8"+DateUtils.format(new Date(), DateUtils.DATE_TIME_STAMP)+"12346");
				String result;
//				result = HttpUtils.sendGET("http://mpzx.chfcloud.com/csc-cloud/v1/order/update", "orderCode=101CKGI190100025&orderStatus=1&ID="+"8"+DateUtils.format(new Date(), DateUtils.DATE_TIME_STAMP)+"12346");
				result = HttpUtils.post("https://mp.sunscloud.com/csc-cloud/v1/order/update", jb);
				if(result!=null) {
					suc++;		
				}else {
					writeFile(code);
					errs.add(code);
				}
				now=i;
			}
			System.out.println("总共"+codes.length+"单"+"成功"+suc+"单"+DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
			System.out.println("失败的："+errs.toString());
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
			testMapToString(now);
		}
	}
	public void testwritefile() {
		testMapToString(0);
//		writeFile("123213");
	}
	public void writeFile(String no) {
		try{
		      String data = "\n\r"+no;

		      File file =new File("C:\\Users\\fengchaseyou\\Desktop\\errororder.txt");

		      //if file doesnt exists, then create it
		      if(!file.exists()){
		       file.createNewFile();
		      }

		      //true = append file
		      FileWriter fileWritter = new FileWriter(file,true);
		      fileWritter.write(data);
		      fileWritter.close();

		      System.out.println("Done");

		     }catch(IOException e){
		      e.printStackTrace();
		     }
	}
//	@Test
	public static void testFankui() {
		//?type=shunfeng&postid=708546710852&temp=456&phone=4089
		List<Integer> list=new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		System.out.println("测试");
//		list.forEach(t -> System.out.println(t));
	}
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

	@Test
	public  void testhttp() {
		JSONObject jb=new JSONObject();
		jb.put("loginName", "test");
		jb.put("password", "123456");
		String result="";
		try {
			result = HttpUtils.sendGetByHttps("https://orion-ticket-check.changhong.com/ticket/getTicketInfo", "name="+URLEncoder.encode("漆尧", "UTF-8")+"&ticketNum=8762116150653");
//			result = HttpUtils.sendHtpps("aaa", "https://orion-ticket-check.changhong.com/ticket/getTicketInfo?name=漆尧&ticketNum=8762116150653");
//			result = HttpUtils.postByJson("http://www.banxue.fun:10100/api/login", jb);
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
		System.out.println(result);
	}
	public static void main(String[] args) {
	}
	
}
