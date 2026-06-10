/**
 * Day 3 - Sealed Classes in Kotlin
 *
 * Kotlin's sealed classes are perfect for representing:
 *   - State machines
 *   - Result types (success/error)
 *   - Algebraic data types
 *   - Restricted class hierarchies
 *
 * Benefits:
 *   - Exhaustive when expressions (compiler checks!)
 *   - No missing branches
 *   - Type-safe state handling
 */

// ═══════════════════════════════════════════════════════════════
// 1. BASIC SEALED CLASS
// ═══════════════════════════════════════════════════════════════

// Kotlin: Sealed class restricts which classes can extend
sealed class Result {
    data class Success(val data: String) : Result()
    data class Error(val message: String) : Result()
    data object Loading : Result()
}

// Java equivalent (Java 17+):
// public sealed interface Result permits Success, Error, Loading {}
// public record Success(String data) implements Result {}
// public record Error(String message) implements Result {}
// public record Loading() implements Result {}

// ═══════════════════════════════════════════════════════════════
// 2. EXHAUSTIVE WHEN EXPRESSION
// ═══════════════════════════════════════════════════════════════

// Kotlin: when is EXHAUSTIVE for sealed classes — compiler checks!
fun handleResult(result: Result): String {
    return when (result) {
        is Result.Success -> "Success: ${result.data}"
        is Result.Error -> "Error: ${result.message}"
        is Result.Loading -> "Loading..."
        // No else needed — all cases covered!
        // If you add a new subtype, compiler forces you to handle it!
    }
}

// Java equivalent (Java 21+):
// public static String handleResult(Result result) {
//     return switch (result) {
//         case Success s -> "Success: " + s.data();
//         case Error e -> "Error: " + e.message();
//         case Loading l -> "Loading...";
//     };
// }

// ═══════════════════════════════════════════════════════════════
// 3. SEALED INTERFACE (Kotlin 1.5+)
// ═══════════════════════════════════════════════════════════════

// Kotlin: Sealed interface (like sealed class, but for interfaces)
sealed interface Shape {
    data class Circle(val radius: Double) : Shape
    data class Rectangle(val width: Double, val height: Double) : Shape
    data class Triangle(val base: Double, val height: Double) : Shape
}

fun calculateArea(shape: Shape): Double {
    return when (shape) {
        is Shape.Circle -> Math.PI * shape.radius * shape.radius
        is Shape.Rectangle -> shape.width * shape.height
        is Shape.Triangle -> 0.5 * shape.base * shape.height
        // Exhaustive — no else needed!
    }
}

// ═══════════════════════════════════════════════════════════════
// 4. SEALED CLASS WITH METHODS
// ═══════════════════════════════════════════════════════════════

// Kotlin: Sealed classes can have methods
sealed class State {
    abstract fun describe(): String

    data object Idle : State() {
        override fun describe() = "Not running"
    }

    data class Running(val progress: Int) : State() {
        override fun describe() = "Running at $progress%"
    }

    data class Paused(val progress: Int) : State() {
        override fun describe() = "Paused at $progress%"
    }

    data object Stopped : State() {
        override fun describe() = "Stopped"
    }
}

fun processState(state: State): String {
    return when (state) {
        is State.Idle -> state.describe()
        is State.Running -> state.describe()
        is State.Paused -> state.describe()
        is State.Stopped -> state.describe()
    }
}

// ═══════════════════════════════════════════════════════════════
// 5. SEALED CLASS WITH DATA
// ═══════════════════════════════════════════════════════════════

// Kotlin: Each subtype can hold different data
sealed class NetworkResult {
    data class Success(val statusCode: Int, val body: String) : NetworkResult()
    data class Error(val statusCode: Int, val message: String) : NetworkResult()
    data object Timeout : NetworkResult()
    data object NoConnection : NetworkResult()
}

fun handleNetwork(result: NetworkResult): String {
    return when (result) {
        is NetworkResult.Success -> "HTTP ${result.statusCode}: ${result.body.take(50)}"
        is NetworkResult.Error -> "HTTP ${result.statusCode}: ${result.message}"
        is NetworkResult.Timeout -> "Request timed out"
        is NetworkResult.NoConnection -> "No internet connection"
    }
}

// ═══════════════════════════════════════════════════════════════
// 6. SEALED CLASS WITH WHEN STATEMENT (not expression)
// ═══════════════════════════════════════════════════════════════

// When used as statement (not expression), else is required
fun logResult(result: Result) {
    when (result) {
        is Result.Success -> println("Success: ${result.data}")
        is Result.Error -> println("Error: ${result.message}")
        is Result.Loading -> println("Loading...")
        // else -> println("Unknown")  // Not needed for sealed class!
    }
}

// ═══════════════════════════════════════════════════════════════
// 7. PRACTICAL EXAMPLE: API RESPONSE
// ═══════════════════════════════════════════════════════════════

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val code: Int, val message: String) : ApiResponse<Nothing>()
    data object Loading : ApiResponse<Nothing>()
}

fun <T> handleApi(response: ApiResponse<T>): String {
    return when (response) {
        is ApiResponse.Success -> "Got data: ${response.data}"
        is ApiResponse.Error -> "Error ${response.code}: ${response.message}"
        is ApiResponse.Loading -> "Loading..."
        // Exhaustive!
    }
}

// ═══════════════════════════════════════════════════════════════
// 8. PRACTICAL EXAMPLE: MATH OPERATIONS
// ═══════════════════════════════════════════════════════════════

sealed class MathOperation {
    data class Add(val a: Double, val b: Double) : MathOperation()
    data class Subtract(val a: Double, val b: Double) : MathOperation()
    data class Multiply(val a: Double, val b: Double) : MathOperation()
    data class Divide(val a: Double, val b: Double) : MathOperation()
}

fun calculate(operation: MathOperation): Double {
    return when (operation) {
        is MathOperation.Add -> operation.a + operation.b
        is MathOperation.Subtract -> operation.a - operation.b
        is MathOperation.Multiply -> operation.a * operation.b
        is MathOperation.Divide -> {
            if (operation.b == 0.0) throw ArithmeticException("Division by zero")
            operation.a / operation.b
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// MAIN FUNCTION
// ═══════════════════════════════════════════════════════════════

fun main() {

    // Basic sealed class
    val success = Result.Success("Data loaded")
    val error = Result.Error("Failed to load")
    val loading = Result.Loading

    println("Success: ${handleResult(success)}")
    println("Error: ${handleResult(error)}")
    println("Loading: ${handleResult(loading)}")

    // Sealed interface
    val circle = Shape.Circle(5.0)
    val rectangle = Shape.Rectangle(4.0, 6.0)
    val triangle = Shape.Triangle(3.0, 8.0)

    println("Circle area: ${calculateArea(circle)}")
    println("Rectangle area: ${calculateArea(rectangle)}")
    println("Triangle area: ${calculateArea(triangle)}")

    // State machine
    val states = listOf(
        State.Idle,
        State.Running(50),
        State.Paused(50),
        State.Stopped
    )
    states.forEach { println("State: ${it.describe()}") }

    // Network result
    val networkResults = listOf(
        NetworkResult.Success(200, "OK"),
        NetworkResult.Error(404, "Not Found"),
        NetworkResult.Timeout,
        NetworkResult.NoConnection
    )
    networkResults.forEach { println("Network: ${handleNetwork(it)}") }

    // Math operations
    val operations = listOf(
        MathOperation.Add(10.0, 5.0),
        MathOperation.Subtract(10.0, 5.0),
        MathOperation.Multiply(10.0, 5.0),
        MathOperation.Divide(10.0, 5.0)
    )
    operations.forEach { println("Result: ${calculate(it)}") }

    // API response
    val apiResponses = listOf(
        ApiResponse.Success("User data"),
        ApiResponse.Error(401, "Unauthorized"),
        ApiResponse.Loading
    )
    apiResponses.forEach { println("API: ${handleApi(it)}") }

    // ═══════════════════════════════════════════════════════════════
    // COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // JAVA ENUM                  JAVA SEALED CLASS      KOTLIN SEALED CLASS
    // ────────────────────────── ────────────────────── ──────────────────────
    // Fixed set of values        Restricted hierarchy   Restricted hierarchy
    // No data per value          Record per subtype     data class per subtype
    // No behavior                Methods in record      Methods in class
    // Exhaustive when            Pattern matching       Exhaustive when
    // Limited                    Limited                Powerful
    //
    // KEY RULES:
    // ─────────────────────────────────────────────────
    // 1. sealed class restricts which classes can extend
    // 2. when is EXHAUSTIVE — compiler checks for missing branches
    // 3. Use data classes for subtypes (auto equals/hashCode/copy)
    // 4. Use sealed interface for interface hierarchies
    // 5. Each subtype can hold different data
    // 6. Perfect for state machines and result types
    // 7. Adding new subtype forces compiler errors in when expressions
    //
    // ═══════════════════════════════════════════════════════════════
}
