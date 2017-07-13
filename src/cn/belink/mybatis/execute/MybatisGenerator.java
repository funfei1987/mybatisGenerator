package cn.belink.mybatis.execute;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MybatisGenerator {

	private static Properties properties = new Properties();

	public static void main(String[] args) throws Exception {

		load();
		Context content = create();// 创建实体
		System.out.println("生成完成");

	}

	private static void load() {
		String savePath = MybatisGenerator.class
				.getResource("/conf/path.properties").getPath();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					savePath));
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 创建实体，mapper，xml
	 * 
	 * @param jspPath
	 * @throws Exception
	 */
	private static Context create() throws Exception {

		String filePath =   "src/generatorConfig.xml";
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File(filePath);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		System.out.println(config.toString());
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
				callback, warnings);
		myBatisGenerator.generate(null);
		Context content = config.getContext("mysqlTables");
		return content;

	}

	/**
	 * 转换下划线后面的第一个字母为大写，并去除下划线
	 * 
	 * @param property
	 * @return
	 */
	private static String toEntityCol(String property) {
		property = property.toLowerCase();
		Matcher matcher = Pattern.compile("_[a-z]?", Pattern.DOTALL).matcher(
				property);
		while (matcher.find()) {
			property = property.replaceAll(matcher.group(), matcher.group()
					.toUpperCase());
		}
		return property.replaceAll("_", "");
	}

}
