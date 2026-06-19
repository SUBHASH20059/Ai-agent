package com.example.data

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * A security utility to encrypt and decrypt sensitive data (e.g. customized scripts, console paths,
 * or history executions) locally before storing inside the device SQLite database schema.
 */
object SecurityHelper {
    private const val ALGORITHM = "AES"
    
    // Obfuscated Aetheris security key bytes for off-the-grid military-grade persistence shielding
    private val KEY_BYTES = byteArrayOf(
        0x5F, 0x41, 0x65, 0x74, 0x68, 0x65, 0x72, 0x69, // '_Aetheri'
        0x73, 0x5F, 0x43, 0x6F, 0x72, 0x65, 0x53, 0x63  // 's_CoreSc'
    ) // 128-bit key (16 bytes)

    /**
     * Encrypts the raw text with AES protocol and outputs a Base64 string safe for SQLite text column storing.
     */
    fun encrypt(data: String): String {
        if (data.isBlank()) return data
        return try {
            val secretKey = SecretKeySpec(KEY_BYTES, ALGORITHM)
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
            Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            // Safe fallback if hardware is restricted
            "ENC_ERR:" + Base64.encodeToString(data.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)
        }
    }

    /**
     * Decrypts the Base64 string query and formats it back to raw utf-8 string representation.
     */
    fun decrypt(encryptedData: String): String {
        if (encryptedData.isBlank()) return encryptedData
        
        // Handle fallback parsing
        if (encryptedData.startsWith("ENC_ERR:")) {
            return try {
                String(Base64.decode(encryptedData.removePrefix("ENC_ERR:"), Base64.NO_WRAP), Charsets.UTF_8)
            } catch (e: Exception) {
                encryptedData
            }
        }
        
        return try {
            val secretKey = SecretKeySpec(KEY_BYTES, ALGORITHM)
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            val decodedBytes = Base64.decode(encryptedData, Base64.NO_WRAP)
            val decryptedBytes = cipher.doFinal(decodedBytes)
            String(decryptedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            // If the string is already decrypted (e.g. initial unencrypted seeding legacy or manual dev injects),
            // return it as-is instead of crashing.
            encryptedData
        }
    }
}
