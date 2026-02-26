package namedEntity;

/**
 * This class models the concept of a named entity.
 * <p>
 * A {@code NamedEntity} has a {@code name}, a {@code category} (such as
 * "PERSON", "LOCATION", "COMPANY", etc.),
 * and a {@code frequency} indicating how many times it appears in the processed
 * text.
 * </p>
 */
public class NamedEntity {

    String name;
    String category;
    int frequency;

    public NamedEntity(String name, String category, int frequency) {
        super();
        this.name = name;
        this.category = category;
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void increaseFrequency() {
        this.frequency++;
    }

    @Override
    public String toString() {
        return "ObjectNamedEntity [name=" + name + ", frequency=" + frequency + "]";
    }

    public void prettyPrint() {
        System.out.println(this.getName() + " " + this.getFrequency());
    }

}
