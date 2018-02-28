package ind.entity;

import com.bstek.urule.model.Label;

/**
 * @author BJQXDN0626
 * @create 2018/2/28
 */
public class Customer {


    @Label("ID")
    private int id;
    @Label("名称")
    private String name;
    @Label("年龄")
    private int age;
    @Label("性别")
    private boolean sexual;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public boolean isSexual() {
        return sexual;
    }

    public void setSexual(boolean sexual) {
        this.sexual = sexual;
    }
}
