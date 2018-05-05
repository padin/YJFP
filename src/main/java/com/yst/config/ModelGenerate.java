package com.yst.config;

import javax.sql.DataSource;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * @author PADIN
 * @Time 2018年5月4日下午8:50:02
 * @Title
 * @Description
 */
public class ModelGenerate {
	public static void main(String[] args) {
		DruidPlugin dp = new DruidPlugin("jdbc:mysql://219.151.22.103/db_voucher", "yst", "1234",
				"org.mariadb.jdbc.Driver");
		dp.start();
		DataSource ds = dp.getDataSource();
		// base model 所使用的包名
		String baseModelPkg = "com.yst.model";
		// base model 文件保存路径
		String baseModelDir = PathKit.getWebRootPath() + "/src/main/java/com/yst/model";

		// model 所使用的包名
		String modelPkg = "com.yst.model";
		// model 文件保存路径
		String modelDir = baseModelDir + "/";

		Generator gernerator = new Generator(ds, baseModelPkg, baseModelDir, modelPkg, modelDir);
		gernerator.generate();
	}
}
