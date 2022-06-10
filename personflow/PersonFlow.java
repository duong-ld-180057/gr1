package personflow;

import java.io.*;
import java.util.Collections;
import java.util.Vector;

public class PersonFlow implements Comparable<PersonFlow> {
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

    @Override
    public int compareTo(PersonFlow o) {
        if (begin == o.begin)
            return 0;
        else if (begin > o.begin)
            return 1;
        else
            return -1;
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
        try {
            Function function = new Function();
            Vector<PersonFlow> personFlowVector = new Vector<>();
            for (int i = 0; i < 20; i++) {
                String arr[] = function.locate();
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
                                        vector.get(j).get(2 * k + 1), arr[2 * n], arr[2 * n + 1]);
                                personFlow.setImpatience(Math.round(Math.random() * 10) / 10.0);
                                personFlowVector.add(personFlow);
                            }
                        }
                    }
                }

                // int N2 = vector.size() * vector.get(0).size();
                // int N1 = arr.length;
                // int P = personFlowVector.size();

                // assert (P <= N1 * N2 * 7) : "P > N1 * N2 * 7";
                // assert (P >= N1 * N2) : "P < N1 * N2";
            }

            Collections.sort(personFlowVector);
            try {
                File file = new File("./personflow/personFlow.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file, true);
                BufferedWriter bufferedReader = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedReader);
                int dem = 0;
                for (PersonFlow element : personFlowVector) {
                    dem++;
                    printWriter.println("<personFlow begin=\"" + element.begin + "\" id=\"" + dem
                            + "\" impatience=\"" + element.impatience + "\" end=\"" + element.end
                            + "\" period=\"1\">");
                    printWriter.println(
                            "\t<walk from=\"" + element.from + "\" to=\"" + element.to + "\"/>");
                    printWriter.println("</personFlow>");
                }
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
