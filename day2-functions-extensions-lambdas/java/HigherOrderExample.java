/**
 * Day 2 - Higher-Order Functions & Lambdas in Java vs Kotlin
 *
 * Java:
 *   - Functional interfaces (Java 8+)
 *   - Lambda expressions (Java 8+)
 *   - Method references (Java 8+)
 *   - Anonymous classes (pre-Java 8)
 *
 * Kotlin:
 *   - Lambda expressions (cleaner than Java)
 *   - Higher-order functions (functions as parameters/return values)
 *   - Function types: (Int) -> String
 *   - SAM conversion (Java interop)
 *   - Extension functions as lambdas
 */
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class HigherOrderExample {

    // ═══════════════════════════════════════════════════════════════
    // 1. JAVA: FUNCTIONAL INTERFACES (Java 8+)
    // ═══════════════════════════════════════════════════════════════

    // Java: Must define functional interface (single abstract method)
    @FunctionalInterface
    interface Transformer<T, R> {
        R transform(T input);
    }

    @FunctionalInterface
    interface Validator {
        boolean validate(String input);
    }

    // Java: Higher-order function takes functional interface
    public static <T, R> List<R> mapList(List<T> list, Transformer<T, R> transformer) {
        List<R> result = new ArrayList<>();
        for (T item : list) {
            result.add(transformer.transform(item));
        }
        return result;
    }

    public static List<String> filterList(List<String> list, Validator validator) {
        List<String> result = new ArrayList<>();
        for (String item : list) {
            if (validator.validate(item)) {
                result.add(item);
            }
        }
        return result;
    }

    // ═══════════════════════════════════════════════════════════════
    // 2. JAVA: LAMBDA EXPRESSIONS (Java 8+)
    // ═══════════════════════════════════════════════════════════════

    // Java: Lambda syntax (verbose compared to Kotlin)
    // (parameters) -> expression
    // (parameters) -> { statements; }

    // ═══════════════════════════════════════════════════════════════
    // 3. JAVA: METHOD REFERENCES (Java 8+)
    // ═══════════════════════════════════════════════════════════════

    // Java: Method reference shortcuts
    // ClassName::staticMethod
    // instance::method
    // ClassName::new

    // ═══════════════════════════════════════════════════════════════
    // 4. JAVA: ANONYMOUS CLASSES (pre-Java 8)
    // ═══════════════════════════════════════════════════════════════

    // Java: Verbose anonymous class
    // List<String> sorted = list.sort(new Comparator<String>() {
    //     @Override
    //     public int compare(String a, String b) {
    //         return a.compareTo(b);
    //     }
    // });

    // ═══════════════════════════════════════════════════════════════
    // 5. COMPARISON TABLE
    // ═══════════════════════════════════════════════════════════════
    //
    //   Feature                Java 8+                          Kotlin
    //   ────────────────────── ──────────────────────────────── ────────────────────────────
    //   Lambda syntax          (x) -> x * 2                    { x -> x * 2 }
    //   Single param           x -> x * 2                      { it * 2 }
    //   Function type          Function<Integer, Integer>       (Int) -> Int
    //   Return lambda          return (x) -> x * 2             return { x -> x * 2 }
    //   Method ref             String::toUpperCase             String::uppercase
    //   It keyword             ❌ Must name param              ✅ { it.length }
    //   Extension lambda       ❌ Not possible                 ✅ String.() -> Unit
    //
    // ═══════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // ═══════════════════════════════════════════════════════════════
        // JAVA LAMBDA EXAMPLES
        // ═══════════════════════════════════════════════════════════════

        // Lambda with explicit types
        List<Integer> doubled = mapList(numbers, (Integer x) -> x * 2);
        System.out.println("Doubled: " + doubled);

        // Lambda with inferred types
        List<String> uppercased = mapList(names, (x) -> x.toUpperCase());
        System.out.println("Uppercased: " + uppercased);

        // Single-parameter lambda (no parens needed)
        List<String> longNames = filterList(names, name -> name.length() > 4);
        System.out.println("Long names: " + longNames);

        // Method reference
        List<String> upperNames = mapList(names, String::toUpperCase);
        System.out.println("Method ref: " + upperNames);

        // ═══════════════════════════════════════════════════════════════
        // JAVA: STREAM API (most common lambda usage)
        // ═══════════════════════════════════════════════════════════════

        List<String> result = names.stream()
            .filter(name -> name.length() > 4)
            .map(String::toUpperCase)
            .sorted()
            .toList();
        System.out.println("Stream result: " + result);

        // Java: Comparator lambda
        List<String> sorted = new ArrayList<>(names);
        sorted.sort((a, b) -> a.compareTo(b));
        System.out.println("Sorted: " + sorted);

        // Java: Consumer lambda
        names.forEach(name -> System.out.println("Name: " + name));

        // ═══════════════════════════════════════════════════════════════
        // JAVA: LAMBDA SCOPE (effectively final)
        // ═══════════════════════════════════════════════════════════════

        // Java: Lambdas can only capture EFFECTIVELY FINAL variables
        String prefix = "Hello";  // effectively final
        // prefix = "Changed";   // Would make it not effectively final!

        List<String> greetings = mapList(names, name -> prefix + ", " + name);
        System.out.println("Greetings: " + greetings);

        // ═══════════════════════════════════════════════════════════════
        // KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════════
        //
        // JAVA LAMBDAS:
        //   1. Syntax: (x) -> expression or (x) -> { statements }
        //   2. Must target functional interface
        //   3. No "it" keyword — must name parameter
        //   4. Cannot use as return values directly (need wrapper)
        //   5. Cannot have extension function lambdas
        //
        // KOTLIN LAMBDAS:
        //   1. Syntax: { x -> expression } or { it * 2 }
        //   2. First class — can be passed directly
        //   3. "it" keyword for single parameter
        //   4. Can return lambdas directly
        //   5. Extension function lambdas: String.() -> Unit
        //   6. Much more concise!
        //
        // ═══════════════════════════════════════════════════════════════
    }
}
