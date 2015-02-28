package test;

import com.easydb.util.XMLUtil;

public class XMLUtilTest {
	public void testRootElement(){
		XMLUtil util=XMLUtil.getInstance();
		util.xmlName="Dream";
		util.parseCoreXML();
		System.out.println(util.getDBURL());
	}
	
	public static void main(String[] args) {
		new XMLUtilTest().testRootElement();
	}
}
