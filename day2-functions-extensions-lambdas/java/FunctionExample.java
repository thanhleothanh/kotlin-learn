/**
 * Day 2 - Functions in Java vs Kotlin
 *
 * Java:
 *   - Method overloading (no named params, no default args)
 *   - varargs for variable arguments
 *   - Single-expression methods (Java 14+ with return)
 *
 * Kotlin:
 *   - Named arguments
 *   - Default parameter values
 *   - Single-expression functions
 *   - Infix functions
 *   - Extension functions
 *   - Higher-order functions
 */
import java.util.List;
import java.util.ArrayList;

public class FunctionExample {

    // ═══════════════════════════════════════════════════════════════
    // 1. JAVA FUNCTIONS (methods)
    // ═══════════════════════════════════════════════════════════════

    // Java: Must specify return type, parameter types
    public static String greet(String name) {
        return "Hello, " + name + "!";
    }

    // Java: Method overloading (different parameter list)
    public static String greet(String firstName, String lastName) {
        return "Hello, " + firstName + " " + lastName + "!";
    }

    // Java: Overloading with different types
    public static String greet(int times, String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append("Hello, ").append(name).append("! ");
        }
        return sb.toString().trim();
    }

    // ═══════════════════════════════════════════════════════════════
    // 2. JAVA: NO NAMED PARAMETERS!
    // ═══════════════════════════════════════════════════════════════

    // Java: Arguments passed by position ONLY
    // createPerson("Alice", 25, "alice@email.com")
    // If you want to skip a param, you MUST overload or use builder pattern

    // Java workaround: Builder pattern
    public static class PersonBuilder {
        private String name = "Unknown";
        private int age = 0;
        private String email = "";

        public PersonBuilder name(String name) { this.name = name; return this; }
        public PersonBuilder age(int age) { this.age = age; return this; }
        public PersonBuilder email(String email) { this.email = email; return this; }
        public String build() { return name + " (" + age + ") - " + email; }
    }

    // ═══════════════════════════════════════════════════════════════
    // 3. JAVA: NO DEFAULT PARAMETERS!
    // ═══════════════════════════════════════════════════════════════

    // Java: Must overload for default values
    public static String formatString(String text) {
        return formatString(text, "UPPER");  // Call overload with default
    }

    public static String formatString(String text, String mode) {
        return switch (mode) {
            case "UPPER" -> text.toUpperCase();
            case "LOWER" -> text.toLowerCase();
            default -> text;
        };
    }

    // Java: Many overloads needed!
    // print("hello")
    // print("hello", true)
    // print("hello", true, 2)
    // print("hello", true, 2, '\n')
    // Each combination needs a separate method!

    // ═══════════════════════════════════════════════════════════════
    // 4. JAVA: VARARGS (variable arguments)
    // ═══════════════════════════════════════════════════════════════

    public static int sum(int... numbers) {
        int total = 0;
        for (int n : numbers) {
            total += n;
        }
        return total;
    }

    // ═══════════════════════════════════════════════════════════════
    // 5. JAVA: SINGLE-EXPRESSION (Java 14+)
    // ═══════════════════════════════════════════════════════════════

    // Java 14+: yield for switch expressions
    // But no single-expression function syntax like Kotlin

    // ═══════════════════════════════════════════════════════════════
    // 6. JAVA: STATIC METHODS vs INSTANCE METHODS
    // ═══════════════════════════════════════════════════════════════

    // Java: Must use class name for static methods
    // FunctionExample.greet("Alice")

    // Java 19+: static import for cleaner calls
    // import static FunctionExample.greet;
    // greet("Alice");

    // ═══════════════════════════════════════════════════════════════
    // 7. COMPARISON TABLE
    // ═══════════════════════════════════════════════════════════════
    //
    //   Feature              Java 17                         Kotlin
    //   ──────────────────── ─────────────────────────────── ────────────────────────────
    //   Function syntax      public static String f(int x)  fun f(x: Int): String
    //   Named arguments      ❌ Not supported                ✅ f(x = 5)
    //   Default args         ❌ Must overload                ✅ fun f(x: Int = 5)
    //   Single expression    ❌ Must use body                ✅ fun f() = expr
    //   Varargs              int... args                     vararg args: Int
    //   Extension functions  ❌ Not possible                 ✅ fun String.f()
    //   Infix functions      ❌ Not possible                 ✅ infix fun f()
    //   Higher-order         ❌ Verbose (interfaces)         ✅ (Int) -> String
    //
    // ═══════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        // Basic function calls
        System.out.println(greet("Alice"));
        System.out.println(greet("Alice", "Smith"));

        // No named parameters — must pass in order!
        // createPerson("Alice", 25, "alice@email.com")

        // Builder pattern workaround
        String person = new PersonBuilder()
            .name("Alice")
            .age(25)
            .email("alice@email.com")
            .build();
        System.out.println(person);

        // Default args workaround: overloading
        System.out.println(formatString("hello"));           // Uses overload
        System.out.println(formatString("hello", "lower"));  // Full params

        // Varargs
        System.out.println("Sum: " + sum(1, 2, 3, 4, 5));

        // ═══════════════════════════════════════════════════════════════
        // 8. KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════════
        //
        // JAVA PROBLEMS:
        //   1. No named arguments → hard to read `f(true, false, true, false)`
        //   2. No default arguments → must overload for each combination
        //   3. No extension functions → must use utility classes
        //   4. No single-expression functions → verbose getters
        //   5. No infix functions → `map.get(key)` instead of `map[key]`
        //
        // KOTLIN SOLUTIONS:
        //   1. Named arguments: `f(a = true, c = true)` — skip b, d
        //   2. Default arguments: `fun f(a: Int = 1, b: Int = 2)`
        //   3. Extension functions: `fun String.isEmail()`
        //   4. Single-expression: `fun double(x: Int) = x * 2`
        //   5. Infix functions: `key map value`
        //
        // ═══════════════════════════════════════════════════════════════
    }
}
