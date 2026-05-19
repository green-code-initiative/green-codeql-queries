package test;

@interface GetMapping {
    String value() default "";
}

@interface RestController {}

interface Pageable {}

interface Page<T> {}

interface Repository<T, ID> {}

interface JpaRepository<T, ID> extends Repository<T, ID> {
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