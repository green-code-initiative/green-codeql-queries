import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

class Employee {
    int id;
}

interface EmployeeRepository {
    Optional<Employee> findById(int id);
    List<Employee> findAllById(Iterable<Integer> ids);
}

// ============================================================
// Noncompliant cases
// ============================================================

class NoncompliantForLoop {
    EmployeeRepository repo;
    void test() {
        for (int i = 0; i < 5; i++) {
            repo.findById(i); // $ Alert
        }
    }
}

class NoncompliantEnhancedForLoop {
    EmployeeRepository repo;
    void test() {
        List<Integer> ids = Arrays.asList(1, 2, 3);
        for (Integer id : ids) {
            repo.findById(id); // $ Alert
        }
    }
}

class NoncompliantWhileLoop {
    EmployeeRepository repo;
    void test() {
        int i = 0;
        while (i < 5) {
            repo.findById(i); // $ Alert
            i++;
        }
    }
}

class NoncompliantStreamForEach {
    EmployeeRepository repo;
    void test() {
        Stream.of(1, 2, 3).forEach(id -> repo.findById(id)); // $ Alert
    }
}

class NoncompliantStreamMap {
    EmployeeRepository repo;
    void test() {
        Stream.of(1, 2, 3).map(id -> repo.findById(id)); // $ Alert
    }
}

class NoncompliantStreamPeek {
    EmployeeRepository repo;
    void test() {
        Stream.of(1, 2, 3).peek(id -> repo.findById(id)); // $ Alert
    }
}

class NoncompliantStreamForEachOrdered {
    EmployeeRepository repo;
    void test() {
        Stream.of(1, 2, 3).forEachOrdered(id -> repo.findById(id)); // $ Alert
    }
}

// ============================================================
// Compliant cases
// ============================================================

class CompliantBatchCall {
    EmployeeRepository repo;
    void test() {
        List<Integer> ids = Arrays.asList(1, 2, 3, 4, 5);
        repo.findAllById(ids); // OK
    }
}

class CompliantSingleCall {
    EmployeeRepository repo;
    void test() {
        repo.findById(1); // OK
    }
}
