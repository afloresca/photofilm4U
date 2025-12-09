package edu.uoc.epcsd.course.infrastructure.repository.rest;

import edu.uoc.epcsd.course.domain.Course;
import edu.uoc.epcsd.course.domain.repository.RestMicrocredentialsRepositoryUOC;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Log4j2
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RestMicrocredentialsRepositoryUOCImpl implements RestMicrocredentialsRepositoryUOC {

	public void createMicrocredentials(Course course) {
		
		String url = "https://api.uoc.edu/microcredentials/create";
		
		try {

	        log.info("Sent the credentials request to "+url);
	        
		} catch (RestClientException e) {		       
		      throw new IllegalArgumentException(url+" does not exist !");
		}
    }
}
