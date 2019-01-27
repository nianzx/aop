package cn.nianzx.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;

/**
 * 切面类
 * Created by nianzx on 2019/1/25.
 */
@Aspect
@Component
public class MyAspect {

    /*
    * 申明一个切点，用来确定哪些类或方法需要代理，参数是execution表达式
    * execution表达式语法如下
    * execution(<修饰符模式>? <返回类型模式> <方法名模式>(<参数模式>) <异常模式>?) 带?为可选参数
    * 其中 修饰符、返回类型模式为*表示所有
    * <方法名模式>(<参数模式>例子如：cn.netinnet..controller.*.*(..)
    * 可以指定具体包名.方法名 其中包名可以使用..表示当前包和当前包的所有子包，后面的.*表示任意类名
    * 最后的*(..)表示任意方法 括号中的..表示任意参数
    *
    * 这段的最终意思即为：任意路径下(对于这个项目来说)的controller文件夹下的所有类里面的所有方法 都会触发这个切点
    * */
    @Pointcut("execution(public * cn.nianzx..controller.*.*(..))")
    private void point() {
    }


    @Before("point()&&@annotation(cn.nianzx.aop.MyAnnotation)")
    public void methodBefore(JoinPoint joinPoint) {
        System.out.println("进入Before...");
        System.out.println("目标方法名为:" + joinPoint.getSignature().getName());
        System.out.println("目标方法所属类的简单类名:" + joinPoint.getSignature().getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i + 1) + "个参数为:" + args[i]);
        }
    }

    @After("point()&&@annotation(cn.nianzx.aop.MyAnnotation)")
    public void methodAfter() {
        System.out.println("进入After...");
    }

    //在切入点, return后执行,可以获取到方法的返回值，但是无法修改
    @AfterReturning(pointcut = "point()&&@annotation(cn.nianzx.aop.MyAnnotation)", returning = "rvt")
    public void methodAfterReturning(Object rvt) {
        System.out.println("进入AfterReturning...");
        System.out.println("AfterReturning中获取目标方法返回值:" + rvt);
    }

    @Around("point()")
    public Object methodAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("进入Around...");
        Object result;
        try {
            //执行目标方法
            result = proceedingJoinPoint.proceed();
            //替换参数去执行
            //result = proceedingJoinPoint.proceed(new Object[]{"456"});
            System.out.println("Around目标方法返回结果后...");
        } catch (Throwable e) {
            System.out.println("Around执行中出现异常...");
            throw new RuntimeException(e);
        }
        System.out.println("Around执行结束...");
        return result;
    }

    @AfterThrowing(pointcut = "point()", throwing = "e")
    public void methodAfterThrowing(Throwable e) {
        System.out.println("进入AfterThrowing...发生了异常" + e.getMessage());
    }
}
