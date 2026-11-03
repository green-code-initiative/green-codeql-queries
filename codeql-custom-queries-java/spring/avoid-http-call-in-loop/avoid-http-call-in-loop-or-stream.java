package test;

class Response {}

class RestTemplate {

    Response getForObject(String url, Class<?> clazz) {
        return null;
    }

    Response postForObject(String url, Object body, Class<?> clazz) {
        return null;
    }
}

class User {
    int getId() {
        return 0;
    }
}

class Service {

    private final RestTemplate restTemplate = new RestTemplate();

    // BAD
    void bad(java.util.List<User> users) {

        for (User user : users) {
            restTemplate.getForObject(
                "/api/users/" + user.getId(),
                Response.class
            );
        }
    }

    // GOOD
    void good(java.util.List<User> users) {

        restTemplate.getForObject(
            "/api/users/batch",
            Response.class
        );
    }

    // BAD
    void badStream(java.util.List<User> users) {

        users.forEach(user ->
            restTemplate.postForObject(
                "/api/users/" + user.getId(),
                user,
                Response.class
            )
        );
    }
}