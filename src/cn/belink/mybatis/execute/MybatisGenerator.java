package cn.belink.mybatis.execute;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MybatisGenerator {

	private static Properties properties = new Properties();

	public static void main(String[] args) throws Exception {

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
		ProgressCallback progressCallback = new VerboseProgressCallback();
		myBatisGenerator.generate(progressCallback);
		 for (String warning : warnings) {
	            System.out.println(warning);
	        }
		 System.out.println("生成完成");

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
