package com.example.dynamicworklog.objects;

public class Login {
    private String matricula;
    private String name;
    private String password;
    private Department department;
    private Function function;


    public Login() {
    }

    public Login(String matricula, String name, String password, Department department, Function function) {
        this.matricula = matricula;
        this.name = name;
        this.password = password;
        this.department = department;
        this.function = function;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

}
