package com.example.dynamicworklog.util;

public class MatriculaGenerator {

    public String randomMatricula(){
        // 1000 to 1000;
        int matricula = (int)(Math.random()*9001)+1000;
        return (String.valueOf (matricula));
    }

}
