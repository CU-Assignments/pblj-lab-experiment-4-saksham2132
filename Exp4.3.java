class TicketBookingSystem {
    private final boolean[] seats;  
    private final int totalSeats;

    public TicketBookingSystem(int totalSeats) {
        this.totalSeats = totalSeats;
        seats = new boolean[totalSeats];
    }

    public synchronized void bookSeat(int seatNumber, String userName, boolean isVIP) {
        if (seatNumber < 1 || seatNumber > totalSeats) {
            System.out.println(userName + ": Invalid seat number!");
            return;
        }

        if (seats[seatNumber - 1]) {
            System.out.println(userName + ": Seat " + seatNumber + " is already booked!");
        } else {
            seats[seatNumber - 1] = true;
            String status = isVIP ? "(VIP)" : "(Regular)";
            System.out.println(userName + " " + status + " booked seat " + seatNumber);
        }
    }

    public void displayBookings() {
        System.out.println("Current Bookings:");
        for (int i = 0; i < totalSeats; i++) {
            System.out.println("Seat " + (i + 1) + ": " + (seats[i] ? "Booked" : "Available"));
        }
    }
}

class UserThread extends Thread {
    private final TicketBookingSystem bookingSystem;
    private final int seatNumber;
    private final String userName;
    private final boolean isVIP;

    public UserThread(TicketBookingSystem bookingSystem, String userName, int seatNumber, boolean isVIP) {
        this.bookingSystem = bookingSystem;
        this.userName = userName;
        this.seatNumber = seatNumber;
        this.isVIP = isVIP;
    }

    @Override
    public void run() {
        bookingSystem.bookSeat(seatNumber, userName, isVIP);
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Test Case 1: No Seats Available Initially");
        TicketBookingSystem system1 = new TicketBookingSystem(5);
        system1.displayBookings();

        System.out.println("\nTest Case 2: Successful Booking");
        TicketBookingSystem system2 = new TicketBookingSystem(5);
        new UserThread(system2, "Anish", 1, true).start();
        new UserThread(system2, "Bobby", 2, false).start();
        new UserThread(system2, "Charlie", 3, true).start();

        System.out.println("\nTest Case 3: Thread Priorities (VIP First)");
        TicketBookingSystem system3 = new TicketBookingSystem(5);
        UserThread bobbyThread = new UserThread(system3, "Bobby", 4, false);
        UserThread anishThread = new UserThread(system3, "Anish", 4, true);
        bobbyThread.setPriority(Thread.MIN_PRIORITY); 
        anishThread.setPriority(Thread.MAX_PRIORITY); 
        anishThread.start();
        bobbyThread.start();

        System.out.println("\nTest Case 4: Preventing Double Booking");
        TicketBookingSystem system4 = new TicketBookingSystem(5);
        new UserThread(system4, "Anish", 1, true).start();
        new UserThread(system4, "Bobby", 1, false).start();

        System.out.println("\nTest Case 5: Booking After All Seats Are Taken");
        TicketBookingSystem system5 = new TicketBookingSystem(5);
        for (int i = 1; i <= 5; i++) {
            new UserThread(system5, "User" + i, i, false).start();
        }
        new UserThread(system5, "NewUser", 3, false).start();

        System.out.println("\nTest Case 6: Invalid Seat Selection");
        TicketBookingSystem system6 = new TicketBookingSystem(5);
        new UserThread(system6, "User1", 0, false).start(); 
        new UserThread(system6, "User2", 6, false).start(); 

        System.out.println("\nTest Case 7: Simultaneous Bookings (Concurrency Test)");
        TicketBookingSystem system7 = new TicketBookingSystem(5);
        for (int i = 1; i <= 10; i++) {
            boolean isVIP = i % 2 == 0; 
            new UserThread(system7, "User" + i, (i % 5) + 1, isVIP).start();
        }
    }
}
