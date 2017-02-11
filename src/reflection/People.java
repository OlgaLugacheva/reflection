package reflection;

/**
 * Created by Olga on 10.02.2017.
 */
public class People {
    private String name;
    private Integer age;

    protected void paySalary()
    {
        System.out.println("Pay");
    }

    public  People()
    {}

    public People(String name, Integer age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    private double salary;
}
