# Setup Instructions

## JDK Version Used
- **JDK Version**: JDK 17 or higher (recommended)
- **Note**: This project uses standard Java features compatible with JDK 8 and above

## Installation Steps

### 1. Install JDK
1. Download JDK from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
2. Install JDK following the installation wizard
3. Verify installation by opening a terminal/command prompt and running:
   ```bash
   java -version
   javac -version
   ```

### 2. Set JAVA_HOME (Optional but Recommended)
- **Windows**: 
  - Go to System Properties → Environment Variables
  - Add new system variable: `JAVA_HOME` = `C:\Program Files\Java\jdk-17` (adjust path as needed)
  - Add to PATH: `%JAVA_HOME%\bin`

- **Linux/Mac**:
  ```bash
  export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
  export PATH=$JAVA_HOME/bin:$PATH
  ```

## Hello World Program

### Create HelloWorld.java
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

### Compile and Run
```bash
# Compile
javac HelloWorld.java

# Run
java HelloWorld
```

### Expected Output
```
Hello, World!
```

## Compiling and Running LearnTrack

### Using Command Line

1. **Navigate to project root directory**:
   ```bash
   cd "C:\Users\nikhi\Documents\Airtribe projects"
   ```

2. **Compile all Java files**:
   ```bash
   javac -d bin src/com/airtribe/learntrack/**/*.java
   ```
   
   Or on Windows PowerShell:
   ```powershell
   javac -d bin src/com/airtribe/learntrack/Main.java src/com/airtribe/learntrack/**/*.java
   ```

3. **Run the application**:
   ```bash
   java -cp bin com.airtribe.learntrack.Main
   ```

### Using IDE (IntelliJ IDEA / Eclipse / VS Code)

1. **IntelliJ IDEA**:
   - Open project folder
   - Right-click on `Main.java` → Run 'Main.main()'

2. **Eclipse**:
   - Import project as Java Project
   - Right-click on `Main.java` → Run As → Java Application

3. **VS Code**:
   - Install Java Extension Pack
   - Open `Main.java` and click "Run" button

## Project Structure
```
Project Root/
├── src/
│   └── com/
│       └── airtribe/
│           └── learntrack/
│               ├── Main.java
│               ├── entity/
│               ├── repository/
│               ├── service/
│               ├── exception/
│               ├── util/
│               ├── constants/
│               └── enums/
└── docs/
```

## Troubleshooting

### Common Issues

1. **'javac' is not recognized**
   - Solution: Add JDK bin directory to PATH environment variable

2. **Package does not exist**
   - Solution: Ensure you're compiling from the correct directory and using proper package structure

3. **ClassNotFoundException**
   - Solution: Check classpath (-cp) includes the compiled classes directory (bin)

