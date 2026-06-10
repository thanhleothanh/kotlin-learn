# Day 3: Data Classes, Sealed Classes & Objects

> For Java 17+ developers learning Kotlin

---

## 1. Data Classes

| Feature | Java POJO | Java Record | Kotlin data class |
|---------|-----------|-------------|-------------------|
| Boilerplate | High | Low | Zero |
| Immutability | Manual | Built-in | Built-in (val) |
| equals/hashCode | Manual | Auto | Auto |
| toString | Manual | Auto | Auto |
| copy | Manual | No | Auto copy() |
| Destructuring | No | componentN() | componentN() |
| Inheritance | Yes | No (final) | Yes (open) |
| Default values | No | No | Yes |

```kotlin
// Kotlin: one line replaces 50+ lines of Java
data class User(val name: String, val age: Int, val email: String = "")

// Auto-generates:
// - constructor
// - getters (val properties)
// - equals(), hashCode()
// - toString()
// - copy()
// - componentN()

// Usage
val user = User("Alice", 25)
val older = user.copy(age = 26)    // Copy with modification
val (name, age) = user             // Destructuring
```

---

## 2. Sealed Classes

| Feature | Java enum | Java sealed (17+) | Kotlin sealed |
|---------|-----------|-------------------|---------------|
| Fixed values | Yes | No | No |
| Data per state | No | Record | data class |
| Behavior | Limited | Methods | Methods |
| Exhaustive when | Yes | Pattern matching | when expression |
| Power | Limited | Limited | Full |

```kotlin
// Kotlin: restricted hierarchy with exhaustive when
sealed class Result {
    data class Success(val data: String) : Result()
    data class Error(val message: String) : Result()
    data object Loading : Result()
}

fun handle(result: Result) = when (result) {
    is Result.Success -> "Got: ${result.data}"
    is Result.Error -> "Error: ${result.message}"
    is Result.Loading -> "Loading..."
    // No else needed — compiler ensures exhaustive!
}
```

---

## 3. Companion Objects (replaces static)

| Feature | Java static | Kotlin companion object |
|---------|-------------|------------------------|
| Class-level members | `static` keyword | `companion object { }` |
| Factory method | `static create()` | `companion object { fun create() }` |
| Constants | `static final` | `const val` in companion |
| Implement interface | ❌ | ✅ |
| Extension | ❌ | ✅ `fun C.f()` |
| Singleton | Complex patterns | `object Name { }` |

```kotlin
class User private constructor(val name: String) {
    companion object : Logger {     // Can implement interfaces!
        const val MAX_USERS = 100   // Like static final
        fun create(name: String) = User(name)  // Factory
        override fun log(msg: String) = println(msg)
    }
}

User.create("Alice")     // Factory call
User.MAX_USERS           // Constant access
```

---

## 4. Object Declaration (Singleton)

| Feature | Java Singleton | Kotlin object |
|---------|----------------|---------------|
| Implementation | 10-20 lines | 1 line |
| Thread safety | Manual | Built-in |
| Lazy loading | Manual | `lazy` delegate |
| Serialization | Complex | Simple |

```kotlin
// Kotlin: one line = thread-safe singleton
object DatabaseConfig {
    val host = "localhost"
    val port = 5432
    fun connect() = "Connecting to $host:$port"
}

// Java equivalent:
// public class DatabaseConfig {
//     private static volatile DatabaseConfig instance;
//     public static DatabaseConfig getInstance() {
//         if (instance == null) {
//             synchronized (DatabaseConfig.class) {
//                 if (instance == null) {
//                     instance = new DatabaseConfig();
//                 }
//             }
//         }
//         return instance;
//     }
// }
```

---

## 5. Object Expression (Anonymous Object)

```kotlin
// Kotlin: object expression
val listener = object : ClickListener {
    override fun onClick(viewId: String) {
        println("Clicked: $viewId")
    }
}

// Can also create anonymous class without interface
val obj = object {
    val x = 10
    fun sum() = x + x
}
```

---

## Quick Reference: Java → Kotlin

```java
// Java
public class User {
    private final String name;
    private final int age;
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    // equals, hashCode, toString...
}

public class MathUtils {
    public static final double PI = 3.14;
    public static int add(int a, int b) { return a + b; }
}

// Singleton
public class Config {
    private static Config instance;
    public static Config getInstance() { ... }
}
```

```kotlin
// Kotlin
data class User(val name: String, val age: Int)

class MathUtils {
    companion object {
        const val PI = 3.14
        fun add(a: Int, b: Int) = a + b
    }
}

// Singleton
object Config { ... }
```

---

## Key Things to Remember

1. **data class** — auto-generates equals/hashCode/toString/copy/componentN
2. **val in data class** — immutable property (preferred)
3. **copy()** — create modified copies without mutation
4. **sealed class** — restrict hierarchy, exhaustive when
5. **sealed interface** — same as sealed class but for interfaces
6. **companion object** — replaces static, can implement interfaces
7. **object declaration** — thread-safe singleton (one line!)
8. **object expression** — anonymous object (like Java anonymous class)
9. **const val** — compile-time constant (like static final)
10. **@JvmStatic** — expose companion methods as true static for Java interop
