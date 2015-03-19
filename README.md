# EasyDB
闲时无聊拿来学习java的。


实体类
  public class Dog extends EntityBase {
	
	@Id(type=Type.String)   // -- 自定义ID，默认不需要声明id，则为自增int类型
	private String id;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	private int age;
}


数据操作

  public static void main(String[] args) {
		//web系统在web.xml中配置服务器启动时加载该监听类
		//当前为测试，手动加载此监听类
		ContextLoaderListener listener=new ContextLoaderListener();
		Dog dog=new Dog();
		dog.setAge(3);
		dog.setId("testid");
		dog.setName("jeep");
		dog.save();//增
		
		dog.setId("testid");
		
		dog=dog.load();//根据id查询
		dog.setName("jeep2");
		dog.update();//修改name
		
		List<Dog> results=dog.findListBySQL("name = ?", "jepp2");//条件查询
		if(results.size()>0){
			dog=results.get(0);
		}
		dog.print();//打印属性值
		dog.delete();//删除
	}
