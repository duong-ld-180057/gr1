import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Function {
    public int[] turkeyWFunc(int i, double alpha, double duration) {
        if (alpha <= 0 || alpha >= 1)
            return null;
        // int k = (int) duration;
        // if (duration - k != 0 || Math.abs(duration - k / 3600 * 3600) > 600) return null;
        if (duration % 3600 > 600) {
            return null;
        }
        if (i < 0 || i > 6)
            return null;
        int arr[] = new int[2];
        Random random = new Random();
        arr[0] = i * 2 * 3600;
        arr[1] = (i * 2 + 1) * 3600 + random.nextInt(601);
        return arr;
    }

    public Vector<Vector<Integer>> generateWholeDay(int inc, int N, int duration) {
        int arr[][] = new int[7][2 * N];
        double alpha[] = new double[7];
        if (inc != Math.pow(2, 31) - 1) {
            alpha[0] = Math.random();
            if (inc == 1) {
                for (int i = 1; i < 7; i++) {
                    alpha[i] = alpha[i - 1] + Math.random();
                }
            } else if (inc == -1) {
                for (int i = 1; i < 7; i++) {
                    alpha[i] = alpha[i - 1] - Math.random();
                }
            } else if (inc == 0) {
                for (int i = 1; i < 7; i++) {
                    alpha[i] = alpha[i - 1];
                }
            }
        } else {
            alpha[0] = 0.75;
            alpha[1] = 0.4;
            alpha[2] = 0.5;
            alpha[3] = 0.3;
            alpha[4] = 0.75;
            alpha[5] = 0.4;
            alpha[6] = 0.6;
        }
        for (int i = 0; i < 7; i++) {
            double deltaT = alpha[i] * (duration - 1) / (2 * N);
            int arr1[] = new int[2];
            arr1 = turkeyWFunc(i, alpha[i], duration);
            arr[i][0] = arr1[0];
            arr[i][1] = arr1[1];
            for (int k = 1; k < N; k++) {
                arr[i][2 * k] = (int) (arr[i][0] + k * Math.ceil(deltaT));
                arr[i][2 * k + 1] = (int) (arr[i][1] - k * Math.ceil(deltaT));
            }
        }
        Vector<Vector<Integer>> vector = new Vector<Vector<Integer>>();
        for (int i = 0; i < 7; i++) {
            Vector<Integer> vector1 = new Vector<Integer>();
            for (int k = 0; k < 2 * N; k++) {
                vector1.add(arr[i][k]);
            }
            vector.add(vector1);
        }
        return vector;
    }

    public Vector<Vector<Integer>> generateWholeDay(int inc, int N, String center,
            String direction) {
        int duration = 3600;
        int arr[][] = new int[7][2 * N];
        double adjust[] = null;
        double alpha[] = new double[7];
        if (inc != (int) Math.pow(2, 31) - 1) {
            alpha[0] = Math.random();
            if (inc == 1) {
                for (int i = 1; i < 7; i++) {
                    alpha[i] = alpha[i - 1] + Math.random();
                }
            } else if (inc == -1) {
                for (int i = 1; i < 7; i++) {
                    alpha[i] = alpha[i - 1] - Math.random();
                }
            } else
                for (int i = 1; i < 7; i++) {
                    alpha[i] = alpha[i - 1];
                }
        } else {
            alpha[0] = 0.75;
            alpha[1] = 0.35;
            alpha[2] = 0.5;
            alpha[3] = 0.3;
            alpha[4] = 0.75;
            alpha[5] = 0.35;
            alpha[6] = 0.6;

            if (center.equals("Gastro")) {
                if (direction.equals("West")) {
                    adjust = new double[] {0, 0.6, 0, 0.6, 0, 0.6, 0};
                } else if (direction.equals("East")) {
                    adjust = new double[] {0.1, 0, 0.2, 0, 0.1, 0, 0.2};
                } else if (direction.equals("North")) {
                    adjust = new double[] {0, 0.6, 0, 0.6, 0, 0.6, 0};
                } else if (direction.equals("South")) {
                    adjust = new double[] {0.2, 0.6, 0, 0.6, 0.2, 0.6, 0};
                }

            } else if (center.equals("Neuro")) {
                if (direction.equals("West")) {
                    adjust = new double[] {0.1, 0, 0.2, 0, 0.1, 0, 0.2};
                } else if (direction.equals("East")) {
                    adjust = new double[] {0, 0.6, 0, 0.6, 0, 0.6, 0};
                } else if (direction.equals("North")) {
                    adjust = new double[] {0, 0.6, 0, 0.6, 0, 0.6, 0};
                } else if (direction.equals("South")) {
                    adjust = new double[] {0.2, 0.6, 0, 0.6, 0.2, 0.6, 0};
                }
            } else if (center.equals("Supply")) {
                if (direction.equals("West")) {
                    adjust = new double[] {0.2, 0.55, 0.45, 0.65, 0.2, 0.55, 0.35};
                } else if (direction.equals("East")) {
                    adjust = new double[] {0.2, 0, 0.45, 0.65, 0.2, 0.55, 0.35};
                } else if (direction.equals("North")) {
                    adjust = new double[] {0.2, 0.55, 0.45, 0.65, 0.2, 0.55, 0.35};
                } else if (direction.equals("South")) {
                    adjust = new double[] {0.2, 0.55, 0.45, 0.65, 0.2, 0.55, 0.35};
                }

            } else if (center.equals("Disposal")) {
                if (direction.equals("West")) {
                    adjust = new double[] {0.2, 0, 0.45, 0.65, 0.2, 0.55, 0.35};
                } else if (direction.equals("East")) {
                    adjust = new double[] {0.2, 0.55, 0.45, 0.65, 0.2, 0.55, 0.35};
                } else if (direction.equals("North")) {
                    adjust = new double[] {0.2, 0.55, 0.45, 0.65, 0.2, 0.55, 0.35};
                } else if (direction.equals("South")) {
                    adjust = new double[] {0.2, 0.55, 0.45, 0.65, 0.2, 0.55, 0.35};
                }

            } else if (center.equals("Emergency")) {
                if (direction.equals("West")) {
                    adjust = new double[] {0.2, 0, 0, 0, 0.2, 0, 0};
                } else if (direction.equals("East")) {
                    adjust = new double[] {0.23, 0.3, 0, 0.3, 0.2, 0, 0};
                } else if (direction.equals("North")) {
                    adjust = new double[] {0.23, 0.05, 0, -0.05, 0.2, -0.05, 0};
                } else if (direction.equals("South")) {
                    adjust = new double[] {0.23, 0.05, 0, -0.05, 0.2, -0.05, 0};
                }

            } else if (center.equals("Mobility")) {
                if (direction.equals("West")) {
                    adjust = new double[] {-0.05, 0, -0.2, 0, -0.05, 0, -0.1};
                } else if (direction.equals("East")) {
                    adjust = new double[] {-0.05, 0, -0.2, 0, -0.05, 0, -0.1};
                } else if (direction.equals("North")) {
                    adjust = new double[] {-0.05, 0, -0.2, 0, -0.2, 0, -0.1};
                } else if (direction.equals("South")) {
                    adjust = new double[] {-0.05, 0, -0.2, -0.2, -0.2, -0.1, -0.1};
                }

            } else if (center.equals("Woman")) {
                if (direction.equals("West")) {
                    adjust = new double[] {-0.25, -0.2, -0.2, -0.2, -0.25, -0.2, -0.2};
                } else if (direction.equals("East")) {
                    adjust = new double[] {-0.25, -0.2, -0.2, -0.2, -0.25, -0.2, -0.2};
                } else if (direction.equals("North")) {
                    adjust = new double[] {-0.25, -0.2, -0.2, -0.2, -0.25, -0.2, -0.2};
                } else if (direction.equals("South")) {
                    adjust = new double[] {-0.25, -0.2, -0.2, -0.2, -0.25, -0.2, -0.2};
                }

            } else if (center.equals("Brain")) {
                if (direction.equals("West")) {
                    adjust = new double[] {-0.05, -0.1, -0.1, -0.1, 0, 0, -0.1};
                } else if (direction.equals("East")) {
                    adjust = new double[] {-0.15, 0.2, -0.1, 0.2, -0.25, 0.2, 0.2};
                } else if (direction.equals("North")) {
                    adjust = new double[] {-0.15, 0.2, -0.1, 0.2, 0.15, 0.2, 0.2};
                } else if (direction.equals("South")) {
                    adjust = new double[] {-0.25, 0.2, -0.1, 0.2, -0.25, 0.2, 0.2};
                }

            } else if (center.equals("Admin") || center.equals("Knowledge")
                    || center.equals("Lab")) {
                duration = 15 * 3600;
                adjust = new double[] {0, 0, 0, 0, 0, 0, 0};
                for (int i = 0; i < 7; i++) {
                    alpha[i] = 0.29;
                }
            }

        }

        for (int i = 0; i < 7; i++) {
            alpha[i] = alpha[i] + adjust[i];
        }
        for (int i = 0; i < 7; i++) {
            double deltaT = alpha[i] * (duration - 1) / (2 * N);
            int arr1[] = new int[2];
            arr1 = turkeyWFunc(i, alpha[i], duration);
            arr[i][0] = arr1[0];
            arr[i][1] = arr1[1];
            for (int k = 1; k < N; k++) {
                arr[i][2 * k] = (int) (arr[i][0] + k * Math.ceil(deltaT));
                arr[i][2 * k + 1] = (int) (arr[i][1] - k * Math.ceil(deltaT));
            }
        }
        Vector<Vector<Integer>> vector = new Vector<Vector<Integer>>();
        for (int i = 0; i < 7; i++) {
            Vector<Integer> vector1 = new Vector<Integer>();
            for (int k = 0; k < 2 * N; k++) {
                vector1.add(arr[i][k]);
            }
            vector.add(vector1);
        }
        return vector;
    }

    public static String[] getDefaultEdge(int No) {
        String[] result = new String[4];
        switch (No % 32) {
            case 0:
                result[0] = "Gastro";
                result[1] = "North";
                result[2] = "E23";
                result[3] = "E27";
                return result;
            case 1:
                result[0] = "Gastro";
                result[1] = "East";
                result[2] = "E28";
                result[3] = "E40";
                return result;
            case 2:
                result[0] = "Gastro";
                result[1] = "South";
                result[2] = "E173";
                result[3] = "E177";
                return result;
            case 3:
                result[0] = "Gastro";
                result[1] = "West";
                result[2] = "-E20";
                result[3] = "-E8";
                return result;
            case 4:
                result[0] = "Knowledge";
                result[1] = "North";
                result[2] = "E168";
                result[3] = "E172";
                return result;
            case 5:
                result[0] = "Knowledge";
                result[1] = "East";
                result[2] = "E43";
                result[3] = "E55";
                return result;
            case 6:
                result[0] = "Knowledge";
                result[1] = "South";
                result[2] = "-E137";
                result[3] = "-E133";
                return result;
            case 7:
                result[0] = "Knowledge";
                result[1] = "West";
                result[2] = "E138";
                result[3] = "E150";
                return result;
            case 8:
                result[0] = "Neuro";
                result[1] = "North";
                result[2] = "-E130";
                result[3] = "-E126";
                return result;
            case 9:
                result[0] = "Neuro";
                result[1] = "East";
                result[2] = "E58";
                result[3] = "E71";
                return result;
            case 10:
                result[0] = "Neuro";
                result[1] = "South";
                result[2] = "E76";
                result[3] = "E80";
                return result;
            case 11:
                result[0] = "Neuro";
                result[1] = "West";
                result[2] = "-E125";
                result[3] = "-E112";
                return result;
            case 12:
                result[0] = "Emergency";
                result[1] = "North";
                result[2] = "E508";
                result[3] = "E521";
                return result;
            case 13:
                result[0] = "Emergency";
                result[1] = "East";
                result[2] = "-E161";
                result[3] = "E98";
                return result;
            case 14:
                result[0] = "Emergency";
                result[1] = "South";
                result[2] = "-E435";
                result[3] = "-E422";
                return result;
            case 15:
                result[0] = "Emergency";
                result[1] = "West";
                result[2] = "E360";
                result[3] = "-E307";
                return result;
            case 16:
                result[0] = "Mobility";
                result[1] = "North";
                result[2] = "E23";
                result[3] = "E27";
                return result;
            case 17:
                result[0] = "Mobility";
                result[1] = "East";
                result[2] = "E28";
                result[3] = "E40";
                return result;
            case 18:
                result[0] = "Mobility";
                result[1] = "South";
                result[2] = "E173";
                result[3] = "E177";
                return result;
            case 19:
                result[0] = "Mobility";
                result[1] = "West";
                result[2] = "-E20";
                result[3] = "-E8";
                return result;
            case 20:
                result[0] = "Admin";
                result[1] = "North";
                result[2] = "-E240";
                result[3] = "-E236";
                return result;
            case 21:
                result[0] = "Admin";
                result[1] = "East";
                result[2] = "-E378";
                result[3] = "-E366";
                return result;
            case 22:
                result[0] = "Admin";
                result[1] = "South";
                result[2] = "-E409";
                result[3] = "-E405";
                return result;
            case 23:
                result[0] = "Admin";
                result[1] = "West";
                result[2] = "-E253";
                result[3] = "-E241";
                return result;
            case 24:
                result[0] = "Lab";
                result[1] = "North";
                result[2] = "-E414";
                result[3] = "-E410";
                return result;
            case 25:
                result[0] = "Lab";
                result[1] = "East";
                result[2] = "E318";
                result[3] = "E330";
                return result;
            case 26:
                result[0] = "Lab";
                result[1] = "South";
                result[2] = "E457";
                result[3] = "E461";
                return result;
            case 27:
                result[0] = "Lab";
                result[1] = "West";
                result[2] = "-E268";
                result[3] = "-E256";
                return result;
            case 28:
                result[0] = "Brain";
                result[1] = "North";
                result[2] = "E462";
                result[3] = "E466";
                return result;
            case 29:
                result[0] = "Brain";
                result[1] = "East";
                result[2] = "-E392";
                result[3] = "-E379";
                return result;
            case 30:
                result[0] = "Brain";
                result[1] = "South";
                result[2] = "-E289";
                result[3] = "-E285";
                return result;
            case 31:
                result[0] = "Brain";
                result[1] = "West";
                result[2] = "-E284";
                result[3] = "-E271";
                return result;
            default:
                result[0] = "Gastro";
                result[1] = "North";
                result[2] = "E23";
                result[3] = "E27";
                return result;
        }
    }

    public void assignPartition(Vector<String> vec, Scanner sc, boolean isUserType, int No) {
        String arr[] = new String[] {"Gastro", "Neuro", "Brain", "Supply", "Disposal", "Admin",
                "Knowledge", "Lab", "Emergency", "Mobility"};
        String arr1[] = new String[] {"West", "East", "North", "South"};
        String center = "";
        String direction = "";
        String src = "";
        String dst = "";

        if (!isUserType) {
            String tmp[] = getDefaultEdge(No);
            center = tmp[0];
            direction = tmp[1];
            src = tmp[2];
            dst = tmp[3];
        } else {
            do {
                System.out.print("Nhap ten trung tam: ");
                center = sc.nextLine();
            } while (!Arrays.asList(arr).contains(center));
            vec.add(center);

            do {
                System.out.print("Nhap huong: ");
                try {
                    direction = sc.nextLine();
                } catch (Exception e) {
                    continue;
                }
            } while (!Arrays.asList(arr1).contains(direction));
            vec.add(direction);

            System.out.print("Nhap dinh nguon: ");
            src = sc.nextLine();
            System.out.print("Nhap dinh dich: ");
            dst = sc.nextLine();
        }

        // add center and direction to vector
        vec.add(center);
        vec.add(direction);

        // add all lanes to vector
        boolean notFound = false;
        do {
            try {
                Arrays.asList(Read.findWay(src, dst).split(" ")).forEach(vec::add);
            } catch (Exception e) {
                notFound = true;
                System.out.println("Khong tim thay duong di");
                System.out.print("Nhap lai dinh nguon: ");
                src = sc.nextLine();
                System.out.print("Nhap lai dinh dich: ");
                dst = sc.nextLine();
            }
        } while (notFound);
    }

    public String[][] locate(Scanner sc, boolean isUserType, int No) {
        Vector<String> vec = new Vector<String>();
        assignPartition(vec, sc, isUserType, No);

        // convert to array
        String[] arr = new String[vec.size()];
        return splitArray(vec.toArray(arr), No);
    }

    // split array string to array of array string
    public String[][] splitArray(String[] arr, int No) {
        String[][] result = new String[arr.length - 2][5];
        Random random = new Random();

        // print arr
        System.out.println("\n====================");
        System.out.println("Before split: ");
        System.out.print("[ ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.print(" ]\n");
        System.out.println();
        System.out.println("\nAfter split: ");

        for (int i = 0; i < arr.length - 2; i++) {
            result[i][0] = arr[0];
            result[i][1] = arr[1];
            result[i][2] = arr[i + 2];
            if (arr[i+2].charAt(0) == '-') {
                result[i][3] = arr[i + 2].substring(1);
            } else {
                result[i][3] = "-" + arr[i + 2];
            }
            result[i][4] = String.valueOf(random.nextInt(1003 / 5) + 1);
            System.out.println("[ " + result[i][0] + " " + result[i][1] + " " + result[i][2] + " " + result[i][3] + " " + result[i][4] + " ]");
        }
        return result;
    }
}

