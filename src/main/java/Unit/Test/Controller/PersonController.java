package Unit.Test.Controller;

import Unit.Test.Entities.Person;
import Unit.Test.Repository.UserRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class PersonController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public @ResponseBody Person create(@RequestBody Person person) {
        return userRepository.save(person);
    }

    @GetMapping("/{id}")
    public @ResponseBody Person getAStudent(@PathVariable long id) {
        Optional<Person> person = userRepository.findById(id);
        if (person.isEmpty()) return null;
        return person.get();
    }

    @GetMapping("/all")
    public @ResponseBody List<Person> getUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/{id}")
    public @ResponseBody Person update(@PathVariable Long id, @RequestBody @NotNull Person person) {
        person.setId(id);
        return userRepository.save(person);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        userRepository.deleteById(id);
    }
}
