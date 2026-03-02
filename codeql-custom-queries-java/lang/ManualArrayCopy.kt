class ManualArrayCopy {

    // Devrait déclencher 4 à 5 alertes selon la précision de la requête
    fun testManualCopies() {
        val src = intArrayOf(1, 2, 3, 4, 5)
        val dest = IntArray(5)

        // 1. Boucle for classique (indexée)
        for (i in src.indices) {
            dest[i] = src[i]
        }

        // 2. Boucle while
        var j = 0
        while (j < src.size) {
            dest[j] = src[j]
            j++
        }

        // 3. Boucle for-each (Enhanced for)
        var index = 0
        for (value in src) {
            dest[index++] = value
        }

        // 4. Boucle imbriquée dans une condition
        if (dest.isNotEmpty()) {
            for (k in src.indices) {
                dest[k] = src[k]
            }
        }
    }

    // Ne devrait pas déclencher d'alerte
    fun testValidCases() {
        val src = intArrayOf(1, 2, 3, 4, 5)
        val dest = IntArray(5)

        // La manière correcte en Kotlin (équivalent System.arraycopy)
        src.copyInto(dest)

        // Manipulation de données (pas une simple copie)
        for (i in src.indices) {
            src[i] = src[i] * 2
        }

        // Initialisation (pas une copie d'un autre tableau)
        for (i in dest.indices) {
            dest[i] = 0
        }
    }
}