package edu.uoc.epcsd.course.domain.repository;

public interface UserRepository {

    boolean findUserByEmail(String email);

}
