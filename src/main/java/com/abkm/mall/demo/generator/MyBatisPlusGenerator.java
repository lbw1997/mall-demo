package com.abkm.mall.demo.generator;

import cn.hutool.setting.dialect.Props;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * description: MyBatisPlusGenerator代码生成器 <br>
 * date: 2020/9/11 20:33 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
public class MyBatisPlusGenerator {

    public static void main(String[] args) {
        //返回项目的真实路径地址
        String projectPath = System.getProperty("user.dir");
        String moduleName = scanner("模块名");
        String[] tableNames = scanner("请输入表名，多个以,分隔").split(",");
        //代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(initGlobalConfig(projectPath));
        autoGenerator.setDataSource(initDataSourceConfig());
        autoGenerator.setPackageInfo(initPackageConfig(moduleName));
        autoGenerator.setCfg(initInjectionConfig(projectPath,moduleName));
        autoGenerator.setTemplate(initTemplateConfig());
        autoGenerator.setStrategy(initStrategyConfig(tableNames));
        autoGenerator.setTemplateEngine(new VelocityTemplateEngine());
        autoGenerator.execute();
    }

    /**
     * 读取控制台信息
     * @param tip
     * @return
     */
    private static String scanner(String tip) {
        Scanner sc = new Scanner(System.in);
        System.out.println(("请输入" + tip + "："));
        if (sc.hasNext()) {
            String next = sc.next();
            if (!StringUtils.isEmpty(next)) {
                return next;
            }
        }
        throw new MybatisPlusException("请输入正确的"+tip+"!");
    }

    /**
     * 初始化全局配置
     */
    private static GlobalConfig initGlobalConfig(String projectPath) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(projectPath+"/src/main/java");
        globalConfig.setAuthor("abkm");
        globalConfig.setOpen(true);
        //给实体类添加Swagger2注解
        globalConfig.setSwagger2(true);
        //在mapper中添加Map映射
        globalConfig.setBaseResultMap(true);
        //覆盖原文件
        globalConfig.setFileOverride(true);
        //%s表示占位符，用来读取表名
        globalConfig.setEntityName("%s");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");
        globalConfig.setDateType(DateType.ONLY_DATE);
        return globalConfig;
    }

    /**
     * 初始化数据源配置
     */
    private static DataSourceConfig initDataSourceConfig() {
        Props props = new Props("generator.properties");
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(props.getStr("dataSource.url"));
        dataSourceConfig.setUsername(props.getStr("dataSource.username"));
        dataSourceConfig.setPassword(props.getStr("dataSource.password"));
        dataSourceConfig.setDriverName(props.getStr("dataSource.driverName"));
        return dataSourceConfig;
    }

    /**
     * 初始化包配置
     */
    private static PackageConfig initPackageConfig(String modelName) {
        Props props = new Props("generator.properties");
        PackageConfig config = new PackageConfig();
        config.setModuleName(modelName);
        config.setParent(props.getStr("package.base"));
        config.setEntity("model");
        return config;
    }

    /**
     * 初始化模板设置
     */
    private static TemplateConfig initTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        //可以对Controller、Entity、Service模板进行设置
        //mapper.xml需要单独设置
        templateConfig.setXml(null);
//        templateConfig.setEntity("templates/entity2.java");
        return templateConfig;
    }

    /**
     * 初始化策略设置
     */
    private static StrategyConfig initStrategyConfig(String[] tableNames) {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        //当表名中带*号时可以启用通配符模式
        if (tableNames.length == 1 && tableNames[0].contains("*")) {
            String[] likeStr = tableNames[0].split("_");
            String likePrefix = likeStr[0] + "_";
            strategyConfig.setLikeTable(new LikeTable(likePrefix));
        } else {
            strategyConfig.setInclude(tableNames);
        }
        return strategyConfig;
    }

    /**
     * 初始化自定义配置
     */
    private static InjectionConfig initInjectionConfig(String projectPath, String moduleName) {
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                //可以用于自定义属性
            }
        };
        //模板引擎是velocity
        String templatePath = "/templates/mapper.xml.vm";
        //自定义输出配置
        List<FileOutConfig> fileOutConfigs = new ArrayList<>();
        fileOutConfigs.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath+"/src/main/resources/mapper."+moduleName+"/"
                    +tableInfo.getEntityName()+"Mapper"+ StringPool.DOT_XML;
            }
        });
        injectionConfig.setFileOutConfigList(fileOutConfigs);
        return injectionConfig;
    }


}
