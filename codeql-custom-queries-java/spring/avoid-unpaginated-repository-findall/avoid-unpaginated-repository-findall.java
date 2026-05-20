import java.util.List;

interface EmployeeRepository {
    List<Object> findAll();
    Object findAll(Object pageable);
    Object findById(int id);
}

// ============================================================
// Noncompliant cases
// ============================================================

class NoncompliantDirectCall {
    EmployeeRepository employeeRepository;

    void loadAll() {
        List<Object> all = employeeRepository.findAll(); // $ Alert
    }
}

class NoncompliantMultipleCalls {
    EmployeeRepository employeeRepository;

    void process() {
        List<Object> employees = employeeRepository.findAll(); // $ Alert
    }
}

// ============================================================
// Compliant cases
// ============================================================

class CompliantPaginated {
    EmployeeRepository employeeRepository;

    void loadPage(Object pageRequest) {
        Object page = employeeRepository.findAll(pageRequest); // OK - paginated
    }
}

class CompliantFindById {
    EmployeeRepository employeeRepository;

    void loadOne() {
        Object emp = employeeRepository.findById(1); // OK - single record
    }
}
