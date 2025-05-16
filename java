import java.util.*;

class Product {
    private static int nextId = 1;
    private int id;
    private String name;
    private int quantity;
    private double price;

    public Product(String name, int quantity, double price) {
        this.id = nextId++;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }

    public void reduceStock(int soldQuantity) {
        if (soldQuantity <= quantity) {
            quantity -= soldQuantity;
        } else {
            System.out.println("❌ Insufficient stock!");
        }
    }

    public String toString() {
        return String.format("ID: %d | Name: %s | Quantity: %d | Price: $%.2f", id, name, quantity, price);
    }
}

class Inventory {
    private Map<Integer, Product> products = new HashMap<>();

    public void addProduct(String name, int quantity, double price) {
        Product product = new Product(name, quantity, price);
        products.put(product.getId(), product);
        System.out.println("✅ Product added: " + product);
    }

    public void updateProduct(int id, String name, int quantity, double price) {
        Product p = products.get(id);
        if (p != null) {
            p.setName(name);
            p.setQuantity(quantity);
            p.setPrice(price);
            System.out.println("✅ Product updated.");
        } else {
            System.out.println("❌ Product not found.");
        }
    }

    public void deleteProduct(int id) {
        if (products.remove(id) != null) {
            System.out.println("✅ Product deleted.");
        } else {
            System.out.println("❌ Product not found.");
        }
    }

    public void listProducts() {
        if (products.isEmpty()) {
            System.out.println("📭 No products in inventory.");
            return;
        }
        System.out.println("📦 Current Inventory:");
        for (Product p : products.values()) {
            System.out.println(p);
        }
    }

    public void searchByName(String name) {
        boolean found = false;
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("🔍 No products found with name: " + name);
        }
    }

    public void sellProduct(int id, int quantity) {
        Product p = products.get(id);
        if (p != null) {
            if (p.getQuantity() >= quantity) {
                p.reduceStock(quantity);
                double total = quantity * p.getPrice();
                System.out.printf("✅ Sold %d units of %s. Total: $%.2f\n", quantity, p.getName(), total);
                if (p.getQuantity() < 5) {
                    System.out.println("⚠  Low stock warning for: " + p.getName());
                }
            } else {
                System.out.println("❌ Not enough stock.");
            }
        } else {
            System.out.println("❌ Product not found.");
        }
    }
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventory inventory = new Inventory();

        while (true) {
            System.out.println("\n=== Inventory System Menu ===");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. List Products");
            System.out.println("5. Search Product");
            System.out.println("6. Sell Product");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int qty = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    inventory.addProduct(name, qty, price);
                    break;

                case "2":
                    System.out.print("Enter product ID to update: ");
                    int updId = Integer.parseInt(scanner.nextLine());
                    System.out.print("New name: ");
                    String newName = scanner.nextLine();
                    System.out.print("New quantity: ");
                    int newQty = Integer.parseInt(scanner.nextLine());
                    System.out.print("New price: ");
                    double newPrice = Double.parseDouble(scanner.nextLine());
                    inventory.updateProduct(updId, newName, newQty, newPrice);
                    break;

                case "3":
                    System.out.print("Enter product ID to delete: ");
                    int delId = Integer.parseInt(scanner.nextLine());
                    inventory.deleteProduct(delId);
                    break;

                case "4":
                    inventory.listProducts();
                    break;

                case "5":
                    System.out.print("Enter search term: ");
                    String search = scanner.nextLine();
                    inventory.searchByName(search);
                    break;

                case "6":
                    System.out.print("Enter product ID to sell: ");
                    int sellId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter quantity to sell: ");
                    int sellQty = Integer.parseInt(scanner.nextLine());
                    inventory.sellProduct(sellId, sellQty);
                    break;

                case "0":
                    System.out.println("👋 Exiting. Goodbye!");
                    return;

                default:
                    System.out.println("❗ Invalid choice. Try again.");
            }
        }
    }
}
