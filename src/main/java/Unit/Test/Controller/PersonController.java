package Unit.Test.Controller;

import Unit.Test.Entities.Person;
import Unit.Test.Service.PersonService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public @ResponseBody void create(@RequestBody Person person) {
        personService.save(person);
    }

    @GetMapping("/{id}")
    public @ResponseBody HttpStatus getAStudent(@PathVariable long id) {
        Optional<Person> person = personService.findById(id);
        if (person.isPresent()) person.get();
        return HttpStatus.NOT_FOUND;
    }

    @GetMapping("/all")
    public @ResponseBody List<Person> getUsers() {
        return personService.findAll();
    }

    @PutMapping("/{id}")
    public @ResponseBody void update(@PathVariable Long id, @RequestBody @NotNull Person person) {
        person.setId(id);
        personService.save(person);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        personService.deleteById(id);
    }
}
