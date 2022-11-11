package com.example.TDD.resources.exceptions;

import com.example.TDD.services.Exceptions.CantSaveUser;
import com.example.TDD.services.Exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionsHandlerTest {
    @InjectMocks
    private GlobalExceptionsHandler globalExceptionsHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void whenObjectNotFoundExceptionThenReturnResponseEntity() {
        ResponseEntity<StandardError> response = globalExceptionsHandler.
                httpException(new ObjectNotFoundException("not found!"), new MockHttpServletRequest());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getStatusCode());
        assertEquals(StandardError.class,response.getBody().getClass());
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(404,response.getBody().getStatus());
        assertNotNull(response.getBody().getPath()); //it return Null because you arent access the endPoint
    }

    @Test
    void testHttpException() {
        ResponseEntity<StandardError> response = globalExceptionsHandler.
                httpException(new CantSaveUser("cant save!"), new MockHttpServletRequest());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getStatusCode());
        assertEquals(StandardError.class,response.getBody().getClass());
        assertEquals(401,response.getBody().getStatus());
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
    }
}