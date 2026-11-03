package test;

@interface GetMapping {
    String value() default "";
}

@interface RestController {}

interface Pageable {}

interface Page<T> {}

interface Repository<T, ID> { java.util.List<T> findAll(); }

interface JpaRepository<T, ID> extends org.springframework.data.repository.Repository<T, ID> {
    java.util.List<T> findAll();
    Page<T> findAll(Pageable pageable);
}

class User {}

interface UserRepository extends JpaRepository<User, Long> {}

@RestController
class UserController {

    private final UserRepository repository = null;

    // BAD
    @GetMapping("/users")
    public java.util.List<User> getUsers() {
        return repository.findAll();
    }

    // GOOD
    @GetMapping("/users-paged")
    public Page<User> getUsersPaged(Pageable pageable) {
        return repository.findAll(pageable);
    }
}

interface CustomUserRepository extends Repository<User, Long> {}

@RestController
class CustomRepositoryController {

    private final CustomUserRepository repository = null;

    // GOOD: Repository is not a Spring Data type
    @GetMapping("/custom-users")
    public java.util.List<User> getCustomUsers() {
        return repository.findAll();
    }
}
