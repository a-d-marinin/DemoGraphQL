package ru.innoseti.demo.dao;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.innoseti.demo.config.MyPostgreSQLContainer;
import ru.innoseti.demo.dto.AuthorInput;
import ru.innoseti.demo.model.Author;
import ru.innoseti.demo.repository.AuthorRepository;
import ru.innoseti.demo.repository.BookRepository;
import ru.innoseti.demo.service.AuthorService;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AuthorDaoIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    private static final String TEST_AUTHOR_NAME = "Test Author";
    private static final String TEST_BOOK_TITLE_1 = "Test Book 1";
    private static final String TEST_BOOK_TITLE_2 = "Test Book 2";

    private static final int EXPOSED_PORT = 5432;

    @Container
    public static MyPostgreSQLContainer postgreSQLContainer = new MyPostgreSQLContainer();

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        dynamicPropertyRegistry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
    }

    @BeforeAll
    private static void getPort(){
        Integer firstMappedPort = postgreSQLContainer.getMappedPort(EXPOSED_PORT);
        System.out.println("Port for DB connection: " + firstMappedPort);
    }

    @BeforeEach
    public void setup() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }


    private AuthorInput createTestAuthorInput() {
        Set<String> bookTitles = Set.of(TEST_BOOK_TITLE_1, TEST_BOOK_TITLE_2);
        return new AuthorInput(TEST_AUTHOR_NAME, bookTitles);
    }

    @Test
    public void testSaveAuthor() {
        AuthorInput authorInput = createTestAuthorInput();

        Author savedAuthor = authorService.saveAuthor(authorInput);

        assertNotNull(savedAuthor.getId());
        assertEquals(TEST_AUTHOR_NAME, savedAuthor.getName());
        assertEquals(authorInput.books().size(), savedAuthor.getBooks().size());

        Author retrievedAuthor = entityManager.find(Author.class, savedAuthor.getId());
        assertNotNull(retrievedAuthor);
        assertEquals(savedAuthor.getName(), retrievedAuthor.getName());
        assertEquals(savedAuthor.getBooks().size(), retrievedAuthor.getBooks().size());
    }

    @Test
    public void testGetAuthor() {
        AuthorInput authorInput = createTestAuthorInput();

        Author savedAuthor = authorService.saveAuthor(authorInput);

        Optional<Author> retrievedAuthor = authorService.getAuthor(TEST_AUTHOR_NAME);

        assertTrue(retrievedAuthor.isPresent());
        assertEquals(savedAuthor.getId(), retrievedAuthor.get().getId());
        assertEquals(savedAuthor.getName(), retrievedAuthor.get().getName());
        assertEquals(savedAuthor.getBooks().size(), retrievedAuthor.get().getBooks().size());
    }

}
