/**
 * Day 3 - Companion Objects & Object Declarations in Kotlin
 *
 * Kotlin has NO "static" keyword! Instead:
 *   - companion object — static-like members
 *   - object declaration — singleton
 *   - object expression — anonymous object
 *
 * Benefits:
 *   - Companion object can implement interfaces
 *   - Can have factory methods
 *   - Can have companion object extensions
 *   - Much cleaner than Java's static!
 */

// ═══════════════════════════════════════════════════════════════
// 1. COMPANION OBJECT (replaces static)
// ═══════════════════════════════════════════════════════════════

// Kotlin: companion object replaces static members
class MathUtils private constructor() {

    // Companion object — like static members
    companion object {
        const val PI = 3.14159
        const val MAX_SIZE = 100

        // Factory method
        fun create(): MathUtils {
            return MathUtils()
        }

        // Static-like method
        fun add(a: Int, b: Int): Int = a + b
    }

    // Instance method
    fun calculate(): Double = PI * 2
}

// Java equivalent:
// public class MathUtils {
//     public static final double PI = 3.14159;
//     public static int add(int a, int b) { return a + b; }
//     public static MathUtils create() { return new MathUtils(); }
// }

// ═══════════════════════════════════════════════════════════════
// 2. COMPANION OBJECT WITH INTERFACE
// ═══════════════════════════════════════════════════════════════

// Kotlin: Companion object can implement interfaces!
interface Logger {
    fun log(message: String)
}

class AppConfig private constructor() {
    val host: String = "localhost"
    val port: Int = 8080

    // Companion object can implement interface
    companion object : Logger {
        const val VERSION = "1.0"

        override fun log(message: String) {
            println("[AppConfig] $message")
        }

        // Factory method
        fun create(): AppConfig {
            log("Creating AppConfig")
            return AppConfig()
        }
    }
}

// Java: Would need static utility class + separate logger

// ═══════════════════════════════════════════════════════════════
// 3. COMPANION OBJECT EXTENSIONS
// ═══════════════════════════════════════════════════════════════

// Kotlin: Can add extension functions to companion object
class User(val name: String, val age: Int) {
    companion object {
        // Factory methods
        fun fromName(name: String) = User(name, 0)
        fun create(name: String, age: Int) = User(name, age)
    }
}

// Extension function on companion object
fun User.Companion.guest(): User = User("Guest", 0)
fun User.Companion.fromEmail(email: String): User {
    val name = email.substringBefore("@")
    return User(name, 0)
}

// Java: Cannot extend static members!

// ═══════════════════════════════════════════════════════════════
// 4. OBJECT DECLARATION (Singleton)
// ═══════════════════════════════════════════════════════════════

// Kotlin: object declaration = thread-safe singleton (one line!)
object DatabaseConfig {
    val host = "localhost"
    val port = 5432
    val maxConnections = 10

    fun connect(): String {
        return "Connecting to $host:$port"
    }
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

// ═══════════════════════════════════════════════════════════════
// 5. OBJECT EXPRESSION (Anonymous Object)
// ═══════════════════════════════════════════════════════════════

// Kotlin: object expression = anonymous object (like Java's anonymous class)
interface ClickListener {
    fun onClick(viewId: String)
}

fun setupButton() {
    // Kotlin: object expression
    val listener = object : ClickListener {
        override fun onClick(viewId: String) {
            println("Clicked: $viewId")
        }
    }

    // Java equivalent:
    // ClickListener listener = new ClickListener() {
    //     @Override
    //     public void onClick(String viewId) {
    //         System.out.println("Clicked: " + viewId);
    //     }
    // };
}

// Kotlin: Can create anonymous object without interface
fun createAnonymousObject() {
    val obj = object {
        val x = 10
        val y = 20
        fun sum() = x + y
    }
    println("Sum: ${obj.sum()}")

    // Java: Cannot create anonymous object without interface/class!
    // Must use some class or interface
}

// ═══════════════════════════════════════════════════════════════
// 6. PRACTICAL EXAMPLES
// ═══════════════════════════════════════════════════════════════

// Example 1: Factory Pattern
sealed class Shape {
    data class Circle(val radius: Double) : Shape()
    data class Rectangle(val width: Double, val height: Double) : Shape()

    companion object {
        // Factory methods
        fun circle(radius: Double) = Circle(radius)
        fun rectangle(width: Double, height: Double) = Rectangle(width, height)

        // From string
        fun fromString(description: String): Shape {
            val parts = description.split(",")
            return when (parts[0]) {
                "circle" -> Circle(parts[1].toDouble())
                "rectangle" -> Rectangle(parts[1].toDouble(), parts[2].toDouble())
                else -> throw IllegalArgumentException("Unknown shape: $description")
            }
        }
    }
}

// Example 2: Constants Holder
object Constants {
    const val APP_NAME = "MyApp"
    const val VERSION = "1.0.0"
    const val API_TIMEOUT = 30000L

    object Paths {
        const val API_BASE = "/api/v1"
        const val USERS = "$API_BASE/users"
        const val POSTS = "$API_BASE/posts"
    }
}

// Example 3: Service Locator
object ServiceLocator {
    private val services = mutableMapOf<String, Any>()

    fun <T : Any> register(name: String, service: T) {
        services[name] = service
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(name: String): T = services[name] as T
}

// ═══════════════════════════════════════════════════════════════
// MAIN FUNCTION
// ═══════════════════════════════════════════════════════════════

fun main() {

    // Companion object
    println("PI: ${MathUtils.PI}")
    println("Add: ${MathUtils.add(1, 2)}")
    val utils = MathUtils.create()
    println("Calculate: ${utils.calculate()}")

    // Companion object with interface
    val config = AppConfig.create()
    println("Host: ${config.host}")
    println("Version: ${AppConfig.VERSION}")
    AppConfig.log("App started")

    // Companion object extensions
    val guest = User.guest()
    val fromEmail = User.fromEmail("alice@example.com")
    println("Guest: ${guest.name}")
    println("From email: ${fromEmail.name}")

    // Object declaration (singleton)
    println("Connect: ${DatabaseConfig.connect()}")
    println("Max connections: ${DatabaseConfig.maxConnections}")

    // Object expression
    setupButton()
    createAnonymousObject()

    // Factory pattern
    val circle = Shape.circle(5.0)
    val rect = Shape.rectangle(4.0, 6.0)
    val fromStr = Shape.fromString("circle,3.0")
    println("Circle: $circle")
    println("Rectangle: $rect")
    println("From string: $fromStr")

    // Constants
    println("App: ${Constants.APP_NAME} v${Constants.VERSION}")
    println("Users URL: ${Constants.Paths.USERS}")

    // Service locator
    ServiceLocator.register("database", "PostgreSQL")
    val db: String = ServiceLocator.get("database")
    println("Database: $db")

    // ═══════════════════════════════════════════════════════════════
    // COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // JAVA                              KOTLIN
    // ────────────────────────────────  ──────────────────────────────
    // static final int X = 5            companion object { const val X = 5 }
    // static void f() { }               companion object { fun f() { } }
    // Singleton (complex)               object Singleton { }
    // Anonymous class                   object : Interface { }
    // ClassName.staticMethod()          ClassName.staticMethod()
    // Math.random()                     Math.random() or Utils.random()
    //
    // KEY RULES:
    // ─────────────────────────────────────────────────
    // 1. companion object replaces static
    // 2. object declaration = singleton
    // 3. object expression = anonymous object
    // 4. Companion object can implement interfaces
    // 5. Can add extensions to companion object
    // 6. const val in companion object = static final
    // 7. @JvmStatic for Java interop
    // 8. @JvmField for direct field access
    //
    // ═══════════════════════════════════════════════════════════════
}
