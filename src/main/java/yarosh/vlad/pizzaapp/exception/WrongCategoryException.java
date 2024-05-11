package yarosh.vlad.pizzaapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongCategoryException extends RuntimeException {

    private final String category;

    public WrongCategoryException(String category) {
        this.category = category;
    }
}
