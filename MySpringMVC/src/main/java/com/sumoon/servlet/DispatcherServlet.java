package com.sumoon.servlet;

import com.sumoon.annotation.*;
import com.sumoon.controller.UserController;
import com.sumoon.service.UserService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 转发Servlet
 */
@WebServlet(name = "dispatcherServlet", urlPatterns = "/", loadOnStartup = 1, initParams = {
        @WebInitParam(name = "base-package", value = "com.sumoon")})
public class DispatcherServlet extends HttpServlet {

    /**
     * 带全限定名的包名
     */
    private List<String> packageNames = new ArrayList<String>();

    /**
     * 实例化映射
     */
    private Map<String, Object> instanceMap = new HashMap<String, Object>();

    /**
     * 名称映射
     */
    private Map<String, String> nameMap = new HashMap<String, String>();

    /**
     * url和方法的映射
     */
    private Map<String, Method> urlMethodMap = new HashMap<String, Method>();

    /**
     * 方法和全限定名对应关系
     */
    private Map<Method, String> methodPackageMap = new HashMap<Method, String>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = url.replaceAll(contextPath, "");
        Method method = urlMethodMap.get(path);
        if (method != null) {
            String packageName = methodPackageMap.get(method);
            String controllerName = nameMap.get(packageName);
            if (instanceMap.get(controllerName) instanceof UserController) {
                UserController controller = (UserController) instanceMap.get(controllerName);
                try {
                    method.setAccessible(true);
                    Object responseValue = method.invoke(controller);
                    req.getRequestDispatcher("/index.jsp").forward(req, resp);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        String basePackage = config.getInitParameter("base-package");
        try {
            scanPackage(basePackage);
            instance();
            // 通过反射实现控制反转（IOC）
            springIOC();
            handleMethodPackageMapping();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理方法和全限定名
     *
     * @throws ClassNotFoundException
     */
    private void handleMethodPackageMapping() throws ClassNotFoundException {
        if (packageNames.size() > 0) {
            for (String name : packageNames) {
                Class<?> clazz = Class.forName(name);
                if (clazz.isAnnotationPresent(Controller.class)) {
                    StringBuffer sb = new StringBuffer();
                    if (clazz.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
                        sb.append(mapping.value());
                    }
                    Method[] methods = clazz.getDeclaredMethods();
                    if (methods != null && methods.length > 0) {
                        for (Method method : methods) {
                            if (method.isAnnotationPresent(RequestMapping.class)) {
                                sb.append(method.getAnnotation(RequestMapping.class).value());
                                urlMethodMap.put(sb.toString(), method);
                                methodPackageMap.put(method, name);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 实现依赖控制反转
     *
     * @throws IllegalArgumentException illegal access
     * @throws IllegalAccessException   illegal access
     */
    private void springIOC() throws IllegalArgumentException, IllegalAccessException {
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String name = autowired.value();
                    if (StringUtils.isBlank(name)) {
                        for (Map.Entry<String, Object> entrySet : instanceMap.entrySet()) {
                            Object value = entrySet.getValue();
                            if (value instanceof UserService) {
                                field.setAccessible(true);
                                field.set(entry.getValue(), value);
                            }
                        }
                    } else {
                        if (instanceMap.get(name) == null) {
                            throw new RuntimeException("找不到对应注解的类");
                        }
                        field.setAccessible(true);
                        field.set(entry.getValue(), instanceMap.get(name));
                    }
                }
            }
        }
    }

    /**
     * 实例化全限定类
     *
     * @throws ClassNotFoundException class Not found
     * @throws IllegalAccessException illegal Access
     * @throws InstantiationException instantiation error
     */
    private void instance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (String packageName : packageNames) {
            Class<?> clazz = Class.forName(packageName);
            if (clazz.isAnnotationPresent(Controller.class)) {
                // 控制器注解
                Controller annotation = clazz.getAnnotation(Controller.class);
                String controllerName = annotation.value();
                if (StringUtils.isBlank(controllerName)) {
                    controllerName = StringUtils.uncapitalize(clazz.getSimpleName());
                }
                instanceMap.put(controllerName, clazz.newInstance());
                nameMap.put(packageName, controllerName);
                System.out.println("controller: " + packageName + ", value: " + controllerName);
            } else if (clazz.isAnnotationPresent(Service.class)) {
                // 服务器注解
                Service service = clazz.getAnnotation(Service.class);
                String serviceName = service.value();
                if (StringUtils.isBlank(serviceName)) {
                    serviceName = StringUtils.uncapitalize(clazz.getSimpleName());
                }
                instanceMap.put(serviceName, clazz.newInstance());
                nameMap.put(packageName, serviceName);
                System.out.println("service: " + packageName + ", value: " + serviceName);
            }
        }
    }

    /**
     * 扫描基包
     *
     * @param basePackage 基包路径名
     */
    private void scanPackage(String basePackage) {
        URL url = this.getClass().getClassLoader().getResource(basePackage.replaceAll("\\.", "/"));
        File file = new File(url.getPath());
        System.out.println("scan: " + basePackage);
        for (File myfile : file.listFiles()) {
            if (myfile.isDirectory()) {
                // 是目录，递归扫描
                scanPackage(basePackage + "." + myfile.getName());
            } else {
                // 是文件
                packageNames.add(basePackage + "." + myfile.getName().split("\\.")[0]);
            }
        }
    }
}
