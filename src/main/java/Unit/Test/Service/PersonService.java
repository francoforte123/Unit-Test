package Unit.Test.Service;

import Unit.Test.Entities.Person;
import Unit.Test.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private UserRepository userRepository;


    public List<Person> findAll() {
        return userRepository.findAll();
    }

    public Optional<Person> findById(Long id) {
        return userRepository.findById(id);
    }

    public void save(Person person) {
        userRepository.save(person);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
