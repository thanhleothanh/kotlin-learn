/**
 * Day 3 - Companion Objects & Object Declarations in Java vs Kotlin
 *
 * Java:
 *   - Static methods and fields
 *   - Static utility classes
 *   - Singleton pattern (various implementations)
 *   - Factory methods
 *
 * Kotlin:
 *   - companion object — static-like members
 *   - object declaration — singleton
 *   - object expression — anonymous object
 *   - No "static" keyword!
 */
import java.util.Map;
import java.util.HashMap;

public class CompanionObjectExample {

    // ═══════════════════════════════════════════════════════════════
    // 1. JAVA: STATIC METHODS AND FIELDS
    // ═══════════════════════════════════════════════════════════════

    // Java: static keyword for class-level members
    public static class MathUtils {
        public static final double PI = 3.14159;
        public static final int MAX_SIZE = 100;

        // Static method
        public static int add(int a, int b) {
            return a + b;
        }

        // Static factory method
        public static MathUtils create() {
            return new MathUtils();
        }

        // Private constructor (prevent instantiation)
        private MathUtils() {}
    }

    // Java: Call static members via class name
    // MathUtils.add(1, 2)
    // MathUtils.PI

    // ═══════════════════════════════════════════════════════════════
    // 2. JAVA: SINGLETON (various implementations)
    // ═══════════════════════════════════════════════════════════════

    // Java: Eager initialization
    public static class Singletons {
        // Eager singleton
        public static final Singletons EAGER = new Singletons();

        // Lazy singleton (thread-safe)
        private static volatile Singletons instance;
        public static Singletons getInstance() {
            if (instance == null) {
                synchronized (Singletons.class) {
                    if (instance == null) {
                        instance = new Singletons();
                    }
                }
            }
            return instance;
        }

        // Enum singleton (best practice)
        public enum EnumSingleton {
            INSTANCE;
            public void doSomething() { System.out.println("Enum singleton"); }
        }

        // Holder singleton (lazy, thread-safe)
        private static class Holder {
            static final Singletons INSTANCE = new Singletons();
        }
        public static Singletons getHolderInstance() {
            return Holder.INSTANCE;
        }
    }

    // Java: Singleton is verbose and error-prone!

    // ═══════════════════════════════════════════════════════════════
    // 3. JAVA: FACTORY PATTERN
    // ═══════════════════════════════════════════════════════════════

    // Java: Factory method pattern
    public interface Shape {
        double area();
    }

    public static class Circle implements Shape {
        private final double radius;
        public Circle(double radius) { this.radius = radius; }
        public double area() { return Math.PI * radius * radius; }

        // Factory method
        public static Circle create(double radius) {
            return new Circle(radius);
        }
    }

    public static class Rectangle implements Shape {
        private final double width, height;
        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }
        public double area() { return width * height; }

        // Factory method
        public static Rectangle create(double width, double height) {
            return new Rectangle(width, height);
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // 4. COMPARISON TABLE
    // ═══════════════════════════════════════════════════════════════
    //
    //   Feature              Java                              Kotlin
    //   ──────────────────── ────────────────────────────────  ────────────────────────────
    //   Static members       static keyword                   companion object
    //   Static method        static void f()                  companion object { fun f() }
    //   Static field         static final int X = 5           companion object { val X = 5 }
    //   Singleton            Various patterns                 object : ClassName { }
    //   Factory              Static method                     companion object { fun create() }
    //   Type access          ClassName.staticMethod()         ClassName.staticMethod()
    //   Instance access      N/A                              ClassName().method()
    //
    // ═══════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        // Java static members
        System.out.println("PI: " + MathUtils.PI);
        System.out.println("Add: " + MathUtils.add(1, 2));

        // Java singleton
        Singletons singleton = Singletons.getInstance();
        Singletons.EnumSingleton.INSTANCE.doSomething();

        // Java factory
        Shape circle = Circle.create(5.0);
        Shape rectangle = Rectangle.create(4.0, 6.0);
        System.out.println("Circle area: " + circle.area());
        System.out.println("Rectangle area: " + rectangle.area());

        // ═══════════════════════════════════════════════════════════════
        // KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════════
        //
        // JAVA:
        //   - static keyword for class-level members
        //   - Singleton requires complex implementation
        //   - Factory pattern requires interface + implementations
        //   - No true "companion" concept
        //
        // KOTLIN:
        //   - companion object replaces static
        //   - object declaration = singleton (one line!)
        //   - Companion object can implement interfaces
        //   - Companion object can have factory methods
        //   - Much cleaner and more powerful
        //
        // ═══════════════════════════════════════════════════════════════
    }
}
