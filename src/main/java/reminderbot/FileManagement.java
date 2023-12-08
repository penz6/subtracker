package reminderbot;

import okio.Path;
import org.apache.commons.io.output.FileWriterWithEncoding;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class FileManagement {
    //filename
    private static final String filename = "subscriptions.txt";

    //add to subscriptions file
    public static void addSub(String name, String date) throws IOException {
        //filewriters
        BufferedWriter bfwriter = new BufferedWriter(new FileWriter(filename, true));
        //wrte to end of file
        bfwriter.write(name + "-" + date + System.lineSeparator());
        //close file
        try {
            bfwriter.close();
        } catch (IOException e) {
            throw new RuntimeException("thats not so great, file not closed properly");
        }
    }

    //remove from subcription file
    public static boolean removeSub(String name) throws IOException {
        //if no temp file
        File temp = new File("tempfile.txt");
        temp.createNewFile();
        //filewriter
        BufferedWriter bfwriter = new BufferedWriter(new FileWriter("tempfile.txt"));
        BufferedReader bfreader = new BufferedReader(new FileReader(filename));
        //curentline
        String currentLine;
        //the code to remove to line
        while ((currentLine = bfreader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            //get the index where the line splits
            int end = currentLine.indexOf("-");
            if (trimmedLine.substring(0, end).toLowerCase().equals(name.toLowerCase(Locale.ROOT))) continue;
            bfwriter.write(currentLine + System.lineSeparator());
        }
        //rename file to current one
        bfwriter.close();
        bfreader.close();
        //rename to current file
        return temp.renameTo(new File(filename));

    }

    //return all the dates in an array
    public static ArrayList<String> getDates(String currentdate) throws IOException {
        BufferedReader bfreader = new BufferedReader(new FileReader(filename));
        //vars
        String currentLine;
        //arraylist to contain elements
        ArrayList<String> temp = new ArrayList<>();
        //run through file
        while ((currentLine = bfreader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            int start = currentLine.indexOf("-");
            //if it equals current date
            if (trimmedLine.substring(start).equals(currentdate)) {
                temp.add(currentLine);
            }
        }
        bfreader.close();
        return temp;
    }

    //run through the file
    public static ArrayList<String> listFile() throws IOException {
        BufferedReader bfreader = new BufferedReader(new FileReader(filename));
        //vars
        String currentLine;
        ArrayList<String> returner  = new ArrayList<>();
        //arraylist to contain elements
        ArrayList<String> temp = new ArrayList<>();
        //run through file
        while ((currentLine = bfreader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            int end = currentLine.indexOf("-");
            returner.add(trimmedLine.substring(0,end));
            returner.add(trimmedLine.substring(end+1,currentLine.length()));
        }
        bfreader.close();
        return returner;

    }

}
