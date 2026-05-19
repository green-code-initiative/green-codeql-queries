class Repo {
    void saveAndFlush(Object o) {}
}

class NoncompliantSaveAndFlushInLoop {

    void should_flag_saveAndFlush_in_for_loop() {
        Repo repo = new Repo();

        for (int i = 0; i < 10; i++) {
            repo.saveAndFlush(new Object()); // SHOULD BE FLAGGED
        }
    }
    void should_flag_saveAndFlush_in_while_loop() {
        Repo repo = new Repo();
        int i = 0;
        while (i < 10) {
            repo.saveAndFlush(new Object()); // SHOULD BE FLAGGED
            i++;
        }
    }

    void should_flag_saveAndFlush_in_do_while_loop() {
        Repo repo = new Repo();
        int i = 0;
        do {
            repo.saveAndFlush(new Object()); // SHOULD BE FLAGGED
            i++;
        } while (i < 10);
    }

    void should_flag_saveAndFlush_in_foreach_loop() {
        Repo repo = new Repo();
        for (Object o : new Object[10]) {
            repo.saveAndFlush(o); // SHOULD BE FLAGGED
        }
    }

}

class compliantSaveAndFlushOutsideLoop {

    void should_not_flag_saveAndFlush_outside_loop() {
        Repo repo = new Repo();
        repo.saveAndFlush(new Object()); // SHOULD NOT BE FLAGGED
    }
}