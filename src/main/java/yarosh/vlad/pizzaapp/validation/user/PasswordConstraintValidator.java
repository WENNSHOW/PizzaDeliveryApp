package yarosh.vlad.pizzaapp.validation.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;
import yarosh.vlad.pizzaapp.constant.ErrorMessages;

import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		PasswordValidator validator = new PasswordValidator(
				Arrays.asList(
						// at least 8 characters
						new LengthRule(8, 100),

						// at least one upper-case character
						new CharacterRule(EnglishCharacterData.UpperCase, 1),

						// at least one lower-case character
						new CharacterRule(EnglishCharacterData.LowerCase, 1),

						// at least one digit character
						new CharacterRule(EnglishCharacterData.Digit, 1),

						// at least one symbol (special character)
						new CharacterRule(EnglishCharacterData.Special, 1),

						// no whitespace
						new WhitespaceRule()
				));

		RuleResult result = validator.validate(new PasswordData(value));
		if (result.isValid()) {
			return true;
		}

        context.buildConstraintViolationWithTemplate(ErrorMessages.INVALID_PASSWORD)
				.addConstraintViolation()
				.disableDefaultConstraintViolation();

		return false;
	}
}
