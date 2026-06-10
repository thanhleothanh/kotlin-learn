# Day 2: Functions, Extensions & Lambdas

> For Java 17+ developers learning Kotlin

---

## 1. Named Arguments

| Feature | Java | Kotlin |
|---------|------|--------|
| Call syntax | `f("Alice", 25, "email")` | `f(name = "Alice", age = 25, email = "email")` |
| Skip params | ❌ Must overload | ✅ Use defaults |
| Readability | `f(true, false, true)` | `f(a = true, c = true)` |
| Any order | ❌ Positional only | ✅ Named in any order |

```kotlin
// Kotlin: named arguments
fun createPerson(name: String, age: Int, email: String = "") { ... }

createPerson("Alice", 25)                    // Use default email
createPerson(age = 25, name = "Alice")       // Any order!
createPerson("Alice", age = 25, email = "@")  // Mix positional + named
```

---

## 2. Default Parameters

| Feature | Java | Kotlin |
|---------|------|--------|
| Default values | ❌ Must overload | ✅ `fun f(x: Int = 5)` |
| Multiple defaults | N overloads needed | Single function |
| Builder pattern | `Builder().x().y()` | `fun f(x = 1, y = 2)` |

```kotlin
// Kotlin: one function replaces many overloads
fun greet(name: String, greeting: String = "Hello", times: Int = 1): String {
    return (greeting + ", " + name + "! ").repeat(times)
}

greet("Alice")                          // Hello, Alice!
greet("Alice", "Hi")                    // Hi, Alice!
greet("Alice", times = 3)               // Hello, Alice! x3
greet("Alice", "Hey", times = 2)        // Hey, Alice! x2
```

---

## 3. Extension Functions

| Feature | Java | Kotlin |
|---------|------|--------|
| Add method to class | ❌ Not possible | ✅ `fun String.isEmail()` |
| Workaround | Static utils | Natural syntax |
| Call syntax | `StringUtils.isEmail(str)` | `str.isEmail()` |
| Nullable extension | ❌ | ✅ `fun String?.isEmail()` |

```kotlin
// Kotlin: add methods to existing classes
fun String.isEmail(): Boolean = this.contains("@") && this.contains(".")
fun String.capitalizeFirst(): String = this.replaceFirstChar { it.uppercase() }
val String.wordCount: Int get() = this.trim().split("\\s+".toRegex()).size

// Natural call syntax
"user@email.com".isEmail()      // true
"hello".capitalizeFirst()       // Hello
"Hello World".wordCount         // 2
```

---

## 4. Higher-Order Functions

| Feature | Java | Kotlin |
|---------|------|--------|
| Function type | `Function<T, R>` | `(T) -> R` |
| Lambda | `(x) -> x * 2` | `{ x -> x * 2 }` |
| Single param | `x -> x * 2` | `{ it * 2 }` |
| Return lambda | `return x -> x * 2` | `return { x -> x * 2 }` |
| Extension lambda | ❌ | `String.() -> Unit` |
| Mutable capture | AtomicInteger | `var x` (mutable!) |

```kotlin
// Kotlin: function types are first-class
fun apply(f: (Int) -> Int, x: Int): Int = f(x)

apply({ x -> x * 2 }, 5)     // 10
apply({ it * 2 }, 5)          // 10
apply(Int::times, 5)          // Method reference

// Trailing lambda syntax
listOf(1, 2, 3).map { it * 2 }
listOf(1, 2, 3).filter { it > 1 }
```

---

## 5. Infix Functions

| Feature | Java | Kotlin |
|---------|------|--------|
| Method call | `obj.method(arg)` | `obj method arg` |
| Custom operators | ❌ | ✅ `infix fun f()` |
| Map creation | `Map.of("k", "v")` | `mapOf("k" to "v")` |
| Bit shifts | `a << 2` | `a shl 2` |

```kotlin
// Kotlin: infix functions
infix fun Int.power(exp: Int): Int = (1..exp).fold(1) { acc, _ -> acc * this }

val result = 2 power 8  // 256 (instead of 2.power(8))

// "to" creates Pair (used for maps)
val map = mapOf("Alice" to 25, "Bob" to 30)
```

---

## Quick Reference: Java → Kotlin

```java
// Java
public static String greet(String name) {
    return "Hello, " + name + "!";
}
public static int add(int a, int b) { return a + b; }
list.stream().filter(x -> x > 5).map(String::valueOf).toList();
Map.of("key", "value");
```

```kotlin
// Kotlin
fun greet(name: String) = "Hello, $name!"
fun add(a: Int, b: Int) = a + b
list.filter { it > 5 }.map { it.toString() }
mapOf("key" to "value")
```

---

## Key Things to Remember

1. **Named arguments** — skip defaults, any order, very readable
2. **Default parameters** — replaces method overloading
3. **Extension functions** — add methods to existing classes
4. **`it` keyword** — implicit single parameter in lambdas
5. **Trailing lambda** — move lambda outside parentheses
6. **Function types** — `(Int) -> String` is a type
7. **infix fun** — one parameter, no dots/parens
8. **`to`** — creates Pair, used for map creation
9. **Member > Extension** — member functions win in resolution
10. **Static resolution** — extensions resolved at compile time
