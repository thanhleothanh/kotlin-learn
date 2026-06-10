# Kotlin Mastery Plan — For Java 17+ Engineers

## Overview

This plan is designed for engineers who have **mastered Java 17** and want to achieve **Kotlin proficiency**. Each day includes:
1. **Java Comparison** — How this topic differs from Java
2. **What to Learn** — The Kotlin-specific knowledge
3. **Gaps Identified** — Missing topics from the original plan
4. **Practice** — Hands-on exercises

---

## Day 1: Kotlin Syntax Crash Course

### Core Topics
| Concept | Java Equivalent | Key Difference |
|---------|-----------------|----------------|
| `val`/`var` | `final` / non-final local vars | `val` = immutable reference (not object), `var` = mutable |
| Type inference | `var` (Java 10+) | Kotlin infers everywhere, not just local vars |
| String templates | Text blocks (Java 15+) | `$variable` and `${expression}` interpolation |
| `when` | `switch` expressions (Java 14+) | No fall-through, exhaustive, can take任意 args |
| Null safety | `Optional<T>` | Language-level, not library-level |

### 🔴 Gaps to Add

#### 1. Smart Casting
```kotlin
// Java: manual check + cast
if (obj instanceof String) {
    String s = (String) obj;
    // use s
}

// Kotlin: smart cast
if (obj is String) {
    obj.length  // automatically cast
}
```

#### 2. The `Nothing` Type
```kotlin
// Functions that never return
fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
}

// Used with type system
val list: MutableList<String?> = mutableListOf("a", "b")
val nothing: Nothing? = list.add(null)  // Returns Nothing?
```

#### 3. Early Returns with `val`
```kotlin
// Java: if-else assignment
final int length;
if (input == null) {
    length = 0;
} else {
    length = input.length();
}

// Kotlin: val with if/when
val length = if (input == null) 0 else input.length

// Or with run
val length = run {
    if (input == null) 0 else input.length
}
```

#### 4. Ranges and Progressions
```kotlin
// Java: IntStream.range()
for (int i = 0; i < 10; i++) { }

// Kotlin: ranges
for (i in 0 until 10) { }  // 0..9
for (i in 0..10) { }       // 0..10 inclusive
for (i in 10 downTo 0) { } // reverse
for (i in 0..10 step 2) { } // step

// Char ranges
for (c in 'a'..'z') { }
```

#### 5. Number Type Conversions
```kotlin
// Java: implicit widening, explicit narrowing
int i = 10;
long l = i;      // implicit
int j = (int) l; // explicit

// Kotlin: NO implicit conversions
val i: Int = 10
val l: Long = i.toLong()  // explicit conversion required
val d: Double = i.toDouble()

// Common conversions: toInt(), toLong(), toDouble(), toFloat(), toString()
```

#### 6. Regex
```kotlin
// Java
Pattern pattern = Pattern.compile("\\d+");
Matcher matcher = pattern.matcher("abc123");
if (matcher.find()) {
    String found = matcher.group();
}

// Kotlin (stdlib)
val pattern = Regex("\\d+")
val match = pattern.find("abc123")
match?.value  // "123"

// Or with extension
"abc123".contains(Regex("\\d+"))  // true
```

### Practice
- [ ] Convert a Java class with null checks to Kotlin null safety
- [ ] Use `when` for pattern matching on types
- [ ] Experiment with ranges and progressions

---

## Day 2: Functions, Extensions & Lambdas

### Core Topics
| Concept | Java Equivalent | Key Difference |
|---------|-----------------|----------------|
| Named params | No equivalent | `fun foo(name: String, age: Int)` called as `foo(name = "John", age = 30)` |
| Default args | Method overloading | `fun foo(x: Int = 0)` — no need for multiple methods |
| Extension functions | Static utility methods | `fun String.isEmail()` — looks like member method |
| Higher-order functions | Lambdas (Java 8+) | Cleaner syntax, `it` keyword for single param |
| Infix functions | No equivalent | `"hello" world "foo"` → `infix fun String.world(x: String)` |

### 🔴 Gaps to Add

#### 1. Inline Functions (Critical for Performance)
```kotlin
// Without inline: creates Function object for each lambda
fun <T> measure(block: () -> T): T {
    val start = System.nanoTime()
    val result = block()
    val duration = System.nanoTime() - start
    println("Took ${duration}ns")
    return result
}

// With inline: lambda code is inlined at call site, no object creation
inline fun <T> measure(block: () -> T): T {
    val start = System.nanoTime()
    val result = block()
    val duration = System.nanoTime() - start
    println("Took ${duration}ns")
    return result
}

// noinline: prevent inlining of specific lambda
inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) {
    // notInlined can be stored, passed around
    // inlined cannot
}
```

#### 2. Function References
```kotlin
// Lambda
val lengths = strings.map { it.length }

// Function reference (::)
val lengths = strings.map(String::length)

// Reference to constructor
val strings = list.map(String::class.java::new) // Java style
val strings = list.map(::String) // Kotlin style
```

#### 3. SAM Conversions (Single Abstract Method)
```kotlin
// Java interface with single abstract method
interface Predicate<T> {
    fun test(t: T): Boolean
}

// Java: can use lambda
list.stream().filter(x -> x > 0);

// Kotlin: SAM conversion
val p = Predicate<Int> { it > 0 }  // explicit type
val p = { x: Int -> x > 0 }       // if type expected

// Or with interface reference
val p = Predicate<Int> { it > 0 }
```

#### 4. Local Functions
```kotlin
fun processUser(user: User) {
    // Local function
    fun validate(name: String): Boolean {
        return name.isNotBlank() && name.length >= 2
    }

    if (validate(user.name)) {
        // process
    }
}

// Local functions can access outer function's variables
fun processUser(user: User) {
    var errors = mutableListOf<String>()

    fun validate(name: String) {
        if (name.isBlank()) errors.add("Name is blank")
        if (name.length < 2) errors.add("Name too short")
    }

    validate(user.name)
    if (errors.isNotEmpty()) {
        throw ValidationException(errors)
    }
}
```

#### 5. Variadic Arguments (Spread Operator)
```kotlin
// Java: varargs
void foo(String... args) { }

// Kotlin: varargs
fun foo(vararg args: String) { }

// Calling with spread operator
val array = arrayOf("a", "b", "c")
foo(*array)  // spread operator

// Or directly
foo("a", "b", "c")
```

#### 6. Destructuring in Lambdas
```kotlin
// Map iteration
map.forEach { (key, value) ->
    println("$key = $value")
}

// With typed
map.forEach { (key: String, value: Int) ->
    println("$key = $value")
}

// In lambdas
val pairs = listOf(Pair("a", 1), Pair("b", 2))
val lengths = pairs.map { (letter, number) -> letter.repeat(number) }
```

### Practice
- [ ] Create 5 extension functions for String and List
- [ ] Rewrite utility classes using extension functions
- [ ] Use inline functions with reified types

---

## Day 3: Data Classes, Sealed Classes & Objects

### Core Topics
| Concept | Java Equivalent | Key Difference |
|---------|-----------------|----------------|
| `data class` | Records (Java 16+) | More flexible, mutable properties allowed, copy() method |
| `sealed class` | Sealed classes (Java 17) | More mature, all subclasses in same package/module |
| `companion object` | Static methods/fields | Object tied to class, can implement interfaces |

### 🔴 Gaps to Add

#### 1. Value Classes (Inline Classes) — Performance
```kotlin
// Wrapper with zero overhead at runtime
@JvmInline
value class Email(val value: String) {
    init {
        require(value.contains("@")) { "Invalid email" }
    }
}

// Usage
fun sendEmail(email: Email) { ... }

// No object allocation, just String at runtime
sendEmail(Email("test@example.com"))
```

#### 2. Sealed Interfaces (Kotlin 1.5+)
```kotlin
// More flexible than sealed class
sealed interface Result<out T>
data class Success<T>(val data: T) : Result<T>
data class Error(val exception: Throwable) : Result<Nothing>
object Loading : Result<Nothing>

// When exhaustive
when (result) {
    is Success -> println(result.data)
    is Error -> println(result.exception)
    is Loading -> println("Loading...")
}
```

#### 3. Enum Classes with Properties
```kotlin
// Java: enums with fields
enum Color {
    RED(1), GREEN(2), BLUE(3);
    private final int code;
    Color(int code) { this.code = code; }
}

// Kotlin: enums with properties and methods
enum class Color(val code: Int) {
    RED(1), GREEN(2), BLUE(3);

    fun description(): String = "Color $name has code $code"
}

// Or with abstract methods
enum class Color(val hex: String) {
    RED("#FF0000") {
        override fun intensity() = 1.0
    },
    GREEN("#00FF00") {
        override fun intensity() = 0.8
    };

    abstract fun intensity(): Double
}
```

#### 4. Object Expressions (Anonymous Objects)
```kotlin
// Java: anonymous class
button.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // handle
    }
});

// Kotlin: object expression
button.addActionListener(object : ActionListener {
    override fun actionPerformed(e: ActionEvent) {
        // handle
    }
})

// Multiple interfaces
val obj = object : InterfaceA, InterfaceB {
    override fun methodA() { }
    override fun methodB() { }
}
```

#### 5. Type Aliases
```kotlin
// Reduce verbosity
typealias UserId = Long
typealias UserMap = Map<UserId, User>
typealias Predicate<T> = (T) -> Boolean

// With generics
typealias Result<T> = Either<Error, T>

// Usage
fun findUser(id: UserId): User? { ... }
val predicates: List<Predicate<User>> = listOf(
    { it.age > 18 },
    { it.active }
)
```

#### 6. `copy()` Method in Data Classes
```kotlin
data class User(
    val name: String,
    val age: Int,
    val email: String
)

// Copy with modifications
val user = User("John", 30, "john@example.com")
val updated = user.copy(age = 31, email = "john.doe@example.com")

// Useful for immutable updates
fun updateUser(user: User, newEmail: String): User {
    return user.copy(email = newEmail)
}
```

#### 7. Destructuring Declarations
```kotlin
// With data classes
data class Point(val x: Int, val y: Int)

val point = Point(10, 20)
val (x, y) = point  // destructuring

// With functions returning data classes
fun getCoordinates(): Point = Point(10, 20)
val (x, y) = getCoordinates()

// Ignoring components
val (_, y) = point  // ignore x
```

### Practice
- [ ] Convert Java records to data classes
- [ ] Implement a state machine with sealed classes
- [ ] Create singleton patterns with companion objects and object declarations

---

## Day 4: Collections, Sequences & Scope Functions ★

### Core Topics
| Concept | Java Equivalent | Key Difference |
|---------|-----------------|----------------|
| Collections | `java.util` | `List`, `Set`, `Map` — immutable by default |
| Sequences | Streams | Lazy evaluation, intermediate operations |
| `let`/`run`/`apply`/`also`/`with` | No equivalent | Scope functions for concise code |
| Destructuring | No equivalent | Unpack objects into variables |

### 🔴 Gaps to Add

#### 1. Collection Operations Deep Dive
```kotlin
// Map operations
val lengths = strings.map { it.length }
val pairs = strings.mapIndexed { index, s -> index to s }

// FlatMap
val nested = listOf(listOf(1, 2), listOf(3, 4))
val flat = nested.flatMap { it }  // [1, 2, 3, 4]

// Filter variants
val evens = numbers.filter { it % 2 == 0 }
val first = numbers.first { it > 5 }
val single = numbers.singleOrNull { it > 10 }  // null if not exactly one

// Fold and Reduce
val sum = numbers.reduce { acc, i -> acc + i }
val sumWithInitial = numbers.fold(0) { acc, i -> acc + i }

// Grouping
val grouped = people.groupBy { it.city }
val partitioned = numbers.partition { it > 0 }  // Pair<List, List>

// Associate
val nameMap = users.associate { it.id to it.name }
val nameMapBy = users.associateBy { it.id }
```

#### 2. Windowed and Chunked Operations
```kotlin
// Windowed (sliding window)
val windowed = listOf(1, 2, 3, 4, 5).windowed(3)
// [[1,2,3], [2,3,4], [3,4,5]]

val windowedWithStep = listOf(1, 2, 3, 4, 5).windowed(3, step = 2)
// [[1,2,3], [3,4,5]]

// Chunked (partition into groups)
val chunked = listOf(1, 2, 3, 4, 5, 6).chunked(3)
// [[1,2,3], [4,5,6]]
```

#### 3. `buildList` and `buildMap` (Kotlin 1.6+)
```kotlin
// Builder pattern for collections
val list = buildList {
    add("a")
    addAll(listOf("b", "c"))
    if (condition) {
        add("d")
    }
}

val map = buildMap {
    put("key1", "value1")
    putAll(existingMap)
    if (condition) {
        put("key2", "value2")
    }
}
```

#### 4. Scope Functions — When to Use Which
```kotlin
// let: null check + transformation
val length = str?.let {
    println("Processing: $it")
    it.length  // last expression is return value
}

// run: null check + transformation with context
val result = obj?.run {
    // 'this' is obj
    "$name is $age years old"
}

// apply: object configuration (builder pattern)
val user = User().apply {
    name = "John"
    age = 30
    email = "john@example.com"
}

// also: side effects (logging, debugging)
val numbers = mutableListOf(1, 2, 3)
    .also { println("Initial list: $it") }
    .apply { add(4) }
    .also { println("After add: $it") }

// with: group operations on non-null object
val sb = with(StringBuilder()) {
    append("Hello")
    append(" ")
    append("World")
    toString()
}

// Comparison:
// let   -> it (object), returns lambda result
// run   -> this (object), returns lambda result
// apply -> this (object), returns object itself
// also  -> it (object), returns object itself
// with  -> this (object), returns lambda result
```

#### 5. Array Operations
```kotlin
// Arrays (not lists)
val array = arrayOf(1, 2, 3)
val intArray = intArrayOf(1, 2, 3)  // primitive array

// Conversion
val list = array.toList()
val array = list.toTypedArray()
val intArray = list.toIntArray()

// Array operations
val reversed = array.reversed()
val sorted = array.sorted()
val filled = Array(5) { 0 }  // [0, 0, 0, 0, 0]
```

#### 6. Collection Mutability
```kotlin
// Immutable (read-only)
val immutableList = listOf(1, 2, 3)  // List<T>
val immutableMap = mapOf("a" to 1)   // Map<K, V>

// Mutable
val mutableList = mutableListOf(1, 2, 3)  // MutableList<T>
val mutableMap = mutableMapOf("a" to 1)   // MutableMap<K, V>

// Conversion
val immutable = mutableList.toList()
val mutable = immutableList.toMutableList()
```

### Practice
- [ ] Use scope functions to simplify nested if-else
- [ ] Chain collection operations to process data
- [ ] Implement a builder pattern using `apply`

---

## Day 5: Coroutines Part 1 — Mini-Project ★

### Core Topics
| Concept | Java Equivalent | Key Difference |
|---------|-----------------|----------------|
| `suspend fun` | `CompletableFuture` / threads | Non-blocking, sequential code style |
| `launch` vs `async` | `CompletableFuture.runAsync` vs `supplyAsync` | Structured concurrency |
| Dispatchers | Thread pools | `Dispatchers.IO`, `Dispatchers.Default` |
| Structured concurrency | No direct equivalent | Automatic cancellation, error propagation |

### 🔴 Gaps to Add

#### 1. Thread vs Coroutine Comparison
```kotlin
// Java: Thread
Thread(() -> {
    System.out.println("Running on thread");
}).start();

// Kotlin: Coroutine
GlobalScope.launch {
    println("Running on coroutine")
}

// Structured concurrency (preferred)
lifecycleScope.launch {
    println("Running in lifecycle scope")
}
```

#### 2. `runBlocking` vs `coroutineScope`
```kotlin
// runBlocking: blocks current thread, for testing/main
fun main() = runBlocking {
    // This is a coroutine scope
    launch {
        delay(1000)
        println("World!")
    }
    println("Hello")
}

// coroutineScope: suspends, doesn't block
suspend fun fetchData() = coroutineScope {
    val deferred1 = async { fetchFromApi1() }
    val deferred2 = async { fetchFromApi2() }
    // Waits for both to complete
    Result(deferred1.await(), deferred2.await())
}
```

#### 3. CoroutineExceptionHandler
```kotlin
// Global exception handler
val handler = CoroutineExceptionHandler { _, exception ->
    println("Caught: ${exception.message}")
}

// With launch
launch(handler) {
    throw RuntimeException("Error")
}

// With async (exceptions come via await())
val deferred = async {
    throw RuntimeException("Error")
}

try {
    deferred.await()
} catch (e: Exception) {
    println("Caught: ${e.message}")
}
```

#### 4. Job Hierarchy and Cancellation
```kotlin
// Parent job cancels children
val parentJob = Job()

val child1 = launch(parentJob) {
    repeat(10) { i ->
        println("Child 1: $i")
        delay(500)
    }
}

val child2 = launch(parentJob) {
    repeat(10) { i ->
        println("Child 2: $i")
        delay(300)
    }
}

delay(1000)
parentJob.cancel()  // Cancels both children

// Cooperative cancellation
launch {
    while (isActive) {  // Check isActive
        // do work
        yield()  // Or delay() for cooperative cancellation
    }
}
```

#### 5. `CoroutineScope` Management
```kotlin
// Custom scope
class MyViewModel : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    fun loadData() {
        scope.launch {
            val data = withContext(Dispatchers.IO) {
                // Fetch data on IO thread
                repository.fetch()
            }
            // Update UI on Main thread
            uiState.value = data
        }
    }

    fun cancel() {
        scope.cancel()  // Cancel all coroutines
    }
}

// Using viewModelScope (Android)
class MyViewModel : ViewModel() {
    fun loadData() {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                repository.fetch()
            }
            uiState.value = data
        }
    }
    // Automatically cancelled when ViewModel is cleared
}
```

#### 6. `withContext` for Thread Switching
```kotlin
// Switch between dispatchers
suspend fun processData(): Result = withContext(Dispatchers.Default) {
    // CPU-bound work
    val processed = data.map { heavyComputation(it) }

    withContext(Dispatchers.IO) {
        // IO-bound work
        repository.save(processed)
    }
}

// Common pattern in Android
viewModelScope.launch {
    _uiState.value = UiState.Loading
    val result = withContext(Dispatchers.IO) {
        repository.getData()
    }
    _uiState.value = UiState.Success(result)
}
```

### Practice
- [ ] Build a concurrent data fetcher with `async`
- [ ] Implement proper cancellation handling
- [ ] Create a scope-aware service class

---

## Day 6: Spring Boot with Kotlin — Setup & DI

### Core Topics
| Concept | Java Equivalent | Key Difference |
|---------|-----------------|----------------|
| `kotlin-spring` plugin | No equivalent | Auto-opens classes for Spring proxies |
| `build.gradle.kts` | `build.gradle` (Groovy) | Type-safe Kotlin DSL |
| Open classes | All classes open in Java | Need `open` for classes extended by Spring |
| DI idioms | Constructor injection | Preferred in Kotlin, no `@Autowired` needed |

### 🔴 Gaps to Add

#### 1. `build.gradle.kts` Deep Dive
```kotlin
// Kotlin DSL
plugins {
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
    kotlin("plugin.jpa") version "1.9.20"
    kotlin("plugin.allopen") version "1.9.20"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}

// Kotlin configuration
tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
    }
}
```

#### 2. Application Class with `runApplication`
```kotlin
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args) {
        // Customization
        setBannerMode(Banner.Mode.OFF)
        addInitializers(
            Beans {
                bean("customBean") { CustomService() }
            }
        )
    }
}
```

#### 3. Configuration Properties with Data Classes
```kotlin
// Java
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String name;
    private int maxRetries;
    // getters/setters
}

// Kotlin
@ConfigurationProperties(prefix = "app")
data class AppProperties(
    val name: String,
    val maxRetries: Int = 3,
    val features: Map<String, Boolean> = emptyMap()
)

// Usage
@Service
class MyService(private val props: AppProperties) {
    fun doSomething() {
        println(props.name)
    }
}
```

#### 4. Profiles in Kotlin
```kotlin
// application.yml
spring:
  profiles:
    active: dev

---
spring:
  config:
    activate:
      on-profile: dev
  datasource:
    url: jdbc:h2:mem:devdb

---
spring:
  config:
    activate:
      on-profile: prod
  datasource:
    url: jdbc:postgresql://localhost:5432/proddb
```

#### 5. Kotlin-specific Spring Configuration
```kotlin
// Using @Configuration with Kotlin
@Configuration
class DatabaseConfig {

    @Bean
    fun transactionManager(
        entityManagerFactory: EntityManagerFactory,
        dataSource: DataSource
    ): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }
}

// Using beans DSL
@Configuration
class BeansConfig {

    @Bean
    fun myService(): MyService {
        return MyService(
            repository = userRepository(),
            cache = cacheManager()
        )
    }
}
```

#### 6. Open Classes (Why Needed)
```kotlin
// Without open (default in Kotlin)
class MyService {
    fun doSomething() { }  // final
}

// Spring creates proxy extending MyService → fails at runtime

// Solution 1: Use kotlin-spring plugin
// @Configuration, @Service, @Controller, @Component are auto-opened

// Solution 2: Manually mark as open
open class MyService {
    open fun doSomething() { }
}

// Solution 3: Use allOpen plugin
plugins {
    kotlin("plugin.allopen")
}

allOpen {
    annotation("org.springframework.stereotype.Service")
    annotation("org.springframework.stereotype.Component")
    // etc.
}
```

### Practice
- [ ] Set up a Kotlin Spring Boot project from scratch
- [ ] Configure `build.gradle.kts` with common dependencies
- [ ] Create a service with constructor injection

---

## Day 7: REST API in Kotlin

### Core Topics
| Concept | Java Equivalent | Key Difference |
|---------|-----------------|----------------|
| Data class DTOs | Records / POJOs | Auto-serialization, copy() |
| Null-safe requests | Optional fields | Nullable types in request bodies |
| Sealed class errors | Exception hierarchy | Type-safe error handling |
| Idiomatic controllers | `@RestController` | Cleaner syntax, less boilerplate |

### 🔴 Gaps to Add

#### 1. Request Validation with `jakarta.validation`
```kotlin
// DTO with validation
data class CreateUserRequest(
    @field:NotBlank
    @field:Size(min = 2, max = 50)
    val name: String,

    @field:Email
    val email: String,

    @field:Min(0)
    @field:Max(150)
    val age: Int
)

// Controller
@PostMapping("/users")
fun createUser(
    @Valid @RequestBody request: CreateUserRequest
): ResponseEntity<UserResponse> {
    // Validation happens automatically
    val user = service.create(request)
    return ResponseEntity.ok(user.toResponse())
}
```

#### 2. Exception Handling with `@RestControllerAdvice`
```kotlin
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException::class)
    fun handleValidation(e: ValidationException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse(e.message ?: "Validation failed"))
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(e: NotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(e.message ?: "Not found"))
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneral(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.internalServerError()
            .body(ErrorResponse("Internal server error"))
    }
}
```

#### 3. `ResponseEntity` Usage
```kotlin
// Explicit response
@GetMapping("/users/{id}")
fun getUser(@PathVariable id: Long): ResponseEntity<User> {
    val user = service.findById(id)
        ?: return ResponseEntity.notFound().build()
    return ResponseEntity.ok(user)
}

// With headers
@PostMapping("/users")
fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<User> {
    val user = service.create(request)
    return ResponseEntity
        .created(URI.create("/users/${user.id}"))
        .header("X-User-Id", user.id.toString())
        .body(user)
}
```

#### 4. DTO Conversion Patterns
```kotlin
// Extension functions for conversion
data class User(
    val id: Long?,
    val name: String,
    val email: String
)

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String
)

data class CreateUserRequest(
    val name: String,
    val email: String
)

// Extension functions
fun User.toResponse() = UserResponse(
    id = id ?: throw IllegalStateException("User not persisted"),
    name = name,
    email = email
)

fun CreateUserRequest.toEntity() = User(
    id = null,
    name = name,
    email = email
)

// Usage in controller
@PostMapping("/users")
fun createUser(@RequestBody request: CreateUserRequest): UserResponse {
    val user = service.create(request.toEntity())
    return user.toResponse()
}
```

#### 5. Content Negotiation
```kotlin
// Multiple representations
@GetMapping("/users/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
fun getUserJson(@PathVariable id: Long): UserResponse {
    return service.findById(id).toResponse()
}

@GetMapping("/users/{id}", produces = [MediaType.APPLICATION_XML_VALUE])
fun getUserXml(@PathVariable id: Long): UserResponse {
    return service.findById(id).toResponse()
}

// Content negotiation configuration
@Configuration
class WebConfig : WebMvcConfigurer {
    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        configurer
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML)
    }
}
```

### Practice
- [ ] Build a complete CRUD REST API with validation
- [ ] Implement global exception handling
- [ ] Create DTOs with extension function conversions

---

## Day 8: Spring Data JPA with Kotlin ★

### Core Topics
| Concept | Java Equivalent | Key Difference |
|---------|-----------------|----------------|
| Open entities | Entity classes | Need `open` for JPA proxies |
| Nullable fields | `@Nullable` annotations | Language-level nullability |
| `kotlin-allopen` | No equivalent | Auto-opens annotated classes |
| Lazy loading gotchas | Same issue | Kotlin's delegation can cause issues |

### 🔴 Gaps to Add

#### 1. Entity with Nullable Fields
```kotlin
@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,  // Nullable for new entities

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = true)
    val phone: String? = null,  // Optional field

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val addresses: MutableList<Address> = mutableListOf()
) {
    // JPA requires no-arg constructor
    constructor() : this(name = "", email = "")
}
```

#### 2. Projection Interfaces
```kotlin
// Interface projections
interface UserSummary {
    val id: Long
    val name: String
}

interface UserDetails {
    val id: Long
    val name: String
    val email: String
    val addresses: List<AddressSummary>
}

interface AddressSummary {
    val city: String
    val street: String
}

// Usage
interface UserRepository : JpaRepository<User, Long> {
    fun findSummaryById(id: Long): UserSummary
    fun findDetailsById(id: Long): UserDetails
}
```

#### 3. Query Derivation
```kotlin
interface UserRepository : JpaRepository<User, Long> {

    // Simple query
    fun findByName(name: String): List<User>

    // Query with AND/OR
    fun findByNameAndEmail(name: String, email: String): User?

    // Query with LIKE
    fun findByNameContainingIgnoreCase(name: String): List<User>

    // Query with ORDER BY
    fun findByNameOrderByCreatedAtDesc(name: String): List<User>

    // Query with LIMIT
    fun findFirst5ByOrderByCreatedAtDesc(): List<User>

    // Query with IS NULL
    fun findByPhoneIsNull(): List<User>

    // Query with IN
    fun findByIdIn(ids: List<Long>): List<User>

    // Query with @Query
    @Query("SELECT u FROM User u WHERE u.createdAt > :date")
    fun findRecentUsers(@Param("date") date: LocalDateTime): List<User>

    // Native query
    @Query(value = "SELECT * FROM users WHERE name LIKE %:name%", nativeQuery = true)
    fun searchByName(@Param("name") name: String): List<User>
}
```

#### 4. Auditing with `@CreatedDate`
```kotlin
// Entity with auditing
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    val email: String,

    @CreatedDate
    @Column(updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @CreatedBy
    @Column(updatable = false)
    var createdBy: String? = null,

    @LastModifiedBy
    var lastModifiedBy: String? = null
)

// Configuration
@Configuration
@EnableJpaAuditing
class JpaConfig

// Custom auditor
class AuditorAwareImpl : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        return Optional.ofNullable(
            SecurityContextHolder.getContext().authentication?.name
        )
    }
}
```

#### 5. Repository Custom Implementation
```kotlin
// Custom repository interface
interface UserRepositoryCustom {
    fun findAllByCriteria(criteria: UserSearchCriteria): List<User>
}

// Implementation
class UserRepositoryImpl(
    private val em: EntityManager
) : UserRepositoryCustom {

    override fun findAllByCriteria(criteria: UserSearchCriteria): List<User> {
        val cb = em.criteriaBuilder
        val query = cb.createQuery(User::class.java)
        val root = query.from(User::class.java)

        val predicates = mutableListOf<Predicate>()

        criteria.name?.let {
            predicates.add(cb.like(root.get("name"), "%$it%"))
        }
        criteria.email?.let {
            predicates.add(cb.equal(root.get("email"), it))
        }

        query.where(predicates.toTypedArray())
        return em.createQuery(query).resultList
    }
}

// Main repository
interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom {
    // Spring Data methods
}
```

#### 6. Lazy Loading Gotchas
```kotlin
// Problem: LazyInitializationException
@Entity
class User(
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val addresses: List<Address> = emptyList()
)

// Solution 1: Use @Transactional
@Service
class UserService(private val userRepo: UserRepository) {

    @Transactional(readOnly = true)
    fun getUserWithAddresses(id: Long): User {
        val user = userRepo.findById(id).orElseThrow()
        // Access addresses within transaction
        println(user.addresses.size)  // OK
        return user
    }
}

// Solution 2: Use fetch join
interface UserRepository : JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.addresses WHERE u.id = :id")
    fun findByIdWithAddresses(@Param("id") id: Long): User?
}

// Solution 3: Use EntityGraph
interface UserRepository : JpaRepository<User, Long> {
    @EntityGraph(attributePaths = ["addresses"])
    fun findById(id: Long): Optional<User>
}
```

### Practice
- [ ] Create entities with proper nullability
- [ ] Implement custom repository methods
- [ ] Set up auditing

---

## Day 9: Spring Security + JWT in Kotlin

### Core Topics
| Concept | Java Equivalent | Key Difference |
|---------|-----------------|----------------|
| Kotlin DSL security config | `SecurityFilterChain` bean | Type-safe builders |
| JWT filter | `OncePerRequestFilter` | Cleaner implementation |
| `@PreAuthorize` | Same annotation | SpEL expressions |
| BCrypt | Same | Kotlin extension |
| CORS | Same configuration | DSL-based |

### 🔴 Gaps to Add

#### 1. Kotlin DSL Security Config
```kotlin
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            sessionManagement { sessionCreationPolicy = STATELESS }
            authorizeHttpRequests {
                authorize("/api/public/**", permitAll)
                authorize("/api/admin/**", hasRole("ADMIN"))
                authorize("/api/**", authenticated)
                authorize(anyRequest, denyAll)
            }
            oauth2ResourceServer { jwt { } }
            cors { }
        }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("http://localhost:3000")
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
            allowedHeaders = listOf("*")
            allowCredentials = true
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
```

#### 2. JWT Filter
```kotlin
@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(7)
        val username = jwtService.extractUsername(jwt)

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)

            if (jwtService.isTokenValid(jwt, userDetails)) {
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }

        filterChain.doFilter(request, response)
    }
}
```

#### 3. JWT Service
```kotlin
@Component
class JwtService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration}") private val expiration: Long
) {

    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(userDetails: UserDetails): String {
        return generateToken(emptyMap(), userDetails)
    }

    fun generateToken(
        claims: Map<String, Any>,
        userDetails: UserDetails
    ): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractUsername(token: String): String? {
        return extractClaim(token) { it.subject }
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun <T> extractClaim(token: String, resolver: (Claims) -> T): T {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
        return resolver(claims)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractClaim(token) { it.expiration }.before(Date())
    }
}
```

#### 4. Password Encoding
```kotlin
@Configuration
class PasswordConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

// Usage
@Service
class UserService(
    private val passwordEncoder: PasswordEncoder
) {
    fun createUser(request: CreateUserRequest): User {
        val encodedPassword = passwordEncoder.encode(request.password)
        return userRepo.save(
            User(
                name = request.name,
                email = request.email,
                password = encodedPassword
            )
        )
    }

    fun verifyPassword(raw: String, encoded: String): Boolean {
        return passwordEncoder.matches(raw, encoded)
    }
}
```

#### 5. Method Security with `@PreAuthorize`
```kotlin
// Enable method security
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class MethodSecurityConfig

// Usage
@Service
class UserService(private val userRepo: UserRepository) {

    @PreAuthorize("hasRole('ADMIN')")
    fun getAllUsers(): List<User> = userRepo.findAll()

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    fun getUser(id: Long): User = userRepo.findById(id).orElseThrow()

    @PreAuthorize("@securityService.isOwner(#id, authentication)")
    fun updateProfile(id: Long, request: UpdateProfileRequest): User {
        // Only owner can update
        val user = userRepo.findById(id).orElseThrow()
        return userRepo.save(user.copy(name = request.name))
    }
}

// Custom security service
@Component("securityService")
class SecurityService {
    fun isOwner(userId: Long, authentication: Authentication): Boolean {
        val principal = authentication.principal as UserDetails
        // Check if user owns the resource
        return principal.username == getUserEmail(userId)
    }
}
```

### Practice
- [ ] Implement complete JWT authentication flow
- [ ] Create role-based access control
- [ ] Add custom security annotations

---

## Day 10: Full Kotlin API Capstone ★

### Core Topics
- Task manager rebuilt in Kotlin
- Data classes for DTOs
- Sealed classes for errors
- JWT authentication
- JPA persistence

### 🔴 Gaps to Add

#### 1. API Versioning
```kotlin
// Path-based versioning
@RestController
@RequestMapping("/api/v1/users")
class UserV1Controller {
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserV1Response {
        // V1 implementation
    }
}

@RestController
@RequestMapping("/api/v2/users")
class UserV2Controller {
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserV2Response {
        // V2 implementation
    }
}
```

#### 2. Rate Limiting
```kotlin
// Using Bucket4j
@Configuration
class RateLimitConfig {

    @Bean
    fun rateLimiter(): Bucket {
        return Bucket.builder()
            .addLimit(
                Bandwidth.builder()
                    .capacity(100)
                    .refillGreedy(10, Duration.ofMinutes(1))
                    .build()
            )
            .build()
    }
}

@Component
class RateLimitFilter(
    private val rateLimiter: Bucket
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (rateLimiter.tryConsume(1)) {
            filterChain.doFilter(request, response)
        } else {
            response.status = HttpStatus.TOO_MANY_REQUESTS.value()
            response.writer.write("Rate limit exceeded")
        }
    }
}
```

#### 3. Caching
```kotlin
@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager {
        val config = HashMap<String, MutableMap<String, Any>>()
        val cacheConfig = mutableMapOf<String, Any>(
            "timeToLiveSeconds" to 3600,
            "maxEntries" to 1000
        )
        return ConcurrentMapCacheManager("users")
    }
}

@Service
class UserService(private val userRepo: UserRepository) {

    @Cacheable(value = ["users"], key = "#id")
    fun getUser(id: Long): User {
        // Only called if not in cache
        return userRepo.findById(id).orElseThrow()
    }

    @CacheEvict(value = ["users"], key = "#id")
    fun deleteUser(id: Long) {
        userRepo.deleteById(id)
    }

    @CachePut(value = ["users"], key = "#result.id")
    fun updateUser(id: Long, request: UpdateUserRequest): User {
        val user = userRepo.findById(id).orElseThrow()
        return userRepo.save(user.copy(name = request.name))
    }
}
```

#### 4. Health Checks
```kotlin
// Custom health indicator
@Component
class DatabaseHealthIndicator(
    private val dataSource: DataSource
) : HealthIndicator {

    override fun health(): Health {
        return try {
            dataSource.connection.use { conn ->
                if (conn.isValid(5)) {
                    Health.up()
                        .withDetail("database", "accessible")
                        .withDetail("connection", conn.metaData.databaseProductName)
                        .build()
                } else {
                    Health.down()
                        .withDetail("database", "connection invalid")
                        .build()
                }
            }
        } catch (e: Exception) {
            Health.down()
                .withDetail("database", e.message)
                .build()
        }
    }
}

// Usage
@GetMapping("/health")
fun health(): Map<String, String> {
    return mapOf("status" to "UP")
}
```

### Practice
- [ ] Build complete task manager API
- [ ] Implement all CRUD operations
- [ ] Add proper error handling

---

## Day 11: Testing in Kotlin — MockK + Kotest

### Core Topics
| Concept | Java Equivalent | Key Difference |
|---------|-----------------|----------------|
| MockK | Mockito | Kotlin-native, reified generics |
| `coEvery` | `when().thenReturn()` | Coroutine mocking |
| Kotest | JUnit 5 | Kotlin-first, matchers |
| JUnit 5 with Kotlin | JUnit 5 | Extension model |

### 🔴 Gaps to Add

#### 1. MockK Basics
```kotlin
// Mock
val userRepo = mockk<UserRepository>()

// Stub
every { userRepo.findById(1L) } returns Optional.of(testUser)

// Verify
verify { userRepo.save(any()) }

// Slot (capture arguments)
val slot = slot<User>()
every { userRepo.save(capture(slot)) } returns testUser

service.createUser(request)

assertThat(slot.captured.name).isEqualTo("John")
```

#### 2. Coroutine Testing
```kotlin
// Test suspend functions
class UserServiceTest {

    @Test
    fun `should fetch user`() = runTest {
        // Arrange
        val userRepo = mockk<UserRepository>()
        coEvery { userRepo.findById(1L) } returns Optional.of(testUser)

        val service = UserService(userRepo)

        // Act
        val result = service.getUser(1L)

        // Assert
        assertThat(result).isEqualTo(testUser)
        coVerify { userRepo.findById(1L) }
    }
}

// Test with Dispatchers
class UserServiceTest {

    @Test
    fun `should fetch user`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)

        Dispatchers.setMain(testDispatcher)

        try {
            // Test code
        } finally {
            Dispatchers.resetMain()
        }
    }
}

// Using Turbine for Flow testing
class FlowTest {

    @Test
    fun `should emit states`() = runTest {
        val viewModel = MyViewModel(repository)

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(UiState.Loading)
            assertThat(awaitItem()).isEqualTo(UiState.Success(data))
        }
    }
}
```

#### 3. Kotest Matchers
```kotlin
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import io.kotest.matchers.string.*

class UserTest : FunSpec({
    test("user should have valid name") {
        user.name shouldHaveLength 4
        user.name shouldStartWith "J"
        user.name shouldContain "ohn"
    }

    test("user list should contain user") {
        users shouldContain user
        users shouldHaveSize 3
        users shouldContainAll listOf(user1, user2)
    }

    test("user age should be in range") {
        user.age shouldBeInRange 18..65
    }

    test("optional should be present") {
        optionalUser shouldBePresent
        optionalUser.get().name shouldBe "John"
    }
})
```

#### 4. Property Testing with Kotest
```kotlin
import io.kotest.property.forAll
import io.kotest.property.forAll

class UserPropertyTest : FunSpec({

    test("name should never be blank") {
        forAll<String> { name ->
            name.isNotBlank() implies {
                val user = User(name = name, email = "test@test.com")
                user.name shouldBe name
            }
        }
    }

    test("age should always be positive") {
        forAll<Int> { age ->
            age > 0 implies {
                val user = User(name = "John", email = "test@test.com", age = age)
                user.age shouldBe age
            }
        }
    }
})
```

#### 5. Test Containers
```kotlin
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class UserRepositoryTest {

    companion object {
        @Container
        val postgres = PostgreSQLContainer("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
    }

    @BeforeEach
    fun setup() {
        // Setup test data
    }

    @Test
    fun `should save user`() {
        // Test with real database
    }
}
```

#### 6. Test Configuration
```kotlin
// application-test.yml
spring:
  datasource:
    url: jdbc:tc:postgresql:15://localhost/testdb
    driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
  jpa:
    hibernate:
      ddl-auto: create-drop

// Test profile
@SpringBootTest
@ActiveProfiles("test")
class IntegrationTest {

    @Autowired
    lateinit var userRepo: UserRepository

    @Test
    fun `should save user`() {
        val user = User(name = "John", email = "john@test.com")
        val saved = userRepo.save(user)
        assertThat(saved.id).isNotNull()
    }
}
```

### Practice
- [ ] Write unit tests for service layer
- [ ] Create integration tests with Test Containers
- [ ] Add property tests for data validation

---

## Day 12: Coroutines Part 2 — Flow + WebFlux

### Core Topics
| Concept | Java Equivalent | Key Difference |
|---------|-----------------|----------------|
| Flow | Reactive Streams / RxJava | Cold streams, sequential code |
| `StateFlow` | `BehaviorSubject` | State holder with current value |
| Spring WebFlux + coroutines | WebFlux with Reactor | Simpler syntax |
| R2DBC | JDBC | Reactive database access |

### 🔴 Gaps to Add

#### 1. Flow Operators
```kotlin
// Transform
flow.map { it * 2 }
flow.filter { it > 0 }
flow.take(5)
flow.drop(3)

// Combine
flow1.combine(flow2) { a, b -> "$a - $b" }
flow1.zip(flow2) { a, b -> "$a - $b" }

// Buffer and Conflate
flow.buffer(10)  // Buffer 10 elements
flow.conflate()  // Skip intermediate values

// Flow builder
fun simpleFlow(): Flow<Int> = flow {
    for (i in 1..10) {
        delay(100)
        emit(i)
    }
}
```

#### 2. `StateFlow` vs `SharedFlow`
```kotlin
// StateFlow: always has a value, distinct until changed
class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val data = repository.getData()
            _uiState.value = UiState.Success(data)
        }
    }
}

// SharedFlow: hot flow, no initial value
class MyViewModel : ViewModel() {
    private val _events = MutableSharedFlow<Event>()
    val events: SharedFlow<Event> = _events.asSharedFlow()

    fun showError(message: String) {
        viewModelScope.launch {
            _events.emit(Event.ShowError(message))
        }
    }
}
```

#### 3. `callbackFlow`
```kotlin
// Convert callback-based API to Flow
fun locationUpdates(): Flow<Location> = callbackFlow {
    val listener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            trySend(location)  // Non-suspending send
        }
    }

    locationManager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER,
        0L,
        0f,
        listener
    )

    awaitClose {
        locationManager.removeUpdates(listener)
    }
}

// Usage
locationUpdates()
    .catch { e -> emit(defaultLocation) }
    .collect { location ->
        updateUI(location)
    }
```

#### 4. R2DBC Repositories
```kotlin
// Entity
@Table("users")
data class User(
    @Id
    val id: Long?,
    val name: String,
    val email: String
)

// Repository
interface UserRepository : ReactiveCrudRepository<User, Long> {

    fun findByName(name: String): Mono<User>

    fun findByEmail(email: String): Flow<User>

    @Query("SELECT * FROM users WHERE name LIKE '%' || :name || '%'")
    fun searchByName(@Param("name") name: String): Flow<User>
}

// Service
@Service
class UserService(private val userRepo: UserRepository) {

    fun getUser(id: Long): Mono<User> {
        return userRepo.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("User not found")))
    }

    fun getAllUsers(): Flow<User> {
        return userRepo.findAll()
    }

    fun createUser(request: CreateUserRequest): Mono<User> {
        val user = User(
            id = null,
            name = request.name,
            email = request.email
        )
        return userRepo.save(user)
    }
}
```

#### 5. WebFlux with Coroutines
```kotlin
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    suspend fun getUser(@PathVariable id: Long): User {
        return userService.getUser(id)
            .orElseThrow { NotFoundException("User not found") }
    }

    @GetMapping
    fun getAllUsers(): Flow<User> {
        return userService.getAllUsers()
    }

    @PostMapping
    suspend fun createUser(@RequestBody request: CreateUserRequest): User {
        return userService.createUser(request)
    }
}
```

#### 6. Flow Exception Handling
```kotlin
// Catch operator
flow {
    emit(1)
    emit(2)
    throw RuntimeException("Error")
}
.catch { e ->
    println("Caught: ${e.message}")
    emit(0)  // Emit default value
}
.collect { println(it) }

// OnCompletion
flow {
    emit(1)
    emit(2)
}
.onCompletion { cause ->
    if (cause != null) {
        println("Flow completed with error: ${cause.message}")
    } else {
        println("Flow completed successfully")
    }
}
.collect { println(it) }
```

### Practice
- [ ] Create a Flow-based data stream
- [ ] Implement R2DBC repository
- [ ] Build WebFlux REST API with coroutines

---

## Day 13: Docker + CI/CD + Deploy ★

### Core Topics
- Multi-stage Dockerfile
- docker-compose
- GitHub Actions
- Railway deploy

### 🔴 Gaps to Add

#### 1. Multi-stage Dockerfile for Kotlin
```dockerfile
# Stage 1: Build
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# Stage 2: Run
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### 2. Docker Compose with Services
```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - DB_HOST=postgres
      - DB_PORT=5432
      - DB_NAME=mydb
    depends_on:
      - postgres
      - redis
    networks:
      - app-network

  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: mydb
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - app-network

  redis:
    image: redis:7-alpine
    networks:
      - app-network

volumes:
  postgres_data:

networks:
  app-network:
```

#### 3. GitHub Actions CI/CD
```yaml
name: CI/CD

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v3

      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Setup Gradle
        uses: gradle/gradle-build-action@v2

      - name: Build with Gradle
        run: ./gradlew build

      - name: Run tests
        run: ./gradlew test

      - name: Build Docker image
        if: github.ref == 'refs/heads/main'
        run: docker build -t myapp:${{ github.sha }} .

      - name: Push to registry
        if: github.ref == 'refs/heads/main'
        run: |
          echo "${{ secrets.DOCKER_PASSWORD }}" | docker login -u "${{ secrets.DOCKER_USERNAME }}" --password-stdin
          docker tag myapp:${{ github.sha }} myregistry/myapp:latest
          docker push myregistry/myapp:latest
```

#### 4. Railway Deployment
```yaml
# railway.json
{
  "build": {
    "builder": "NIXPACKS"
  },
  "deploy": {
    "startCommand": "java -jar build/libs/*.jar",
    "healthcheckPath": "/actuator/health",
    "restartPolicyType": "ON_FAILURE",
    "restartPolicyMaxRetries": 3
  }
}
```

#### 5. Health Checks and Monitoring
```yaml
# Docker health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Spring Boot Actuator
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: when-authorized
```

### Practice
- [ ] Create multi-stage Dockerfile
- [ ] Set up CI/CD pipeline
- [ ] Deploy to cloud platform

---

## Day 14: Advanced Kotlin — DSLs, Reified & Arrow

### Core Topics
| Concept | Java Equivalent | Key Difference |
|---------|-----------------|----------------|
| Type-safe DSL builders | Builder pattern | Compile-time safety |
| `inline` + `reified` | Type erasure workarounds | Access generic types at runtime |
| Arrow Either | No equivalent | Functional error handling |
| Functional patterns | Streams, Optional | More comprehensive FP support |

### 🔴 Gaps to Add

#### 1. Type-Safe DSL Builders
```kotlin
// HTML DSL
fun html(init: HTML.() -> Unit): HTML {
    return HTML().apply(init)
}

class HTML {
    private val children = mutableListOf<HTMLElement>()

    fun head(init: Head.() -> Unit) {
        children.add(Head().apply(init))
    }

    fun body(init: Body.() -> Unit) {
        children.add(Body().apply(init))
    }

    override fun toString(): String {
        return "<html>${children.joinToString("")}</html>"
    }
}

// Usage
val page = html {
    head {
        title("My Page")
    }
    body {
        h1 { +"Hello World" }
        p { +"This is a paragraph" }
    }
}
```

#### 2. `inline` + `reified` Types
```kotlin
// Without reified: type erasure
fun <T> deserialize(json: String, clazz: Class<T>): T {
    return objectMapper.readValue(json, clazz)
}

// With reified: access type at runtime
inline fun <reified T> deserialize(json: String): T {
    return objectMapper.readValue(json, T::class.java)
}

// Usage
val user = deserialize<User>(jsonString)

// With reified in lambdas
inline fun <reified T : Any> Any.isType(): Boolean {
    return this is T
}

// Filter by type
val mixedList = listOf(1, "hello", 3.14, "world")
val strings = mixedList.filterIsInstance<String>()  // ["hello", "world"]
```

#### 3. Arrow Either
```kotlin
import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right

// Define error types
sealed interface UserError {
    data object NotFound : UserError
    data object AlreadyExists : UserError
    data class Validation(val message: String) : UserError
}

// Service returning Either
fun findUser(id: Long): Either<UserError, User> {
    return userRepo.findById(id)
        .toEither { UserError.NotFound }
}

// Chaining operations
fun updateUser(id: Long, request: UpdateUserRequest): Either<UserError, User> {
    return findUser(id)
        .flatMap { user ->
            validateUpdate(request)
                .map { user.copy(name = request.name) }
        }
        .flatMap { user ->
            Either.catch { userRepo.save(user) }
                .mapLeft { UserError.Validation(it.message ?: "Save failed") }
        }
}

// Handling
when (val result = updateUser(1L, request)) {
    is Left -> when (result.value) {
        is UserError.NotFound -> ResponseEntity.notFound().build()
        is UserError.AlreadyExists -> ResponseEntity.badRequest().build()
        is UserError.Validation -> ResponseEntity.badRequest().build()
    }
    is Right -> ResponseEntity.ok(result.value)
}
```

#### 4. Delegated Properties
```kotlin
// Lazy delegation
val expensiveValue: String by lazy {
    println("Computing...")
    "result"
}

// Observable delegation
var name: String by Delegates.observable("initial") { _, old, new ->
    println("$old -> $new")
}

// Vetoable delegation (can reject changes)
var age: Int by Delegates.vetoable(0) { _, _, new ->
    new >= 0  // Only allow non-negative values
}

// Map delegation
class User(map: Map<String, Any>) {
    val name: String by map
    val age: Int by map
}

val user = User(mapOf("name" to "John", "age" to 30))
```

#### 5. Context Receivers (Kotlin 1.6.20+)
```kotlin
// Define context
class Logger {
    fun log(message: String) { println(message) }
}

class Database {
    fun query(sql: String): List<Any> { return emptyList() }
}

// Context receiver
context(Logger, Database)
fun findUser(id: Long): User? {
    log("Finding user $id")
    val results = query("SELECT * FROM users WHERE id = $id")
    return results.firstOrNull() as? User
}

// Usage
with(Logger()) {
    with(Database()) {
        findUser(1)  // Both contexts available
    }
}
```

#### 6. Contracts
```kotlin
import kotlin.contracts.*

// Contract: if condition is true, lambda will execute
fun requireNotNull(value: Any?): Any {
    contract {
        returns() implies (value != null)
    }
    if (value == null) throw IllegalArgumentException("Value must not be null")
    return value
}

// Usage
fun process(value: Any?) {
    requireNotNull(value)
    // Smart cast: value is now non-null
    println(value.length)  // No error
}

// Custom contract
inline fun <T> executeIfNotNull(value: T?, block: (T) -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE) implies (value != null)
    }
    if (value != null) {
        block(value)
    }
}
```

### Practice
- [ ] Build a type-safe DSL for your domain
- [ ] Implement functional error handling with Either
- [ ] Use delegated properties for configuration

---

## Day 15: Final Capstone — Full Kotlin Production App ★★

### Core Topics
- Kotlin-first design
- Coroutines
- MockK tests >70%
- Docker + CI/CD deployed

### 🔴 Gaps to Add

#### 1. Kotlin-First Design Principles
```kotlin
// Prefer immutability
data class User(
    val id: Long,
    val name: String,
    val email: String
) {
    fun withName(newName: String): User = copy(name = newName)
}

// Use sealed types for state
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val exception: Throwable) : UiState<Nothing>
}

// Use extension functions for behavior
fun User.toDto(): UserDto = UserDto(
    id = id,
    name = name,
    email = email
)

// Use higher-order functions for behavior
fun <T> retry(times: Int, block: () -> T): T {
    repeat(times - 1) {
        try { return block() } catch (e: Exception) { }
    }
    return block()
}
```

#### 2. Performance Tuning
```kotlin
// Use value classes for zero-overhead wrappers
@JvmInline
value class UserId(val value: Long)

// Use inline functions for lambda performance
inline fun <T> measureTime(block: () -> T): Pair<T, Long> {
    val start = System.nanoTime()
    val result = block()
    return result to System.nanoTime() - start
}

// Use sequences for large datasets
val result = bigList.asSequence()
    .filter { it.isActive }
    .map { it.toDto() }
    .take(100)
    .toList()
```

#### 3. Monitoring and Observability
```kotlin
// Micrometer metrics
@Component
class Metrics(
    private val meterRegistry: MeterRegistry
) {
    fun recordRequest(duration: Duration) {
        meterRegistry.timer("http.requests")
            .register(duration)
    }

    fun incrementCounter(name: String) {
        meterRegistry.counter(name).increment()
    }
}

// Structured logging
@Service
class UserService(
    private val logger: KLogging()
) {
    fun findUser(id: Long): User? {
        logger.info { "Finding user with id: $id" }
        val user = userRepo.findById(id)
        if (user == null) {
            logger.warn { "User not found: $id" }
        }
        return user
    }
}
```

#### 4. Code Review Checklist
```kotlin
// Checklist for Kotlin code review

// 1. Null safety
// - Use nullable types appropriately
// - Avoid !! operator
// - Use let/run for null checks

// 2. Immutability
// - Prefer val over var
// - Use data classes for DTOs
// - Return new instances instead of mutating

// 3. Scope functions
// - Use apply for object configuration
// - Use let for null checks and transformations
// - Use also for side effects

// 4. Coroutines
// - Use structured concurrency
// - Handle exceptions properly
// - Use appropriate dispatchers

// 5. Testing
// - Write unit tests for business logic
// - Use MockK for mocking
// - Test coroutine behavior

// 6. Performance
// - Use inline functions for lambdas
// - Consider value classes for wrappers
// - Use sequences for large datasets
```

---

## Summary: Key Differences from Java

| Aspect | Java 17+ | Kotlin 2 |
|--------|----------|----------|
| **Null Safety** | `Optional<T>` | `T?` language-level |
| **Immutability** | `final` keyword | `val` (reference) + immutable collections |
| **Data Classes** | Records | `data class` (more flexible) |
| **Pattern Matching** | `switch` + `sealed` | `when` (more powerful) |
| **Extension Functions** | Static methods | `fun Type.method()` |
| **Coroutines** | Threads / CompletableFuture | `suspend fun` |
| **Scope Functions** | No equivalent | `let`/`run`/`apply`/`also`/`with` |
| **DSL Building** | Builder pattern | Type-safe builders |
| **Error Handling** | Exceptions | Exceptions + `Result<T>` + Arrow `Either` |
| **Testing** | Mockito | MockK (Kotlin-native) |

## Additional Topics to Consider

1. **Kotlin Multiplatform** — Shared code across JVM, JS, Native
2. **Kotlin/JS** — Web frontend with Kotlin
3. **Kotlin/Native** — iOS/Android native development
4. **Compose Multiplatform** — UI across platforms
5. **Ktor** — Kotlin-native web framework
6. **Arrowkt** — Functional programming library

---

**Note:** This plan assumes 1-2 hours of daily study and practice. Adjust the pace based on your comfort level and existing Java knowledge.
