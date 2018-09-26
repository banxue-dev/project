package com.banxue.utils.wx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.banxue.utils.pay.wex.pojo.TextMessage;
import com.thoughtworks.xstream.XStream;

/**
 * 作者：fengchase 时间：2018年7月12日 文件：StrXmlToMap.java 项目：banxue-backend
 */
public class StrXmlToMap {

	/**
	 * 将string类型的xml信息转为map
	 * 
	 * @return 2018年7月12日 作者：fengchase
	 * @throws DocumentException
	 */
	public static Map<String, String> strXmltoMap(String xmlStr) throws DocumentException {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		Document document;
		document = DocumentHelper.parseText(xmlStr);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

	/**
	 * 输入流类型的xml转为string
	 * 
	 * @return 2018年7月12日 作者：fengchase
	 * @throws IOException
	 */
	public static String ISXmlToString(InputStream inputStream) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		StringBuffer buf = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			buf.append(line);
		}
		reader.close();
		inputStream.close();
		return buf.toString();

	}
	/**
     * 将文本消息转换为xml
     * 
     * @param textMessage
     * @return
     */
    static String textMessageToXml(TextMessage textMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);
    }
    public static String initText(String toUserName, String fromUserName,
            String content) {
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(WxConstants.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return textMessageToXml(text);
    }

}
