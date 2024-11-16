package com.romeu;

import java.util.Set;

import org.reflections.Reflections;

import com.romeu.anotations.Controller;

public class ClassScanner {

    public static Set<Class<?>> findClasses(String basePackage) {
     
        
        // Cria o Reflections com o contextClassLoader
        Reflections reflections = new Reflections(basePackage);
        
        return reflections.getTypesAnnotatedWith(Controller.class);
    }
}

