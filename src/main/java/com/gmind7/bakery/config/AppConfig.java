package com.gmind7.bakery.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = "com.gmind7", excludeFilters = {@ComponentScan.Filter(Controller.class)})
public class AppConfig {
	
	public AppConfig(){
		StringBuffer sb = new StringBuffer("\n");
		sb.append("  ____           _           _ _____   ____        _                          \n");
		sb.append(" / ___|_ __ ___ (_)_ __   __| |___  | | __ )  __ _| | _____ _ __ _   _        \n");
		sb.append("| |  _| '_ ` _ \\| | '_ \\ / _` |  / /  |  _ \\ / _` | |/ / _ \\ '__| | | |   \n");
		sb.append("| |_| | | | | | | | | | | (_| | / /   | |_) | (_| |   <  __/ |  | |_| |       \n");
		sb.append(" \\____|_| |_| |_|_|_| |_|\\__,_|/_/    |____/ \\__,_|_|\\_\\___|_|   \\__, | \n");
		sb.append("                                                                 |___/        \n");
		sb.append(":: Welcome to the Gmind7 Java Recipes__________________________________       \n");
		sb.append("\n");
		System.out.print(sb.toString());
	}
	
}