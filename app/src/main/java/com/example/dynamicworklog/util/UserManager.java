package com.example.dynamicworklog.util;

import com.example.dynamicworklog.objects.Login;

import java.util.ArrayList;

public class UserManager {
    private static  ArrayList<Login> users = new ArrayList<Login>();
    private static Login user = new Login();

    public static  ArrayList<Login> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<Login> users) {
        UserManager.users = users;
    }

    public static void addUsers(Login login) {
        users.add(login);
    }

    public static String matriculaLastUser(){
        return getUsers().get(getUsers().size() - 1).getMatricula();
    }

    public static Login userByMatricula(String matricula){
        if(userByMatriculaValidation(matricula)) {
            for(Login user : users){
                if(user.getMatricula().equals(matricula)){
                    UserManager.user = user;
                    return user;
                }
            }
        }

        return null;

    }

    public static Login getUser() {
        return user;
    }

    public static void setUser(Login user) {
        UserManager.user = user;
    }

    public static Boolean userByMatriculaValidation(String matricula){
        for (Login user : users){
            if(user.getMatricula().equals(matricula)){
                return true;
            }
        }
        return false;
    }

    public static void registrationValidation(Login user){
        while(user.getMatricula().isEmpty() || isMatriculaExist(user.getMatricula())){
            MatriculaGenerator matriculaGenerator = new MatriculaGenerator();
            user.setMatricula(matriculaGenerator.randomMatricula());
        }

        //user.setMatricula(matricula);
        addUsers(user);
    }

    private static boolean isMatriculaExist(String matricula) {
        for (Login login : users) {
            if (login.getMatricula().equals(matricula)) {
                return true; // Já existe
            }
        }
        return false; // Não existe
    }
}
