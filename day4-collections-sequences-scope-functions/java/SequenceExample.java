/**
 * Day 4 - Sequences (Lazy Evaluation) in Java vs Kotlin
 *
 * Java:
 *   - Streams are eager by default
 *   - parallel() for parallel processing
 *   - No built-in lazy sequences
 *
 * Kotlin:
 *   - Sequences are lazy by default
 *   - Much more efficient for large datasets
 *   - No need for Streams API
 *   - Can be parallelized with asSequence()
 */
import java.util.stream.Stream;
import java.util.stream.IntStream;

public class SequenceExample {

    // ═══════════════════════════════════════════════════════════════
    // 1. JAVA: EAGER COLLECTIONS (all processed at once)
    // ═══════════════════════════════════════════════════════════════

    public static void eagerExample() {
        // Java: Collections process all elements at each step
        java.util.List<Integer> numbers = java.util.List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Each operation creates a NEW list (intermediate collection!)
        java.util.List<Integer> result = numbers.stream()
            .filter(n -> {
                System.out.println("Filter: " + n);
                return n % 2 == 0;
            })
            .map(n -> {
                System.out.println("Map: " + n);
                return n * n;
            })
            .toList();

        // Output: 10 filter operations, 5 map operations
        // This is fine for small collections, but wasteful for large ones
    }

    // ═══════════════════════════════════════════════════════════════
    // 2. JAVA: LAZY STREAMS (with intermediate operations)
    // ═══════════════════════════════════════════════════════════════

    public static void lazyStreamExample() {
        // Java: Stream is lazy, but only with terminal operation
        Stream.iterate(1, n -> n + 1)  // Infinite stream!
            .filter(n -> {
                System.out.println("Filter: " + n);
                return n % 2 == 0;
            })
            .map(n -> {
                System.out.println("Map: " + n);
                return n * n;
            })
            .limit(5)  // Take only 5 elements
            .toList();

        // Output: Only processes until 5 elements found
        // Much more efficient!
    }

    // ═══════════════════════════════════════════════════════════════
    // 3. JAVA: PARALLEL STREAMS
    // ═══════════════════════════════════════════════════════════════

    public static void parallelExample() {
        // Java: parallel() for multi-threaded processing
        long sum = IntStream.rangeClosed(1, 1_000_000)
            .parallel()
            .filter(n -> n % 2 == 0)
            .mapToLong(n -> n)
            .sum();

        System.out.println("Sum: " + sum);
    }

    // ═══════════════════════════════════════════════════════════════
    // 4. COMPARISON TABLE
    // ═══════════════════════════════════════════════════════════════
    //
    //   Feature              Java                              Kotlin
    //   ──────────────────── ────────────────────────────────  ────────────────────────────
    //   Lazy evaluation      Stream.iterate().limit()         sequence { }.take()
    //   Eager evaluation     list.stream().toList()           list.map { }
    //   Infinite sequences   Stream.iterate()                 generateSequence()
    //   Parallel             stream().parallel()              asSequence().concurrent()
    //   Chaining             .filter().map().toList()         .filter{}.map{}.toList()
    //
    // ═══════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        eagerExample();
        lazyStreamExample();
        parallelExample();

        // ═══════════════════════════════════════════════════════════════
        // KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════════
        //
        // JAVA:
        //   - Streams are lazy but need terminal operation
        //   - parallel() for multi-threading
        //   - Infinite sequences with Stream.iterate()
        //   - More verbose syntax
        //
        // KOTLIN:
        //   - Sequences are lazy by default
        //   - generateSequence() for infinite sequences
        //   - Much cleaner syntax
        //   - No need for Streams API
        //
        // ═══════════════════════════════════════════════════════════════
    }
}
