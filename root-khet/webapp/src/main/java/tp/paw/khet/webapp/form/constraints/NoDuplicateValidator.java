package tp.paw.khet.webapp.form.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import tp.paw.khet.webapp.form.wrapper.VideoStringWrapper;

public class NoDuplicateValidator implements ConstraintValidator<NoDuplicateVideos, VideoStringWrapper[]>{
    
    public void initialize(NoDuplicateVideos constraintAnnotation) {        
    }

    public boolean isValid(VideoStringWrapper[] value, ConstraintValidatorContext context) {
    	if (value == null)
    		return true;
    	
        for (int i = 0; i < value.length; i++) {
        	if (value[i] != null && value[i].hasValidUrl()) {
	            for (int j = i+1; j < value.length; j++)
	                if (value[j] != null && value[j].hasValidUrl() && value[i].getVideoId().equals(value[j].getVideoId()))
	                    return false;
        	}
        }
        
        return true;
    }

}
