/**
 * description: MyDirverTest <br>
 * date: 2020/9/12 1:12 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
public class MyDriverTest {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println(aClass.getClassLoader());
    }
}
