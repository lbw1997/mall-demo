import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.CharsetUtil;
import org.junit.platform.commons.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * description: PropertiesLoader <br>
 * date: 2020/9/12 11:51 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
public class MyPropertiesLoader extends Properties {

    /** 属性文件的URL */
    private URL propertiesFileUrl;
    private WatchMonitor watchMonitor;
    private Charset charset = CharsetUtil.CHARSET_ISO_8859_1;

    public MyPropertiesLoader(String path) {
        this(path,CharsetUtil.CHARSET_ISO_8859_1);
    }

    public MyPropertiesLoader(String path, String charsetName) {
        this(path,CharsetUtil.charset(charsetName));
    }

    public MyPropertiesLoader(String path, Charset charset) {
        if (StringUtils.isBlank(path)) {
            Assert.notBlank(path, "Blank properties file path !");
        }
        //ResourceUtil.getReader 会以相对于classpath绝对路径以文件流的形式返回URL
        //根据路径创建File对象并创建FileResource流
        BufferedReader reader = ResourceUtil.getReader(path,charset);
        this.load(reader);
    }

    public void load(BufferedReader reader) {
        try {
            //调用Properties类的load方法，加载properties文件
            super.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取properties文件中的key
    public String getStr(String key) {
        return getProperty(key);
    }
}
class PropertyTest {
    public static void main(String[] args) {
        String loadPath = "generator.properties";
        MyPropertiesLoader loader = new MyPropertiesLoader(loadPath);
        System.out.println(loader.getStr("package.base"));
    }
}
