package globalincidents

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.Collections



@SpringBootApplication
class Application

fun main(args: Array<String>) {
    val app = SpringApplication(Application::class.java)
    app.setDefaultProperties(Collections.singletonMap<String, Any>("server.port", "8081"))
    app.run(*args)
}