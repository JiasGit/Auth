package dream.lab.auth;

public class User {

    public String email, name, age;

    public User(){

    }

    public User(String email, String name, String age){
        this.email = email;
        this.name = name;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }
}

