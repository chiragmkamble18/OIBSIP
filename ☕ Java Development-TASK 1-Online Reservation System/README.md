```markdown
# Online Reservation System

A **Java Swing**-based railway/transport reservation system with **SQLite** persistence. Users can log in, book tickets (auto‑generated PNR), and cancel reservations by PNR number.  
This project was developed as part of the **Oasis Infobyte Internship Program (OIBSIP)** – **Java Development Track (Level 2)**.

---

## 📋 Features

- ✅ **Login Form** – username + password fields; access denied for invalid credentials  
- ✅ **Reservation Form** – passenger name, train number (auto‑populates train name), class type, journey date, source/destination  
- ✅ **PNR Generation** – unique auto‑generated ID per booking (timestamp + random digits)  
- ✅ **Confirmation Dialog** – displays full booking details after successful reservation  
- ✅ **Cancellation Form** – fetch booking details by PNR and delete with confirmation  
- ✅ **Input Validation** – no empty required fields, valid date format (`yyyy‑MM‑dd`), numeric train number  
- ✅ **SQL Injection Prevention** – all database operations use `PreparedStatement`  

---

## 🛠️ Tech Stack

| Component       | Technology                     |
|-----------------|--------------------------------|
| Frontend        | Java Swing (JFrame, JDialog)   |
| Database        | SQLite (embedded)              |
| JDBC Driver     | sqlite‑jdbc (v3.46.0.0)        |
| Logging         | SLF4J + slf4j‑simple           |
| Build           | Manual `javac` / `java`        |

---

## 📂 Project Structure

OIBSIP/JavaDev-L2-OnlineReservationSystem/

├── src/

│   └── com/reservation/

│       ├── Main.java              // Entry point

│       ├── DatabaseManager.java   // DB connection & operations

│       ├── LoginUI.java           // Login window

│       ├── MainMenuUI.java        // Main menu after login

│       ├── Reservation.java       // Model class

│       ├── ReservationUI.java     // Booking form

│       └── CancellationUI.java    // Cancellation form

├── lib/

│   ├── sqlite-jdbc-3.46.0.0.jar

│   ├── slf4j-api-2.0.9.jar

│   └── slf4j-simple-2.0.9.jar

---

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK) 8** or later  
- **Git** (optional, for cloning)

### Download Dependencies

Place the required JAR files in the `lib/` folder:

- `sqlite-jdbc-3.46.0.0.jar` – [Download](https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.46.0.0/sqlite-jdbc-3.46.0.0.jar)  
- `slf4j-api-2.0.9.jar` – [Download](https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar)  
- `slf4j-simple-2.0.9.jar` – [Download](https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar)

### Compilation

Open a terminal/command prompt in the project root (`OIBSIP/JavaDev-L2-OnlineReservationSystem/`) and run:

```bash
javac -cp ".;lib\sqlite-jdbc-3.46.0.0.jar;lib\slf4j-api-2.0.9.jar;lib\slf4j-simple-2.0.9.jar" src\com\reservation\*.java
```

### First Run

On the first execution, the application automatically creates a `reservation.db` SQLite file in the project root and populates it with:

- `users` table with default credentials: `admin` / `admin`
- `trains` table with sample trains:
  - 12001 – Rajdhani Express
  - 12002 – Shatabdi Express
  - 12627 – Karnataka Express
  - 12951 – Mumbai Rajdhani

---

## 🎮 Usage

### Login
- **Username**: `admin`  
- **Password**: `admin`

### Book a Ticket
1. Click **Book Ticket** on the main menu.
2. Fill in all fields:
   - **Passenger Name** – required
   - **Train Number** – numeric (e.g., `12001`) – train name auto‑populates
   - **Class Type** – select from dropdown (Sleeper, AC 3‑Tier, AC 2‑Tier, AC First Class)
   - **Journey Date** – format `yyyy‑MM‑dd` (e.g., `2026‑07‑12`)
   - **Source Station** – required
   - **Destination Station** – required
3. Click **Book**.
4. A confirmation dialog shows your **PNR** and all booking details.  
   The booking is saved in the database.

### Cancel a Ticket
1. Click **Cancel Ticket** on the main menu.
2. Enter the **PNR** number.
3. Click **Fetch Details** – the booking information is displayed.
4. Click **Cancel Ticket** and confirm in the pop‑up.
5. The booking is permanently removed from the database.

## 📝 Additional Notes

- The database file (`reservation.db`) is stored in the project root. You can inspect it with any SQLite browser.
- Passwords are stored in plain text (for demonstration); in a production environment, use hashing.
- PNR generation uses a timestamp + random digits – suitable for this exercise.

---

## 👤 Author

**Chirag Kamble**  
- GitHub: [chiragmkamble18](https://github.com/chiragmkamble18)  
- Email: chiragmkamble18@gmail.com  

---

## 📄 License

This project is for educational purposes as part of the OIBSIP internship. Free to use and modify.

---

**Happy Coding!** 🚀
