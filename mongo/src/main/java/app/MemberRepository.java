package app;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Fatma AÇAR on 13.04.2016.
 */
public interface MemberRepository extends MongoRepository<Member, String> {

}
