package de.ait.tr.g_33_shop.controller;

import de.ait.tr.g_33_shop.domain.dto.ProductDto;
import de.ait.tr.g_33_shop.domain.entity.Role;
import de.ait.tr.g_33_shop.domain.entity.User;
import de.ait.tr.g_33_shop.repository.ProductRepository;
import de.ait.tr.g_33_shop.repository.RoleRepository;
import de.ait.tr.g_33_shop.repository.UserRepository;
import de.ait.tr.g_33_shop.security.sec_dto.TokenResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.annotation.Id;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ProductRepository productRepository;

    private TestRestTemplate template;
    private HttpHeaders headers;
    private ProductDto testProduct;
    private String adminAccessToken;
    private String userAccessToken;
    private static Long savedProductId;

    private final String TEST_PRODUCT_TITLE = "Test product";
    private final BigDecimal TEST_PRODUCT_PRICE = new BigDecimal(777);
    private final String TEST_ADMIN_NAME = "Test Admin";
    private final String TEST_USER_NAME = "Test User";
    private final String TEST_PASSWORD = "Test password";
    private final String ROLE_ADMIN_TITLE = "ROLE_ADMIN";
    private final String ROLE_USER_TITLE = "ROLE_USER";

    private final String URL_PREFIX = "http://localhost:";
    private final String AUTH_RESOURCE_NAME = "/auth";
    private final String PRODUCTS_RESOURCE_NAME = "/products";
    private final String LOGIN_ENDPOINT = "/login";
    private final String ALL_ENDPOINT = "/all";

    // Bearer 987f8snjsbfsf87fshfjbaf8fy7sgfsbfhsdf
    private final String BEARER_PREFIX = "Bearer ";
    private final String AUTH_HEADER_NAME = "Authorization";

    private final String ID_PARAM_NAME="?id=";

    @BeforeEach
    public void setUp() {
        template = new TestRestTemplate();
        headers = new HttpHeaders();

        testProduct = new ProductDto();
        testProduct.setTitle(TEST_PRODUCT_TITLE);
        testProduct.setPrice(TEST_PRODUCT_PRICE);

        BCryptPasswordEncoder encoder = null;
        Role roleAdmin;
        Role roleUser = null;

        User admin = userRepository.findByUsername(TEST_ADMIN_NAME).orElse(null);
        User user = userRepository.findByUsername(TEST_USER_NAME).orElse(null);

        if (admin == null) {
            encoder = new BCryptPasswordEncoder();
            roleAdmin = roleRepository.findByTitle(ROLE_ADMIN_TITLE).orElseThrow(
                    () -> new RuntimeException("Role Admin is missing in the database")
            );

            roleUser = roleRepository.findByTitle(ROLE_USER_TITLE).orElseThrow(
                    () -> new RuntimeException("Role User is missing in the database")
            );

            admin = new User();
            admin.setUsername(TEST_ADMIN_NAME);
            admin.setPassword(encoder.encode(TEST_PASSWORD));
            admin.setRoles(Set.of(roleAdmin, roleUser));

            userRepository.save(admin);
        }

        if (user == null) {
            encoder = encoder == null ? new BCryptPasswordEncoder() : encoder;

            roleUser = roleUser == null ? roleRepository.findByTitle(ROLE_USER_TITLE).orElseThrow(
                    () -> new RuntimeException("Role User is missing in the database")
            ) : roleUser;

            user = new User();
            user.setUsername(TEST_USER_NAME);
            user.setPassword(encoder.encode(TEST_PASSWORD));
            user.setRoles(Set.of(roleUser));

            userRepository.save(user);
        }

        admin.setPassword(TEST_PASSWORD);
        admin.setRoles(null);

        user.setPassword(TEST_PASSWORD);
        user.setRoles(null);

        // POST -> http://localhost:8080/auth/login

        // Получаем аксесс токен для админа
        String url = URL_PREFIX + port + AUTH_RESOURCE_NAME + LOGIN_ENDPOINT;
        HttpEntity<User> request = new HttpEntity<>(admin, headers);

        ResponseEntity<TokenResponseDto> response = template
                .exchange(url, HttpMethod.POST, request, TokenResponseDto.class);

        assertTrue(response.hasBody(), "Auth response body is empty");
        adminAccessToken = BEARER_PREFIX + response.getBody().getAccessToken();

        // Получаем аксесс токен для юзера
        request = new HttpEntity<>(user, headers);

        response = template
                .exchange(url, HttpMethod.POST, request, TokenResponseDto.class);

        assertTrue(response.hasBody(), "Auth response body is empty");
        userAccessToken = BEARER_PREFIX + response.getBody().getAccessToken();
    }

    @Test
    public void positiveGettingAllProductsWithoutAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME + ALL_ENDPOINT;
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<ProductDto[]> response = template
                .exchange(url, HttpMethod.GET, request, ProductDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");
        assertTrue(response.hasBody(), "Response doesn't have a body");
    }

    @Test
    public void negativeSavingProductWithoutAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME;
        HttpEntity<ProductDto> request = new HttpEntity<>(testProduct, headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.POST, request, ProductDto.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
        assertFalse(response.hasBody(), "Response has unexpected body");
    }

    @Test
    public void negativeSavingProductWithUserAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME;
        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken));
        HttpEntity<ProductDto> request = new HttpEntity<>(testProduct, headers);
        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.POST, request, ProductDto.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
        assertFalse(response.hasBody(), "Response has unexpected body");
    }

    // TODO домашнее задание

    @Test
    @Order(1)
    public void positiveSavingProductWithAdminAuthorization() {
        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME;
        headers.put(AUTH_HEADER_NAME, List.of(adminAccessToken));
        HttpEntity<ProductDto> request = new HttpEntity<>(testProduct, headers);
        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.POST, request, ProductDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");
        assertNotNull(response.getBody(), "Response body is null");
        assertEquals(TEST_PRODUCT_TITLE, response.getBody().getTitle(), "Product name does not match");
        savedProductId = response.getBody().getId();
    }

    @Test
    @Order(2)
    public void negativeGettingProductByIdWithoutAuthorization() {
        // TODO домашнее задание

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME + savedProductId; // Пример URL с ID продукта

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, request, String.class);

        // Проверка статуса ответа
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
    }

    @Test
    @Order(3)
    public void negativeGettingProductByIdWithBasicAuthorization() {
        // TODO домашнее задание

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME + "/"+savedProductId; // Пример URL с ID продукта

        // Отправка GET-запроса с базовой авторизацией
        ResponseEntity<String> response = template
                .withBasicAuth(TEST_USER_NAME, TEST_PASSWORD)
                .exchange(url, HttpMethod.GET, null, String.class);

        // Проверка статуса ответа
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");

//        ResponseEntity<ProductDto> response = template
//                .withBasicAuth(TEST_USER_NAME, TEST_PASSWORD)
//                .exchange(url, HttpMethod.POST, request, ProductDto.class);
    }

    @Test
    @Order(4)
    public void negativeGettingProductByIdWithIncorrectToken() {
        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME + savedProductId; // Пример URL с ID продукта

        // Инициализация заголовков с неправильным токеном
        HttpHeaders headers = new HttpHeaders();
        headers.put(AUTH_HEADER_NAME, List.of("Bearer incorrect_token"));
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Создание запроса
        HttpEntity<Void> request = new HttpEntity<>(headers);
        // Отправка GET-запроса
        ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, request, String.class);
        // Проверка статуса ответа
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
        // TODO домашнее задание
    }

    @Test
    @Order(5)
    public void positiveGettingProductByIdWithCorrectToken() {
        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME + ID_PARAM_NAME + savedProductId;
        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken));
        HttpEntity<ProductDto> request = new HttpEntity<>(headers);
        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.GET, request, ProductDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");
        assertNotNull(response.getBody(), "Response body is null");
        assertEquals(TEST_PRODUCT_TITLE, response.getBody().getTitle(), "Product name does not match");

        productRepository.deleteById(savedProductId);
    }


    }
