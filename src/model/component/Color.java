package model.component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Color {
    double[] background();
    double[] foreground() default {1,1,1};
}
