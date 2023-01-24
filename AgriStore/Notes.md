# Notes

@Entity     //Create POJO -> Represent the table in the DB using the JPA



___
### Custom Validation Logic
If predefine valid method is not completing the business requirement
in that case we can use custom validation

 ```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {
    //Error message
    String message() default "Invalid Image Name";
    //Represent group of constraints
    Class<?>[] groups() default {};
    //Addition information about annotation
    Class<? extends Payload>[] payload() default {};
}
 ```
```java
public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String> {
    private Logger logger = LoggerFactory.getLogger(ImageNameValid.class);
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // Logic
        logger.info("Message From isValid :{} ", value);
        if (value.isBlank()) {
            return false;
        } else {
            return true;
        }
    }
}
```