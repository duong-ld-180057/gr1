import java.io.*;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class PersonFlow {
    private int begin;
    private int end;
    private String from;
    private String to;
    private double impatience;

    public PersonFlow(int begin, int end, String from, String to) {
        this.begin = begin;
        this.end = end;
        this.from = from;
        this.to = to;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getImpatience() {
        return impatience;
    }

    public void setImpatience(double impatience) {
        this.impatience = impatience;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int totalRun = 20;
            // hoi nguoi dung tu nhap hay may nhap
            System.out.print("Ban co muon may nhap? (y/n): ");
            String input = sc.nextLine();
            boolean isUserType = true;
            if (input.equals("y")) {
                isUserType = false;
            }

            // hoi nguoi dung co muon them thuoc tinh impaience khong
            System.out.print("Ban co muon them thuoc tinh impaience khong? (y/n): ");
            input = sc.nextLine();
            boolean isAddImpatience = false;
            if (input.equals("y")) {
                isAddImpatience = true;
            }

            // hoi nguoi dung muon sap xep theo kieu gi
            System.out.print("Ban co muon sap xep theo kieu gi? (1: begin/2: from): ");
            input = sc.nextLine();
            int sortType = 1;
            if (input.equals("2")) {
                sortType = 2;
            }

            // hoi nguoi dung co muon chay thu 1 lan khong
            System.out.print("Ban co muon chay thu 1 lan khong? (y/n): ");
            input = sc.nextLine();
            if (input.equals("y")) {
                totalRun = 1;
            }

            Function function = new Function();
            Vector<PersonFlow> personFlowVector = new Vector<>();
            for (int i = 0; i < totalRun; i++) {
                String arrs[][] = function.locate(sc, isUserType, i);
                for (String[] arr : arrs) {
                    Vector<Vector<Integer>> vector =
                            function.generateWholeDay((int) Math.pow(2, 31) - 1,
                                    Integer.parseInt(arr[arr.length - 1]), arr[0], arr[1]);
                    for (int j = 0; j < 7; j++) {
                        for (int k = 0; k < vector.get(j).size() / 2; k++) {
                            if (arr.length == 5) {
                                PersonFlow personFlow = new PersonFlow(vector.get(j).get(2 * k),
                                        vector.get(j).get(2 * k + 1), arr[2], arr[3]);
                                personFlow.setImpatience(Math.round(Math.random() * 10) / 10.0);
                                personFlowVector.add(personFlow);
                            } else {
                                for (int n = 1; n < 4; n++) {
                                    PersonFlow personFlow = new PersonFlow(vector.get(j).get(2 * k),
                                            vector.get(j).get(2 * k + 1), arr[2 * n],
                                            arr[2 * n + 1]);
                                    personFlow.setImpatience(Math.round(Math.random() * 10) / 10.0);
                                    personFlowVector.add(personFlow);
                                }
                            }
                        }
                    }
                }
            }

            if (sortType == 1) {
                Collections.sort(personFlowVector, new BeginComparator());
            } else {
                Collections.sort(personFlowVector, new FromComparator());
            }
            
            try {
                File file = new File("personFlow.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file, false);
                BufferedWriter bufferedReader = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedReader);
                
                // them header
                printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>to");
                printWriter.println();
                printWriter.println(
                        "<routes xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://sumo.dlr.de/xsd/routes_file.xsd\">");
                
                int dem = 0;
                for (PersonFlow element : personFlowVector) {
                    dem++;
                    if (isAddImpatience) {
                        printWriter.println("\t<personFlow begin=\"" + element.begin + "\" id=\""
                                + dem + "\" impatience=\"" + element.impatience + "\" end=\""
                                + element.end + "\" period=\"1\">");
                    } else {
                        printWriter.println("\t<personFlow begin=\"" + element.begin + "\" id=\""
                                + dem + "\" end=\"" + element.end + "\" period=\"1\">");
                    }

                    printWriter.println(
                            "\t\t<walk from=\"" + element.from + "\" to=\"" + element.to + "\"/>");
                    printWriter.println("\t</personFlow>");
                }
                
                printWriter.println("</routes>");
                System.out.println("DONE!!!!");
                printWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception exception) {
            System.out.println("ERROR");
            exception.printStackTrace();
        }
    }
}
