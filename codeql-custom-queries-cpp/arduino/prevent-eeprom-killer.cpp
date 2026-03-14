#include "EEPROM.h"

void setup() {
  // Pas de setup nécessaire, on commence le massacre immédiatement
}

void loop() {
  int adresse = 0;
  byte valeur = 42;

  // ERREUR MAJEURE : Écriture systématique à chaque itération de la boucle
  // La fonction loop() tourne des milliers de fois par seconde.
  EEPROM.write(adresse, valeur);

  // En moins de 2 minutes, cette cellule de mémoire sera physiquement détruite.
  // C'est du gaspillage de matériel pur (Obsolescence programmée par le code).
}