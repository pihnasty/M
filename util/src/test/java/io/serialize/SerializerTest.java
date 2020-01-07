package io.serialize;

import org.testng.annotations.Test;

import java.io.Serializable;

@Test
public class SerializerTest {
    private Double exception =0.0001;


    @Test(enabled = true)
    public void serializeTest(){
        MyClass object1 = new MyClass("Hello", -7, 2.7e10);
        Serializer.serialize(object1,"D://A//M//MyProjects//p71_Conveyor//result//neural_network_model//speed_input","ws_serialize");
    }

}

class MyClass implements Serializable {
    String s;
    int i;
    double d;

    public MyClass(String s, int i, double d) {
        this.s = s;
        this.i = i;
        this.d = d;
    }

    public String toString() {
        return "s=" + s + "; i=" + i + "; d=" + d;
    }
}



