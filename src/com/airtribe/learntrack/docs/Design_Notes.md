# Design Notes

## Why ArrayList Instead of Array?

### Arrays Limitations
1. **Fixed Size**: Once created, array size cannot be changed
   ```java
   String[] students = new String[10]; // Fixed at 10 elements
   // Cannot add 11th student without creating new array
   ```

2. **Memory Management**: Need to manually track available slots
3. **No Built-in Methods**: Limited utility methods for common operations

### ArrayList Advantages
1. **Dynamic Size**: Automatically grows/shrinks as needed
   ```java
   ArrayList<Student> students = new ArrayList<>();
   students.add(new Student(...)); // Automatically expands
   ```

2. **Rich API**: Built-in methods like `add()`, `remove()`, `contains()`, `size()`, etc.
3. **Type Safety**: With generics, ensures type safety at compile time
4. **Better for Collections**: Designed specifically for managing collections of objects

### In LearnTrack
- **StudentRepository**: Uses `ArrayList<Student>` to store students dynamically
- **CourseRepository**: Uses `ArrayList<Course>` for courses
- **EnrollmentRepository**: Uses `ArrayList<Enrollment>` for enrollments

**Example from code**:
```java
private List<Student> students; // ArrayList implementation

public void addStudent(Student student) {
    students.add(student); // Simple, no size management needed
}
```

## Where Static Members Are Used and Why

### 1. IdGenerator Class
**Location**: `com.airtribe.learntrack.util.IdGenerator`

**Static Fields**:
```java
private static int studentIdCounter = 1000;
private static int courseIdCounter = 2000;
private static int enrollmentIdCounter = 3000;
```

**Why Static?**
- **Shared State**: All instances need to share the same counter
- **No Object Needed**: Can generate IDs without creating IdGenerator objects
- **Memory Efficient**: Only one copy exists in memory

**Static Methods**:
```java
public static int getNextStudentId() {
    return ++studentIdCounter;
}
```

**Usage**:
```java
int id = IdGenerator.getNextStudentId(); // No object creation needed
```

### 2. InputValidator Class
**Location**: `com.airtribe.learntrack.util.InputValidator`

**Why Static Methods?**
- **Utility Functions**: Pure functions that don't need instance state
- **No State**: Methods don't depend on object state
- **Convenience**: Can call directly without instantiation

**Example**:
```java
if (InputValidator.isValidInteger(input)) {
    // Process input
}
```

### 3. MenuOptions and AppConstants
**Location**: `com.airtribe.learntrack.constants.*`

**Why Static Final?**
- **Constants**: Values that never change
- **Shared Access**: Available to all classes without instantiation
- **Memory Efficient**: Single copy in memory

**Example**:
```java
public static final int STUDENT_MANAGEMENT = 1;
public static final String APP_NAME = "LearnTrack";
```

### Static vs Instance Summary

| Aspect | Static | Instance |
|--------|--------|----------|
| **Belongs to** | Class | Object |
| **Memory** | One copy | One per object |
| **Access** | ClassName.member | object.member |
| **State** | Shared | Individual |
| **Use When** | Shared utility, constants | Object-specific data |

## Where Inheritance Is Used and What We Gained

### Inheritance Hierarchy

```
        Person (Base Class)
            │
     ┌──────┴──────┐
     │             │
  Student      Trainer (Future)
```

### Implementation

**Base Class - Person**:
```java
public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    
    public String getDisplayName() {
        return firstName + " " + lastName;
    }
}
```

**Derived Class - Student**:
```java
public class Student extends Person {
    private String batch;
    private boolean active;
    
    // Uses super() to call parent constructor
    public Student(int id, String firstName, String lastName, String email, String batch, boolean active) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = active;
    }
    
    // Method overriding - specialized behavior
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (Batch: " + batch + ")";
    }
}
```

### What We Gained from Inheritance

#### 1. **Code Reusability**
- Common fields (id, firstName, lastName, email) defined once in Person
- Student automatically inherits these fields
- No need to duplicate code

#### 2. **Polymorphism**
- Can treat Student as Person
- Method overriding allows specialized behavior
- Example: `getDisplayName()` behaves differently for Student vs Person

#### 3. **Maintainability**
- Changes to Person automatically reflect in Student
- Single point of modification for common attributes

#### 4. **Extensibility**
- Easy to add new types (e.g., Trainer) that extend Person
- Consistent structure across all person types

#### 5. **Type Safety**
- Can use Person reference to hold Student objects
- Enables polymorphic collections

### Example of Polymorphism
```java
Person person = new Student(1, "John", "Doe", "john@example.com", "2024", true);
System.out.println(person.getDisplayName()); 
// Output: "John Doe (Batch: 2024)" - calls Student's overridden method
```

### Benefits Summary

| Benefit | Description |
|---------|-------------|
| **DRY Principle** | Don't Repeat Yourself - common code in one place |
| **Consistency** | All person types share same base structure |
| **Flexibility** | Easy to extend with new types |
| **Polymorphism** | Same interface, different implementations |
| **Maintainability** | Changes in base class affect all derived classes |

## Additional Design Decisions

### 1. Repository Pattern
- **Separation of Concerns**: Data access logic separated from business logic
- **Testability**: Easy to mock repositories for testing
- **Future-proof**: Can swap in-memory storage with database later

### 2. Service Layer
- **Business Logic**: All business rules in service classes
- **Validation**: Service validates before repository operations
- **Transaction-like**: Service coordinates multiple repository operations

### 3. Exception Handling
- **Custom Exceptions**: `EntityNotFoundException` for better error messages
- **Graceful Degradation**: Application doesn't crash on errors
- **User-friendly**: Clear error messages for users

### 4. Encapsulation
- **Private Fields**: All entity fields are private
- **Public Getters/Setters**: Controlled access to fields
- **Data Protection**: Prevents direct field manipulation

### 5. Package Organization
- **Logical Grouping**: Related classes in same package
- **Access Control**: Package-private access where appropriate
- **Clear Structure**: Easy to navigate and understand

