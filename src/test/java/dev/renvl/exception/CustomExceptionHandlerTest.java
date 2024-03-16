package dev.renvl.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomExceptionHandlerTest {

    @InjectMocks
    private CustomExceptionHandler customExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Test
    public void testHandleAllExceptions() {
        Exception ex = new Exception("Test exception");
        ResponseEntity<Object> responseEntity = customExceptionHandler.handleAllExceptions(ex, webRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void testHandleRecordNotFoundException() {
        RecordNotFoundException ex = new RecordNotFoundException("Record not found");
        ResponseEntity<Object> responseEntity = customExceptionHandler.handleRecordNotFoundException(ex, webRequest);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testHandleInsufficientFundsException() {
        InsufficientFundsException ex = new InsufficientFundsException("Insufficient funds");
        ResponseEntity<Object> responseEntity = customExceptionHandler.handleInsufficientFundsException(ex, webRequest);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
    }

    @Test
    public void testHandleMethodArgumentNotValid() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        List<ObjectError> errors = new ArrayList<>();
        errors.add(new ObjectError("field", "Error message"));
        when(bindingResult.getAllErrors()).thenReturn(errors);

        ResponseEntity<Object> responseEntity = customExceptionHandler.handleMethodArgumentNotValid(ex, null, null, webRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
