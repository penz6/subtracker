package test;

import reminderbot.FileManagement;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class TestFileManagement {
    public static void main(String[] args) throws IOException {
        //try to write to file
        FileManagement.addSub("test", "datetest");
        FileManagement.addSub("test2", "datetest");
        FileManagement.addSub("test3", "datetest");
        FileManagement.removeSub("test2");
    }
}
