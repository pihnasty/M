package object;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class ObjectUtil {


    public static ArrayList ArrayListIsNullOrEmpty(ArrayList value, ArrayList defaultValue) {
        ArrayList list = Optional.ofNullable(value).orElse( defaultValue);
        return list.isEmpty()? defaultValue:value;
    }




}
