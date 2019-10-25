package cn.f.demo.pojo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private Integer id;
    private String name;
    private String phone;
    private String education;
    private String state;
    private Date birthday;
    private Date time;

    public User(Integer id, String name, String phone, String education, String state, Date birthday, Date time) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.education = education;
        this.state = state;
        this.birthday = birthday;
        this.time = time;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
