package string;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Optional;

public class StringUtil {


    public static String OptionalIsNullOr(String value, String defaultValue) {
        String pathToProject = Optional.ofNullable(value).orElse( defaultValue);
        return pathToProject.isEmpty()?defaultValue:value;
    }




}
