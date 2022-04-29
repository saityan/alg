import java.util.ArrayList;
import java.util.Objects;

public class HomeworkEighth {
    public static void main(String[] args) {
        //Input
        int size = 10000;

        //Output
        WordsHashMap map = new WordsHashMap(size);
        map.put(new Word("a"));
        map.put(new Word("stable"));
        map.put(new Word("ZZZZZZZZZZZZZZZ"));
        map.display();
        System.out.println("\nAfter deletion:\n");
        map.delete(new Word("stable"));
        map.display();
        System.out.println();
        map.put(new Word("eventual"));
        map.display();
    }
}

class Word {
    private final String word;

    Word(String word)  {
        if (word.matches("\\w+"))
            this.word = word;
        else this.word = "_";
    }
    public String getWord() {
        return word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(this.word, ((Word) o).getWord());
    }

    @Override
    public String toString() {
        return this.word;
    }
}

class WordsHashMap {
    private final int size;
    private final ArrayList<ArrayList<Word>> arr;

    WordsHashMap(int size) {
        this.size = size;
        this.arr = new ArrayList<>(size);;
        for (int i = 0; i < size; i++) {
            this.arr.add(new ArrayList<>());
        }
    }

    public int getHash(Word word) {
        int index = 0;
        char[] charArr = word.getWord().toLowerCase().toCharArray();
        for (int i = charArr.length - 1; i >= 0; i--) {
            if (charArr[i] != '_')
                index += ((int) charArr[i] - 96) * Math.pow(2, charArr.length - i - 1);
        }
        if (index > this.size - 1)
            index = this.size - 1;
        return index;
    }

    public Word find (Word word) {
        int index = getHash(word);
        if (index < 0)
            return null;
        for (Word candidate: this.arr.get(index)) {
            if (candidate.equals(word)) {
                return candidate;
            }
        }
        return null;
    }

    public boolean put (Word word) {
        int index = getHash(word);
        if (index < 0 || word.getWord().equals("_"))
            return false;
        if (this.arr.get(index).contains(word))
            return false;
        this.arr.get(index).add(word);
        return true;
    }

    public boolean delete (Word word) {
        Word removable = this.find(word);
        if (removable == null)
            return false;
        int index = getHash(removable);
        if (index < 0 || word.getWord().equals("_"))
            return false;
        this.arr.get(index).remove(removable);
        return true;
    }
    public void display(int i) {
        if (i >= 0 && i < size) {
            for (Word word : this.arr.get(i)) {
                System.out.println(i + "\t" + word.toString());
            }
        }
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            if (!this.arr.get(i).isEmpty()) {
                display(i);
            }
        }
    }
}
