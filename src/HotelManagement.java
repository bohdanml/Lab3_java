import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class HotelManagement {
    private final List<Amenity> hotelAmenities;
    private final List<Cottage> cottages;

    public HotelManagement() {
        hotelAmenities = new ArrayList<>();
        cottages = new ArrayList<>();
    }

    public void addHotelAmenity(Amenity amenity) {
        hotelAmenities.add(amenity);
    }

    public void addCottage(Cottage cottage) {
        cottages.add(cottage);
    }

    public List<Amenity> getHotelAmenities() {
        return hotelAmenities;
    }

    public List<Cottage> getCottages() {
        return cottages;
    }

    public List<Cottage> findCottagesByAmenity(String amenityName) {
        return cottages.stream()
                .filter(cottage -> cottage.getAmenities().stream().anyMatch(a -> a.getName().equals(amenityName)))
                .collect(Collectors.toList());
    }

    public List<Cottage> findCottagesByCategory(String category) {
        return cottages.stream()
                .filter(cottage -> cottage.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public double calculateTotalIncome() {
        return cottages.stream()
                .flatMap(cottage -> cottage.getBookings().keySet().stream().map(date -> cottage.calculateIncome(date)))
                .reduce(0.0, Double::sum);
    }

    public Optional<Cottage> getAvailableCottage(String category, LocalDate date) {
        return findCottagesByCategory(category).stream()
                .filter(cottage -> cottage.isAvailable(date))
                .findFirst();
    }

    public List<LocalDate> getAvailableDates(String category) {
        List<LocalDate> availableDates = new ArrayList<>();
        for (Cottage cottage : findCottagesByCategory(category)) {
            for (LocalDate date = LocalDate.now(); date.isBefore(LocalDate.now().plusMonths(3)); date = date.plusDays(1)) {
                if (cottage.isAvailable(date)) {
                    availableDates.add(date);
                }
            }
        }
        return availableDates;
    }

    public double calculateTotalExpenses() {
        double cottagesExpenses = cottages.stream()
                .flatMap(cottage -> cottage.getAmenities().stream())
                .mapToDouble(Amenity::getCost)
                .sum();

        double hotelAmenitiesExpenses = hotelAmenities.stream()
                .mapToDouble(Amenity::getCost)
                .sum();

        return cottagesExpenses + hotelAmenitiesExpenses;
    }
}
