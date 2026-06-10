/**
 * Day 3 - Sealed Classes in Java vs Kotlin
 *
 * Java:
 *   - Sealed classes (Java 17+) — restricts class hierarchy
 *   - Pattern matching for switch (Java 21+)
 *   - Still limited compared to Kotlin
 *
 * Kotlin:
 *   - Sealed classes — restricts class hierarchy (same concept)
 *   - Sealed interfaces (Kotlin 1.5+)
 *   - Exhaustive when expressions (compiler checks)
 *   - Much more powerful pattern matching
 */
import java.util.List;

public class SealedClassExample {

    // ═══════════════════════════════════════════════════════════════
    // 1. JAVA SEALED CLASSES (Java 17+)
    // ═══════════════════════════════════════════════════════════════

    // Java 17+: Sealed class — restricts which classes can extend
    public sealed interface Result permits Success, Error, Loading {}

    public record Success(String data) implements Result {}
    public record Error(String message) implements Result {}
    public record Loading() implements Result {}

    // Java: Must use "permits" keyword and list all subclasses
    // Subclasses must be final, sealed, or non-sealed

    // ═══════════════════════════════════════════════════════════════
    // 2. JAVA: PATTERN MATCHING FOR SWITCH (Java 21+)
    // ═══════════════════════════════════════════════════════════════

    // Java 21+: Pattern matching for switch
    public static String handleResult(Result result) {
        return switch (result) {
            case Success s -> "Success: " + s.data();
            case Error e -> "Error: " + e.message();
            case Loading l -> "Loading...";
            // No default needed — sealed class is exhaustive!
        };
    }

    // ═══════════════════════════════════════════════════════════════
    // 3. JAVA: ENUM-BASED STATE MACHINES (pre-Java 17)
    // ═══════════════════════════════════════════════════════════════

    // Before sealed classes, Java used enums for state machines
    public enum State {
        IDLE, RUNNING, PAUSED, STOPPED
    }

    // Enums have limitations:
    // - Cannot hold different data per state
    // - Cannot have different behavior per state
    // - No type safety for state-specific data

    // ═══════════════════════════════════════════════════════════════
    // 4. COMPARISON TABLE
    // ═══════════════════════════════════════════════════════════════
    //
    //   Feature              Java 17                        Kotlin
    //   ──────────────────── ────────────────────────────── ────────────────────────────
    //   Declaration          sealed class/interface         sealed class/interface
    //   Permits              explicit "permits" clause      implicit (same file/module)
    //   Pattern matching     Java 21+ (switch)              when (always)
    //   Exhaustiveness       Optional (switch)              Required (when)
    //   Data per state       Record classes                 data classes
    //   Behavior per state   Methods in record              Methods in class
    //   Extensibility        Final/non-sealed/sealed        final/sealed/open
    //
    // ═══════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        // Java sealed class
        Result success = new Success("Data loaded");
        Result error = new Error("Failed to load");
        Result loading = new Loading();

        System.out.println("Success: " + handleResult(success));
        System.out.println("Error: " + handleResult(error));
        System.out.println("Loading: " + handleResult(loading));

        // Java enum
        State state = State.RUNNING;
        String stateDesc = switch (state) {
            case IDLE -> "Not running";
            case RUNNING -> "Currently running";
            case PAUSED -> "Paused";
            case STOPPED -> "Stopped";
        };
        System.out.println("State: " + stateDesc);

        // ═══════════════════════════════════════════════════════════════
        // 5. KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════════
        //
        // JAVA SEALED CLASSES:
        //   - Java 17+ feature
        //   - Must use "permits" keyword
        //   - Subclasses must be in same module (or package with module)
        //   - Pattern matching (Java 21+) is limited
        //   - Records are common for subtypes
        //
        // KOTLIN SEALED CLASSES:
        //   - Available since Kotlin 1.0
        //   - No "permits" keyword — subtypes in same file/module
        //   - when expressions are exhaustive (compiler checks)
        //   - Data classes for subtypes
        //   - Much more mature and powerful
        //
        // ═══════════════════════════════════════════════════════════════
    }
}
