package com.yst.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.yst.control.Automaticdraw;
import com.yst.model._MappingKit;
 
/**
 * @author PADIN
 * @Time 2018年5月4日下午8:35:34
 * @Title
 * @Description
 */
public class AppConfig extends JFinalConfig {

	public void configConstant(Constants me) {
		me.setDevMode(true);
	}

	public void configRoute(Routes me) {
		me.add("/automaticdraw", Automaticdraw.class);
	}

	public void configEngine(Engine me) {
	}

	public void configPlugin(Plugins me) {
		DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost/db_voucher", "root", "1234","org.mariadb.jdbc.Driver");
	    me.add(dp);
	    ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
	    me.add(arp);
	    _MappingKit.mapping(arp);
	  
	}

	public void configInterceptor(Interceptors me) {
	}

	public void configHandler(Handlers me) {
	}

}
