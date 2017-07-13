# mybatisGenerator
使用mybatis 生成带分页的查询，

将lib包下的jar包添加到classpath中
修改 PaginationPlugin.java中的baseDir路径
在生成的example中增加构造函数
如下
public class CarInfoExample extends AbstractExample {
   
   //手动增加 ---start
    public CarInfoExample(Pageable pageable) {
		super(pageable);
		oredCriteria = new ArrayList<Criteria>();
	}
	//手动增加 ---end
...
}



