package mergefile;

import java.io.*;
import java.util.Vector;

public class MergeFile {
    public static void main(String[] args) {
        String output = "output.txt";
        File files[] = new File[args.length];
        Vector<String> lines = new Vector<>();

        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
            files[i] = new File(args[i]);
        }

        File outputFile = new File(output);

        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            for (File file : files) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;

                while ((line = reader.readLine()) != null) {
                    if (lines.contains(line)) {
                        continue;
                    }
                    lines.add(line);
                    writer.write(line);
                    writer.newLine();
                }
                reader.close();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("DONE!!!!");
    }
}


