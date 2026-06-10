/**
 * Day 2 - Extension Functions in Java vs Kotlin
 *
 * Java:
 *   - NO extension functions!
 *   - Workaround: static utility methods (e.g., Collections.sort())
 *   - Workaround: default methods in interfaces (Java 8+)
 *   - Workaround: wrapper classes
 *
 * Kotlin:
 *   - Extension functions: add methods to existing classes
 *   - Extension properties
 *   - Nullable receiver extensions
 *   - Companion object extensions
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ExtensionFunctionExample {

    // ═══════════════════════════════════════════════════════════════
    // 1. JAVA: STATIC UTILITY METHODS (the workaround)
    // ═══════════════════════════════════════════════════════════════

    // Java: Cannot add methods to existing classes!
    // Must use static methods in utility classes

    // Example: Adding "isEmail" check to String
    // Java: Create a utility class
    public static class StringUtils {
        public static boolean isEmail(String str) {
            return str != null && str.contains("@") && str.contains(".");
        }

        public static boolean isBlank(String str) {
            return str == null || str.trim().isEmpty();
        }

        public static String capitalize(String str) {
            if (str == null || str.isEmpty()) return str;
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

    // Java: Collections utility class
    // Collections.sort(list) — not list.sort()
    // Collections.unmodifiableList(list) — not list.unmodifiable()
    // Collections.frequency(list, item) — not list.frequency(item)

    // ═══════════════════════════════════════════════════════════════
    // 2. JAVA 8+: DEFAULT METHODS IN INTERFACES
    // ═══════════════════════════════════════════════════════════════

    // Java 8+: Can add default methods to interfaces
    // But the class must implement the interface!
    public interface StringExtensions {
        default boolean isEmail() {
            return this.toString().contains("@");
        }

        default boolean isBlank() {
            return this.toString().trim().isEmpty();
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // 3. JAVA: EXTENSION METHODS VIA WRAPPER
    // ═══════════════════════════════════════════════════════════════

    // Java: Wrapper pattern
    public static class EnhancedString {
        private final String value;

        public EnhancedString(String value) {
            this.value = value;
        }

        public boolean isEmail() {
            return value != null && value.contains("@");
        }

        public String capitalize() {
            if (value == null || value.isEmpty()) return value;
            return value.substring(0, 1).toUpperCase() + value.substring(1);
        }

        @Override
        public String toString() { return value; }
    }

    // ═══════════════════════════════════════════════════════════════
    // 4. JAVA 19+: STRUCTURED CONCURRENCY (not extensions)
    // ═══════════════════════════════════════════════════════════════

    // Still no extension functions in Java!

    // ═══════════════════════════════════════════════════════════════
    // 5. COMPARISON TABLE
    // ═══════════════════════════════════════════════════════════════
    //
    //   Feature                  Java                              Kotlin
    //   ──────────────────────── ────────────────────────────────  ────────────────────────────
    //   Add method to class      ❌ Not possible                   ✅ fun String.f()
    //   Add property to class    ❌ Not possible                   ✅ val String.f get() = ...
    //   Workaround               Static utils / wrappers          Extension functions
    //   Call syntax               StringUtils.isEmail(str)         str.isEmail()
    //   Null safety              Manual null checks                fun String?.f()
    //   Chaining                 str.toUpperCase().trim()          str.uppercase().trim()
    //
    // ═══════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        // Java: Static utility method
        String email = "user@example.com";
        System.out.println("Is email? " + StringUtils.isEmail(email));
        System.out.println("Capitalize: " + StringUtils.capitalize("hello"));

        // Java: Collections utility
        List<String> list = new ArrayList<>(List.of("c", "a", "b"));
        Collections.sort(list);
        System.out.println("Sorted: " + list);

        // Java: Must call utility class methods, not object methods
        // str.isEmail()  // ❌ Not possible in Java!
        // StringUtils.isEmail(str)  // ✅ Java approach

        // ═══════════════════════════════════════════════════════════════
        // 6. KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════════
        //
        // JAVA PROBLEMS:
        //   1. Cannot add methods to existing classes
        //   2. Must use static utility methods (verbose)
        //   3. Must import utility classes
        //   4. Cannot chain naturally (StringUtils.f(str).g())
        //   5. Wrapper pattern is clunky
        //
        // KOTLIN SOLUTIONS:
        //   1. Extension functions: fun String.isEmail() = ...
        //   2. Natural call syntax: str.isEmail()
        //   3. Nullable extensions: fun String?.isEmail()
        //   4. Extension properties: val String.length get() = ...
        //   5. Works with existing classes (String, List, etc.)
        //
        // ═══════════════════════════════════════════════════════════════
    }
}
