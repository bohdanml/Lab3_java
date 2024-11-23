import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelManagement hotel = new HotelManagement();

        // Додавання початкових зручностей
        hotel.addHotelAmenity(new Amenity("WiFi", 0, 5));
        hotel.addHotelAmenity(new Amenity("Kitchen", 0, 10));
        hotel.addHotelAmenity(new Amenity("Sofa", 1, 15));
        hotel.addHotelAmenity(new Amenity("SPA", 0, 50));
        hotel.addHotelAmenity(new Amenity("Football Field", 0, 20));

        boolean running = true;
        while (running) {
            System.out.println("\nМеню:");
            System.out.println("1. Додати зручність до готелю");
            System.out.println("2. Додати будиночок");
            System.out.println("3. Забронювати будиночок");
            System.out.println("4. Переглянути всі будиночки із заданою зручністю");
            System.out.println("5. Переглянути всі будиночки за категорією");
            System.out.println("6. Показати дохід готелю");
            System.out.println("7. Переглянути будиночки за іменем");
            System.out.println("8. Перевірити вільні дати для будиночка");
            System.out.println("9. Вивести статистику доходів і витрат");
            System.out.println("0. Вийти");
            System.out.print("Ваш вибір: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Доступні зручності для додавання до готелю:");
                    for (int i = 0; i < hotel.getHotelAmenities().size(); i++) {
                        System.out.println((i + 1) + ". " + hotel.getHotelAmenities().get(i).getName());
                    }
                    System.out.print("Виберіть зручність для додавання (номер): ");
                    int amenityChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (amenityChoice > 0 && amenityChoice <= hotel.getHotelAmenities().size()) {
                        Amenity selectedAmenity = hotel.getHotelAmenities().get(amenityChoice - 1);
                        System.out.println("Зручність додано: " + selectedAmenity.getName());
                    } else {
                        System.out.println("Невірний вибір.");
                    }
                }
                case 2 -> {
                    System.out.println("Додати новий будиночок:");
                    System.out.print("Ім'я будиночка: ");
                    String name = scanner.nextLine();
                    System.out.print("Категорія будиночку (люкс/стандарт): ");
                    String category = scanner.nextLine();
                    System.out.print("Максимальна кількість гостей: ");
                    int maxGuests = scanner.nextInt();
                    System.out.print("Базова ціна: ");
                    double basePrice = scanner.nextDouble();
                    scanner.nextLine();

                    Cottage cottage = new Cottage(name, category, maxGuests, basePrice);

                    System.out.println("Доступні зручності для цього будиночку:");
                    for (int i = 0; i < hotel.getHotelAmenities().size(); i++) {
                        System.out.println((i + 1) + ". " + hotel.getHotelAmenities().get(i).getName());
                    }
                    System.out.print("Виберіть зручність для додавання (введіть кілька номерів через пробіл): ");
                    String[] amenitiesChoices = scanner.nextLine().split(" ");
                    for (String choiceIndex : amenitiesChoices) {
                        try {
                            int amenityChoice = Integer.parseInt(choiceIndex);
                            if (amenityChoice > 0 && amenityChoice <= hotel.getHotelAmenities().size()) {
                                Amenity selectedAmenity = hotel.getHotelAmenities().get(amenityChoice - 1);
                                cottage.addAmenity(selectedAmenity);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Невірний ввід.");
                        }
                    }
                    hotel.addCottage(cottage);
                    System.out.println("Будиночок додано.");
                }
                case 3 -> {
                    System.out.println("Список доступних будиночків:");
                    for (int i = 0; i < hotel.getCottages().size(); i++) {
                        Cottage cottage = hotel.getCottages().get(i);
                        System.out.println((i + 1) + ". " + cottage.getName());
                    }

                    System.out.print("Виберіть будиночок (номер): ");
                    int cottageChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (cottageChoice > 0 && cottageChoice <= hotel.getCottages().size()) {
                        Cottage selectedCottage = hotel.getCottages().get(cottageChoice - 1);

                        System.out.print("Введіть дату бронювання (YYYY-MM-DD): ");
                        String dateInput = scanner.nextLine();
                        System.out.print("Введіть ім'я клієнта: ");
                        String clientName = scanner.nextLine();

                        try {
                            LocalDate date = LocalDate.parse(dateInput);
                            selectedCottage.book(date, clientName);
                            System.out.println("Будиночок успішно заброньовано на " + date + " для клієнта " + clientName);
                        } catch (Exception e) {
                            System.out.println("Помилка: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Невірний вибір будиночка.");
                    }
                }
                case 4 -> {
                    System.out.print("Введіть назву зручності: ");
                    String amenityName = scanner.nextLine();
                    List<Cottage> cottages = hotel.findCottagesByAmenity(amenityName);
                    System.out.println("Будиночки з цією зручністю:");
                    cottages.forEach(cottage -> System.out.println(cottage.getName()));
                }
                case 5 -> {
                    System.out.print("Введіть категорію будиночка: ");
                    String category = scanner.nextLine();
                    List<Cottage> cottages = hotel.findCottagesByCategory(category);
                    System.out.println("Будиночки в категорії " + category + ":");
                    cottages.forEach(cottage -> System.out.println(cottage.getName()));
                }
                case 6 -> {
                    double totalIncome = hotel.calculateTotalIncome();
                    System.out.println("Загальний дохід готелю: $" + totalIncome);
                }
                case 7 -> {
                    System.out.print("Введіть ім'я будиночка для пошуку: ");
                    String name = scanner.nextLine();
                    Cottage foundCottage = hotel.getCottages().stream()
                            .filter(cottage -> cottage.getName().equalsIgnoreCase(name))
                            .findFirst()
                            .orElse(null);
                    if (foundCottage != null) {
                        System.out.println("Знайдено будиночок: " + foundCottage.getName());
                    } else {
                        System.out.println("Будиночок не знайдено.");
                    }
                }
                case 8 -> {
                    System.out.print("Введіть категорію будиночка: ");
                    String category = scanner.nextLine();
                    List<LocalDate> availableDates = hotel.getAvailableDates(category);
                    System.out.println("Вільні дати для будиночків категорії " + category + ":");
                    availableDates.forEach(date -> System.out.println(date));
                }
                case 9 -> {
                    double totalIncome = hotel.calculateTotalIncome();
                    double totalExpenses = hotel.calculateTotalExpenses();
                    System.out.println("Статистика готелю:");
                    System.out.println("Загальний дохід: $" + totalIncome);
                    System.out.println("Загальні витрати: $" + totalExpenses);
                    System.out.println("Чистий дохід: $" + (totalIncome - totalExpenses));
                }
                case 0 -> running = false;
                default -> System.out.println("Невірний вибір.");
            }
        }
    }
}
