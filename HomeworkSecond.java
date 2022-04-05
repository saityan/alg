import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HomeworkSecond {

    public static void main(String[] args) {
        String[] manufacturers = getManufacturersList();
        int n = 10000;

        //Generate source array
        Notebook[] notebooksList = generateNotebooksList(n, manufacturers);

        //Radix sort for price
        radixSortPrice(notebooksList, n);

        //Insertion sort for RAM and manufacturer
        insertionSortRam(notebooksList, n);
        insertionSortManufacturer(notebooksList, n);

        //Print result
        printNotebooksList(notebooksList);
    }

    private static void countingSortPrice(Notebook[] notebooksList, int n, int exponent) {
        Notebook[] output = new Notebook[n];
        int[] count = new int[10];
        Arrays.fill(count, 0);
        for (int i = 0; i < n; i++) {
            count[(notebooksList[i].getPrice() / exponent) % 10]++;
        }
        for (int i = 1; i <10; i++) {
            count[i] += count[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            output[count[(notebooksList[i].getPrice() / exponent) % 10] - 1] = notebooksList[i];
            count[(notebooksList[i].getPrice() / exponent) % 10]--;
        }
        System.arraycopy(output, 0, notebooksList, 0, n);
    }

    private static void radixSortPrice(Notebook[] notebooksList, int n) {
        int maximum = getMaxPrice(notebooksList, n);
        for (int exponent = 10; maximum / exponent > 0;exponent *= 10) {
            countingSortPrice(notebooksList, n, exponent);
        }
    }

    private static void insertionSortRam (Notebook[] notebooksList, int n) {
        for (int i = 1; i < n; i++) {
            int j = i - 1;
            int value = notebooksList[i].getRam();
            Notebook notebook = notebooksList[i];
            while ( j >= 0
                    && notebooksList[j].getRam() > value
                    && notebooksList[j].getPrice() == notebooksList[j + 1].getPrice()
            ) {
                notebooksList[j + 1] = notebooksList[j];
                j--;
            }
            notebooksList[j + 1] = notebook;
        }
    }

    private static void insertionSortManufacturer (Notebook[] notebooksList, int n) {
        for (int i = 1; i < n; i++) {
            int j = i - 1;
            int value = (notebooksList[i]).getManufacturerCode();
            Notebook notebook = notebooksList[i];
            while ( j >= 0
                    && notebooksList[j].getManufacturerCode() > value
                    && notebooksList[j].getPrice() == notebooksList[j + 1].getPrice()
                    && notebooksList[j].getRam() == notebooksList[j + 1].getRam()
            ) {
                notebooksList[j + 1] = notebooksList[j];
                j--;
            }
            notebooksList[j + 1] = notebook;
        }
    }

    private static int getMaxPrice(Notebook[] notebooksList, int n) {
        int maximum = notebooksList[0].getPrice();
        for (int i = 1; i < n; i++) {
            if (notebooksList[i].getPrice() > maximum) {
                maximum = notebooksList[i].getPrice();
            }
        }
        return maximum;
    }

    private static String[] getManufacturersList() {
        return new String[] { "Xamiou", "Eser", "MacNote", "Asos", "Lenuvo" };
    }

    private static String getRandomManufacturer(String[] manufacturers) {
        return manufacturers[new Random().nextInt(manufacturers.length)];
    }

    private static int getRandomPrice() {
        return (ThreadLocalRandom.current().nextInt(0, 31) + 10) * 50;
    }

    private static int getRandomRam () {
        return (ThreadLocalRandom.current().nextInt(0, 6) + 1) * 4;
    }

    private static Notebook[] generateNotebooksList(int n, String[] manufacturers) {
        Notebook[] notebooksList = new Notebook[n];
        for (int i = 0; i < n; i++) {
            notebooksList[i] = new Notebook(getRandomManufacturer(manufacturers), getRandomPrice(), getRandomRam());
        }
        return notebooksList;
    }

    private static void printNotebooksList(Notebook[] notebooksList) {
        for (Notebook notebook : notebooksList) {
            System.out.print(notebook.getManufacturer() + "\t\t\t");
            int price = notebook.getPrice();
            if (price >= 1000) {
                System.out.print(price + "\t\t\t");
            } else { System.out.print(price + "\t\t\t\t"); }
            System.out.println(notebook.getRam());
        }
    }

    private static class Notebook {
        private final String manufacturer;
        private final int price;
        private final int ram;

        Notebook(String manufacturer, int price, int ram) {
            this.manufacturer = manufacturer;
            this.price = price;
            this.ram = ram;
        }

        private int getManufacturerCode() {
            int code = 0;
            String[] manufacturers = getManufacturersList();
            for (int i = 0; i < manufacturers.length; i++) {
                if (Objects.equals(manufacturer, manufacturers[i])) {
                    code = i;
                    break;
                }
            }
            return code;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public int getPrice() {
            return price;
        }

        public int getRam() {
            return ram;
        }
    }
}
