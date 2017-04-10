package tp.paw.khet.webapp.form.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoDuplicateValidator implements ConstraintValidator<NoDuplicateValues, Object[]>{
    
    public void initialize(NoDuplicateValues constraintAnnotation) {        
    }

    public boolean isValid(Object[] value, ConstraintValidatorContext context) {
        for (int i = 0; i < value.length; i++)
            for (int j = i+1; j < value.length; j++)
                if (value[i].equals(value[j]))
                    return false;
        return true;
    }

}
