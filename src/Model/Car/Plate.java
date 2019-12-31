package Model.Car;

public class Plate {

    private String characters;
    private int numbers;

    public Plate(String chars, int nums) {
        this.characters = chars;
        this.numbers = nums;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Plate) {
            Plate p = (Plate) o;
            if (this.getCharacters().equals(p.getCharacters()) && this.getNumbers() == p.getNumbers()) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }

    public String getCharacters() {
        return characters;
    }

    public int getNumbers() {
        return numbers;
    }

}
