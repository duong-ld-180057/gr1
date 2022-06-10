package assign_partition;

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

    public void assignPartition(Vector<String> vec) {
        Scanner sc = new Scanner(System.in);
        String arr[] = new String[] {"Gastro", "Neuro", "Brain", "Supply", "Disposal", "Admin",
                "Knowledge", "Lab", "Emergency", "Mobility"};
        String arr1[] = new String[] {"West", "East", "North", "South"};

        String center = "";
        do {
            System.out.print("Nhap ten trung tam: ");
            center = sc.nextLine();
        } while (!Arrays.asList(arr).contains(center));
        vec.add(center);

        String direction = "";
        do {
            System.out.print("Nhap huong: ");
            direction = sc.nextLine();
        } while (!Arrays.asList(arr1).contains(direction));
        vec.add(direction);

        // user enter all edges
        System.out.print("Nhap canh: ");
        Arrays.asList(sc.nextLine().split(" ")).forEach(vec::add);

        // // user enter src and dst
        // boolean notFound = false;
        // do {
        //     System.out.print("Nhap dinh nguon: ");
        //     String src = sc.nextLine();
        //     System.out.print("Nhap dinh dich: ");
        //     String dst = sc.nextLine();

        //     try {
        //         Arrays.asList(Read.findWay(src, dst).split(" ")).forEach(vec::add);
        //     } catch (Exception e) {
        //         notFound = true;
        //         System.out.println("Khong tim thay duong di");
        //     }
        // } while (notFound);

        sc.close();
    }

    public String[] locate() {
        // String arr[] = new String[] {"Gastro", "Neuro", "Brain", "Supply", "Disposal", "Admin",
        // "Knowledge", "Lab", "Emergency", "Mobility"};
        // String arr1[] = new String[] {"West", "East", "North", "South"};
        // String string[];
        // Random random = new Random();
        // int m;
        // m = random.nextInt(4);
        // if (m < 2) {
        // string = new String[5];
        // string[0] = arr[random.nextInt(10)];
        // string[1] = arr1[random.nextInt(2)];
        // string[2] = "E" + String.valueOf(random.nextInt(100) + 1);
        // string[3] = "-" + string[2];
        // string[4] = String.valueOf(random.nextInt(1003 / 5) + 1);
        // } else {
        // string = new String[9];
        // int temp, temp1, temp2;
        // string[0] = arr[random.nextInt(10)];
        // string[1] = arr1[random.nextInt(2)];
        // temp = random.nextInt(100) + 1;
        // string[2] = "E" + String.valueOf(temp);
        // string[3] = "-" + string[2];
        // do {
        // temp1 = random.nextInt(100) + 1;
        // } while (temp != temp1);
        // string[4] = "E" + String.valueOf(temp1);
        // string[5] = "-" + string[4];
        // do {
        // temp2 = random.nextInt(100) + 1;
        // } while (temp2 != temp1 && temp2 != temp);
        // string[6] = "E" + String.valueOf(temp2);
        // string[7] = "-" + string[6];
        // string[8] = String.valueOf(random.nextInt(1003 / 9) + 1);
        // }
        // return string;

        Random random = new Random();
        Vector<String> vec = new Vector<String>();
        assignPartition(vec);

        vec.add(String.valueOf(random.nextInt(1003 / (vec.size() + 1)) + 1));
        return vec.toArray(new String[vec.size()]);
    }

    public static void main(String[] args) {
    Function function = new Function();
    Vector<String> vec = new Vector<String>();
    function.assignPartition(vec);
    System.out.println(vec);
    }
}

