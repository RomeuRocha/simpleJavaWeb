package com.romeu;


import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.romeu.anotations.Controller;
import com.romeu.anotations.RequestMapping;


@WebServlet("/*") 
public class MiniSpringServlet extends HttpServlet {

    private final Map<String, Method> routeMap = new HashMap<>();
    private final Map<String, Object> controllers = new HashMap<>();

    @Override
    public void init() throws ServletException {
        try {
            // Encontrar todas as classes no pacote base
            String basePackage = "com.romeu.controllers";
            for (Class<?> cls : ClassScanner.findClasses(basePackage)) {
                if (cls.isAnnotationPresent(Controller.class)) {
                    Object controllerInstance = cls.getDeclaredConstructor().newInstance();
                    controllers.put(cls.getName(), controllerInstance);

                    // Registrar métodos anotados com @RequestMapping
                    for (Method method : cls.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                            String routeKey = mapping.method() + ":" + mapping.path();
                            routeMap.put(routeKey, method);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException("Failed to initialize MiniSpringServlet", e);
        }

        System.out.println(routeMap.toString());
        System.out.println(controllers.toString());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        String path = req.getPathInfo();
        String routeKey = method + ":" + path;

        Method handlerMethod = routeMap.get(routeKey);
        if (handlerMethod != null) {
            try {
                Object controller = controllers.get(handlerMethod.getDeclaringClass().getName());
                Object result = handlerMethod.invoke(controller);
                resp.getWriter().write(result.toString());
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request: " + e.getMessage());
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Route not found: " + path);
        }
    }
}

