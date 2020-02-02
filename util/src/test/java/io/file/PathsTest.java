package io.file;

import org.testng.annotations.Test;


public class PathsTest {

    @Test
    public void firstFileNameFromDirectory() {
        String name = Paths.lastSortedFileNameFromDirectory(
            "D:\\A\\M\\MyProjects\\p71_Conveyor\\result\\neural_network_model\\learning_analysis_tool\\ws_serialize",
            "epoch");
        System.out.println(name);
    }
}
