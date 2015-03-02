package test;

import java.util.List;

import com.easydb.core.ContextLoaderListener;

import test.model.BigPerson;

public class MainTest {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		//web系统在web.xml中配置服务器启动时加载该监听类
		//当前为测试，手动加载此监听类
		ContextLoaderListener listener=new ContextLoaderListener();
		
//		BigPerson bigPerson=new BigPerson();
//		bigPerson.setName("test");
//		bigPerson.setSex(true);
//		bigPerson.setAge(55);
//		bigPerson.save();
		BigPerson bigPerson=new BigPerson();
//		List<BigPerson> results=bigPerson.findListBySQL("name=? and id=?", "test",1);
		bigPerson.load();
//		for (BigPerson big : results) {
//			big.print();
//		}
//		bigPerson.setId(2);
//		bigPerson=bigPerson.load();
//		bigPerson.setName("dog");
//		bigPerson.update();
//		bigPerson=bigPerson.load();
//		bigPerson.delete();
//		System.out.println(bigPerson.toString());
		
	}
}
