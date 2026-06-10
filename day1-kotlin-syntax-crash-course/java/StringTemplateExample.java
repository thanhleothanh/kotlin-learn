/**
 * Day 1 - String Templates in Java vs Kotlin
 *
 * Java:
 *   - String concatenation: "Hello " + name
 *   - String.format(): String.format("Hello %s", name)
 *   - Text blocks (Java 13+): """ ... """
 *   - No string templates (unlike many other languages)
 *
 * Kotlin:
 *   - String templates: "Hello $name"
 *   - Expression templates: "Length: ${name.length}"
 *   - Triple-quoted strings: """ ... """ (raw strings)
 */
public class StringTemplateExample {

    public static void main(String[] args) {

        String name = "Kotlin";
        int age = 10;
        double pi = 3.14159;
        String city = "Hanoi";

        // ═══════════════════════════════════════════════════════════════
        // 1. JAVA STRING CONCATENATION (old way)
        // ═══════════════════════════════════════════════════════════════

        // Java: concatenation with + (creates StringBuilder under the hood)
        String greeting1 = "Hello, " + name + "! You are " + age + " years old.";
        System.out.println(greeting1);

        // Java: concatenation in loops is inefficient (creates many StringBuilder objects)

        // ═══════════════════════════════════════════════════════════════
        // 2. JAVA String.format() (C-style formatting)
        // ═══════════════════════════════════════════════════════════════

        // Java: String.format with % placeholders
        String greeting2 = String.format("Hello, %s! You are %d years old.", name, age);
        System.out.println(greeting2);

        // Java: Format specifiers
        String formatted = String.format("Pi is %.2f, city is %s", pi, city);
        System.out.println(formatted);

        // Java: Various format specifiers
        System.out.println(String.format("Hex: %x, Octal: %o, Scientific: %e", 255, 255, 12345.6789));

        // ═══════════════════════════════════════════════════════════════
        // 3. JAVA TEXT BLOCKS (Java 13+)
        // ═══════════════════════════════════════════════════════════════

        // Java: Text blocks (multi-line strings)
        String json = """
                {
                    "name": "%s",
                    "age": %d
                }
                """.formatted(name, age);
        System.out.println(json);

        // ═══════════════════════════════════════════════════════════════
        // 4. JAVA: No string templates!
        // ═══════════════════════════════════════════════════════════════

        // Java has NO equivalent of Kotlin's $name or ${expr} syntax
        // The closest is String.format() or concatenation

        // ═══════════════════════════════════════════════════════════════
        // 5. COMPARISON TABLE
        // ═══════════════════════════════════════════════════════════════
        //
        //   Feature              Java 17                     Kotlin
        //   ──────────────────── ─────────────────────────── ────────────────────────
        //   Simple variable      "Hello " + name             "Hello $name"
        //   Expression           "Len: " + name.length()     "Len: ${name.length}"
        //   Multi-line           """ ... """ (Java 15+)      """ ... """
        //   Formatting           String.format("%s", name)   "Hello $name"
        //   Raw strings          No equivalent               """Hello\nWorld"""
        //   Escape characters    \n \t \\                    ${'$'} (dollar escape)
        //
        // ═══════════════════════════════════════════════════════════════

        // ═══════════════════════════════════════════════════════════════
        // 6. KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════════
        //
        // JAVA:
        //   - Uses concatenation (+) or String.format()
        //   - Text blocks (Java 15+) for multi-line
        //   - No string templates
        //   - Format specifiers: %s, %d, %f, etc.
        //
        // KOTLIN:
        //   - $variable for simple variable interpolation
        //   - ${expression} for complex expressions
        //   - """ for raw/multi-line strings (no escaping needed)
        //   - Much more readable than Java's approach
        //
        // ═══════════════════════════════════════════════════════════════
    }
}
