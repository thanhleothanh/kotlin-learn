# Day 4: Collections, Sequences & Scope Functions

> For Java 17+ developers learning Kotlin

---

## 1. Collections

| Feature | Java | Kotlin |
|---------|------|--------|
| Immutable | `List.of()` | `listOf()` |
| Mutable | `new ArrayList<>()` | `mutableListOf()` |
| Map | `Map.of("k", "v")` | `mapOf("k" to "v")` |
| Set | `Set.of()` | `setOf()` |
| Filter | `stream().filter().toList()` | `filter { }` |
| Map | `stream().map().toList()` | `map { }` |
| Reduce | `stream().reduce()` | `reduce { }` |
| Sort | `Collections.sort()` | `sorted()` |
| Distinct | `stream().distinct()` | `distinct()` |
| FlatMap | `stream().flatMap()` | `flatMap { }` |
| Take | `stream().limit(n)` | `take(n)` |
| Drop | `stream().skip(n)` | `drop(n)` |
| Chunk | ❌ | `chunked(3)` |
| Window | ❌ | `windowed(3)` |
| Zip | ❌ | `list1.zip(list2)` |
| Partition | ❌ | `partition { }` |
| GroupBy | `stream().collect()` | `groupBy { }` |
| Associate | ❌ | `associate { }` |
| Chunked | ❌ | `chunked(n)` |
| Windowed | ❌ | `windowed(n)` |
| RunningFold | ❌ | `runningFold { }` |

```kotlin
// Kotlin: rich stdlib operations
val numbers = listOf(1, 2, 3, 4, 5)
val result = numbers
    .filter { it % 2 == 0 }
    .map { it * it }
    .fold(0) { acc, i -> acc + i }

// No Streams API needed!
```

---

## 2. Sequences (Lazy Evaluation)

| Feature | Java Stream | Kotlin Sequence |
|---------|-------------|-----------------|
| Lazy | With terminal op | Always lazy |
| Infinite | `Stream.iterate()` | `generateSequence()` |
| Limit | `.limit(n)` | `.take(n)` |
| Parallel | `.parallel()` | `.concurrent()` |
| Create from | `Stream.of()` | `sequence { }` |
| To list | `.toList()` | `.toList()` |
| To map | `.collect()` | `.toMap()` |

```kotlin
// Kotlin: lazy evaluation
val result = (1..1_000_000).asSequence()
    .filter { it % 2 == 0 }
    .map { it * it }
    .take(10)
    .toList()

// Infinite sequence
val fib = generateSequence(Pair(0, 1)) { 
    Pair(it.second, it.first + it.second) 
}.map { it.first }
```

---

## 3. Scope Functions

| Function | Receiver | Returns | Use Case |
|----------|----------|---------|----------|
| `let` | `it` | result | Null checks, transformations |
| `run` | `this` | result | Object config + result |
| `apply` | `this` | this | Object configuration |
| `also` | `it` | this | Side effects, logging |
| `with` | `this` | result | Multiple operations |

```kotlin
// let: null checks
val length = name?.let { it.length } ?: 0

// apply: object configuration
val person = Person("", 0, null).apply {
    name = "Bob"
    age = 30
    email = "bob@email.com"
}

// also: logging
val result = list.also { println("Before: $it") }
    .filter { it > 0 }
    .also { println("After: $it") }

// run: configuration + result
val description = person.run {
    "$name is $age years old"
}

// with: multiple operations
val info = with(person) {
    "Name: $name, Age: $age"
}
```

---

## 4. Destructuring

| Feature | Java 17+ | Kotlin |
|---------|----------|--------|
| Basic | Records only | data class |
| Underscore | ❌ | `val (name, _, _) = person` |
| In lambdas | ❌ | `.map { (name, age) -> ... }` |
| Maps | `entry.getKey()` | `for ((k, v) in map)` |
| Collections | ❌ | `for ((i, v) in list.withIndex())` |

```kotlin
// Kotlin: destructuring
data class Person(val name: String, val age: Int, val email: String)

val person = Person("Alice", 25, "alice@email.com")
val (name, age, email) = person  // Destructure

// In lambdas
persons.map { (name, age, _) -> "$name: $age" }

// Maps
for ((key, value) in map) {
    println("$key -> $value")
}

// Collections with index
for ((index, value) in list.withIndex()) {
    println("$index: $value")
}
```

---

## Quick Reference: Java → Kotlin

```java
// Java
List<Integer> numbers = List.of(1, 2, 3, 4, 5);
List<Integer> evens = numbers.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * n)
    .toList();

Person person = new Person("", 0, null);
person.name = "Bob";
person.age = 30;

String name = person.name();
int age = person.age();
```

```kotlin
// Kotlin
val numbers = listOf(1, 2, 3, 4, 5)
val evens = numbers
    .filter { it % 2 == 0 }
    .map { it * it }

val person = Person("", 0, null).apply {
    name = "Bob"
    age = 30
}

val (name, age) = person
```

---

## Key Things to Remember

1. **listOf()** = read-only, **mutableListOf()** = mutable
2. **mapOf("k" to "v")** — "to" creates Pair
3. **asSequence()** for lazy evaluation on large datasets
4. **generateSequence()** for infinite sequences
5. **let** — null checks and transformations
6. **apply** — object configuration (returns object)
7. **also** — side effects and logging
8. **run** — object configuration + result
9. **with** — multiple operations on object
10. **Destructuring** — `val (x, y) = point` or `map { (k, v) -> ... }`
11. **Underscore** — `val (name, _, _) = person` to ignore fields
12. **componentN()** — auto-generated for data classes
13. **chunked()** — split into chunks of size n
14. **windowed()** — sliding window of size n
15. **zip()** — combine two lists into pairs
