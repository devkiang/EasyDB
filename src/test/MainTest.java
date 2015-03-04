package test;

import java.util.Date;
import java.util.List;

import com.easydb.core.ContextLoaderListener;
import com.easydb.util.EasyDBLog;

import test.model.BigPerson;

public class MainTest {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		//web系统在web.xml中配置服务器启动时加载该监听类
		//当前为测试，手动加载此监听类
		ContextLoaderListener listener=new ContextLoaderListener();
		Date d1=new Date();
		long t1=d1.getTime();
		
		 
							
//		for (int i = 0; i < 10000; i++) {
//			BigPerson bigPerson=new BigPerson();
//			bigPerson.setId(i);
//			bigPerson.setName("test"+i);
//			bigPerson.setSex(true);
//			bigPerson.setAge(i);
//			bigPerson.save();
//		}
		
		BigPerson bigPerson=new BigPerson();
		bigPerson.setId("2");
//		System.out.println(bigPerson.getId());
//		List<BigPerson> results=bigPerson.findListBySQL("sex=? and name=?", true,"test5");
		bigPerson=bigPerson.load();
		bigPerson.print();
		bigPerson.setAge(99);
		bigPerson.load();
		bigPerson.print();
//		for (BigPerson big : results) {
//			big.print();
//		}
		
		
		Date d2=new Date();
		long t2=d2.getTime();
		long exetime=t2-t1;
		EasyDBLog.alert("耗时   "+exetime+" ms");

//		bigPerson.setId(2);
//		bigPerson=bigPerson.load();
//		bigPerson.setName("dog");
//		bigPerson.update();
//		bigPerson=bigPerson.load();
//		bigPerson.delete();
//		System.out.println(bigPerson.toString());
		
	}
}
