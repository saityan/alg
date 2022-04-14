import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HomeworkFifth_2 {
    public static List<Item[]> list = new ArrayList<>();

    public static void main(String[] args) {
        //Input
        int quantity = 5; //overall number of items
        int capacity = 7; //maximum weight in kilos
        Item[] items = getItemsList(quantity);
        displayItems(items);
        System.out.println();

        //Output
        arrange(capacity, quantity, items);
        displayItems(getMaximum(list));
    }

    private static Item[] getItemsList(int quantity) {
        Item[] items = new Item[quantity];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item();
        }
        return items;
    }

    private static void displayItems(Item[] items) {
        for (Item item: items) {
            System.out.println("[" + item.getName() + "\t\t" + item.getWorth() + "\t\t" + item.getWeight() + "kg]");
        }
    }

    public static void arrange(int capacity, int quantity, Item[] items) {
        if (quantity < 1)
            return;
        Item[] arr = new Item[quantity];
        for (int i = 0; i < quantity; i++) {
            arr[i] = items[quantity - 1];
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < items.length; j++) {
                arr[i] = items[j];
                boolean duplicate = false;
                for (int k = 0; k < arr.length; k++) {
                    for (int l = k + 1; l < arr.length; l++) {
                        if (k != l && equals(arr[k], arr[l]))
                            duplicate = true;
                    }
                }
                if (!duplicate && overallWeight(arr) <= capacity) {
                    Item[] suit = new Item[arr.length];
                    for (int k = 0; k < arr.length; k++) {
                        suit[k] = arr[k];
                    }
                    list.add(suit);
                }
            }
        }
        arrange(capacity, quantity - 1, items);
    }

    public static int overallWeight(Item[] items) {
        int weight = 0;
        for (Item item: items) {
            weight += item.getWeight();
        }
        return weight;
    }

    public static int overallValue(Item[] items) {
        int value = 0;
        for (Item item: items) {
            value += item.getWorth();
        }
        return value;
    }

    public static Item[] getMaximum(List<Item[]> list) {
        int maximum = 0;
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            int value = overallValue(list.get(i));
                if (value > maximum) {
                    maximum = value;
                    index = i;
                }
        }
        return list.get(index);
    }

    public static boolean equals (Item a, Item b) {
        return a.getName().equals(b.getName()) &&
                a.getWeight() == b.getWeight() && a.getWorth() == b.getWorth();
    }
}

class Item {
    private final String name;
    private final int worth;
    private final int weight;

    Item () {
        this.name = Items.getRandomName();
        this.worth = ThreadLocalRandom.current().nextInt(1, 30) * 1000;
        this.weight = ThreadLocalRandom.current().nextInt(1, 8);
    }

    public String getName() {
        return name;
    }

    public int getWorth() {
        return worth;
    }

    public int getWeight() {
        return weight;
    }
}

class Items {
    private static final String[] list = {
            "Apricot", "Katana", "Carpet", "Cup", "Piano", "Necklace", "Chair", "Jacket",
            "Book", "Toy", "Pen", "Cat", "Dignity", "Picture", "Ball", "Aspirin", "Wheel",
    };

    public static String getRandomName() {
        return list[ThreadLocalRandom.current().nextInt(0, list.length)];
    }
}
