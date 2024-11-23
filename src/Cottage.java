import java.time.LocalDate;
import java.util.*;

public class Cottage {
    private final String name;
    private final String category;
    private final int maxGuests;
    private final double basePrice;
    private final List<Amenity> amenities;
    private final Map<LocalDate, String> bookings;

    public Cottage(String name, String category, int maxGuests, double basePrice) {
        this.name = name;
        this.category = category;
        this.maxGuests = maxGuests;
        this.basePrice = basePrice;
        this.amenities = new ArrayList<>();
        this.bookings = new HashMap<>();
    }

    public void addAmenity(Amenity amenity) {
        amenities.add(amenity);
    }

    public int getMaxGuests() {
        return maxGuests + amenities.stream().mapToInt(Amenity::getGuestIncrease).sum();
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getCategory() {
        return category;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public Map<LocalDate, String> getBookings() {
        return bookings;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable(LocalDate date) {
        return !bookings.containsKey(date);
    }

    public void book(LocalDate date, String client) throws Exception {
        if (!isAvailable(date)) {
            throw new Exception("Cottage is already booked for this date.");
        }
        bookings.put(date, client);
    }

    public double calculateIncome(LocalDate date) {
        double discount = (date.getMonthValue() == 3 || date.getMonthValue() == 11) ? 0.8 : 1.0;
        return basePrice * discount;
    }

    public List<Amenity> getAllAmenities(List<Amenity> hotelAmenities) {
        List<Amenity> allAmenities = new ArrayList<>(amenities);
        allAmenities.addAll(hotelAmenities);
        return allAmenities;
    }
}
