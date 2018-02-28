package ind.entity;

import com.bstek.urule.model.Label;

/**
 * @author BJQXDN0626
 * @create 2018/2/28
 */
public class Server {
    @Label("名称")
    private String name;
    @Label("年龄")
    private int age;

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
}
