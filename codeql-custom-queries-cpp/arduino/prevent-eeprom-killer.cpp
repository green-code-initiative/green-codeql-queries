#include "EEPROM.h"

void setup() {
  // Pas de setup nécessaire, on commence le massacre immédiatement
}

// ============================================================
// 🚫 Noncompliant - systematic write in loop()
// ============================================================
class NoncompliantSystematicWrite {
    void loop() {
        int addr = 10;
        unsigned char val = 255;
        // The loop() function runs thousands of times per second.
        // This will destroy the EEPROM cell physically in a few minutes.
        EEPROM.write(addr, val); // $ Alert
    }
};

// ============================================================
// 🚫 Noncompliant - write inside a for loop
// ============================================================
class NoncompliantForLoop {
    void clearMemory() {
        for (int i = 0; i < 1024; i++) {
            // Writing in a tight loop is dangerous if called frequently
            EEPROM.write(i, 0); // $ Alert
        }
    }
};

// ============================================================
// 🚫 Noncompliant - write inside a while loop
// ============================================================
class NoncompliantWhileLoop {
    void fill(unsigned char data) {
        int i = 0;
        while (i < 10) {
            EEPROM.write(i, data); // $ Alert
            i++;
        }
    }
};

// ============================================================
// 🚫 Noncompliant - nested calls leading to systematic write
// ============================================================
class NoncompliantNestedCall {
    void internalWrite() {
        EEPROM.write(0, 1); // $ Alert
    }

    void loop() {
        internalWrite();
    }
};

// ============================================================
// ✅ Compliant - using update() instead of write()
// ============================================================
class CompliantUseUpdate {
    void loop() {
        int addr = 10;
        unsigned char val = 255;
        // update() reads the value first and only writes if it's different.
        // This is the recommended way to save battery and hardware life.
        EEPROM.update(addr, val); // OK
    }
};

// ============================================================
// ✅ Compliant - write called in setup() (one-time execution)
// ============================================================
class CompliantOneTimeWrite {
    void setup() {
        // setup() only runs once at startup. 
        // Writing here is generally safe for the hardware lifetime.
        EEPROM.write(0, 42); // OK
    }
};

// ============================================================
// ✅ Compliant - write protected by a state change logic
// ============================================================
class CompliantConditionalWrite {
    unsigned char lastState = 0;

    void loop() {
        unsigned char currentState = 1; // Simulated sensor read
        
        if (currentState != lastState) {
            EEPROM.write(0, currentState); // OK - only writes when data changes
            lastState = currentState;
        }
    }
};

// ============================================================
// ✅ Compliant - EEPROM read operation
// ============================================================
class CompliantRead {
    void loop() {
        // Reading does not cause physical wear to the memory cells.
        unsigned char val = EEPROM.read(0); // OK
    }
};