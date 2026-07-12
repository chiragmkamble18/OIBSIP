I see the issue — the README content you extracted from the image has **broken formatting** (missing newlines, incorrect indentation, malformed tables).  
You already have the **proper Markdown version** in your message (the long one with ✅, tables, code blocks, etc.).  

Below is the **final, polished README.md** that you should copy into your repository. It matches the structure you want and is ready to use.

---

## 📄 Final README.md (copy this)

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

```
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
├── screenshots/                   (Add your screenshots here)
│   ├── login.png
│   ├── main-menu.png
│   ├── booking-form.png
│   ├── confirmation.png
│   └── cancellation.png
├── README.md
└── .gitignore
```

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

> **Alternative**: Use an older SQLite driver (e.g., `3.36.0.3`) which does **not** require SLF4J – you can then skip the two SLF4J JARs.

---

### Compilation

Open a terminal/command prompt in the project root (`OIBSIP/JavaDev-L2-OnlineReservationSystem/`) and run:

```bash
javac -cp ".;lib\sqlite-jdbc-3.46.0.0.jar;lib\slf4j-api-2.0.9.jar;lib\slf4j-simple-2.0.9.jar" src\com\reservation\*.java
```

> **Linux/macOS**: Use `:` instead of `;` and `/` for paths.

### Execution

```bash
java -cp ".;lib\sqlite-jdbc-3.46.0.0.jar;lib\slf4j-api-2.0.9.jar;lib\slf4j-simple-2.0.9.jar;src" com.reservation.Main
```

If you see a native‑access warning, add the JVM flag:

```bash
java --enable-native-access=ALL-UNNAMED -cp ".;lib\sqlite-jdbc-3.46.0.0.jar;lib\slf4j-api-2.0.9.jar;lib\slf4j-simple-2.0.9.jar;src" com.reservation.Main
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

---

## 📸 Screenshots

Add your screenshots here (place them in the `screenshots/` folder and reference them):

| Login | Main Menu |
|-------|-----------|
| ![Login](screenshots/login.png) | ![Main Menu](screenshots/main-menu.png) |

| Booking Form | Confirmation |
|--------------|--------------|
| ![Booking Form](screenshots/booking-form.png) | ![Confirmation](screenshots/confirmation.png) |

| Cancellation |
|--------------|
| ![Cancellation](screenshots/cancellation.png) |

---

## 🧪 Troubleshooting

| Error | Solution |
|-------|----------|
| `ClassNotFoundException: org.sqlite.JDBC` | Ensure the SQLite JAR is in the classpath and the filename matches. |
| `ClassNotFoundException: org.slf4j.LoggerFactory` | Add the SLF4J JARs (api + simple) to the classpath, or use an older SQLite driver. |
| `SQLITE_NOTADB: file is not a database` | Delete the existing `reservation.db` file and restart – it will create a fresh database. |
| `No suitable driver found` | The `DatabaseManager` includes a static block to load the driver – ensure the JAR is present. |

---

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
```

---

## ✅ Action Items

1. **Copy the above content** into `OIBSIP/JavaDev-L2-OnlineReservationSystem/README.md`.
2. **Add your screenshots** in the `screenshots/` folder and update the image paths if needed.
3. **Commit and push** the final version.

Now your README will be clean, well‑formatted, and professional – exactly as required. Good luck with your submission! 🎉
