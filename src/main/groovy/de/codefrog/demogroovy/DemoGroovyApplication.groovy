package de.codefrog.demogroovy

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RestResource

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import java.time.LocalDate
import java.util.stream.IntStream

@SpringBootApplication
class DemoGroovyApplication {

    @Bean
    CommandLineRunner runner(TodoRepository todoRepository) {
        return { args ->
            IntStream.range(1, 1000).forEach({ i ->
                todoRepository.save(new Todo("TODO " + i, LocalDate.now().plusDays(i)))
            })
            todoRepository.findAll().forEach({i -> println i})
        }
    }

    static void main(String[] args) {
        SpringApplication.run DemoGroovyApplication, args
    }
}

interface TodoRepository extends JpaRepository<Todo, Long> {
    @RestResource(path = "by-description")
    Collection<Todo> findByDescription(@Param("description") String description)
}

@Entity
class Todo {
    @Id
    @GeneratedValue
    private Long id
    private String description
    private LocalDate due

    Todo() {
    }

    Todo(String description, LocalDate due) {
        this.description = description
        this.due = due
    }

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    LocalDate getDue() {
        return due
    }

    void setDue(LocalDate due) {
        this.due = due
    }
}