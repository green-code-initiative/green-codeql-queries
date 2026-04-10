#ifndef EEPROM_H
#define EEPROM_H

/**
 * @brief Ceci est un stubs contenant seuluemnt les fonctions que l'on souhaite check
 * 
 */

// Utilisation de unsigned char pour correspondre au type 'byte' d'Arduino
typedef unsigned char byte;

class EEPROMClass {
public:
    // Ajout des méthodes manquantes signalées par l'erreur
    void write(int address, byte value) {}
    void update(int address, byte value) {}
    byte read(int address) { return 0; }
};

// Instance globale utilisée par les tests
extern EEPROMClass EEPROM;

#endif