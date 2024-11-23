public class Amenity {
    private final String name;
    private final int guestIncrease;
    private final double cost;

    public Amenity(String name, int guestIncrease, double cost) {
        this.name = name;
        this.guestIncrease = guestIncrease;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getGuestIncrease() {
        return guestIncrease;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name + " (+" + guestIncrease + " guests, $" + cost + ")";
    }
}
