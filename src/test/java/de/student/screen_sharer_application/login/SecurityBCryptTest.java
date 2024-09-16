package de.student.screen_sharer_application.login;

import de.student.screen_sharer_application.services.SecurityData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SecurityBCryptTest {

    @Test
    @DisplayName("Encrypts a given UUID of a user and returns new one and a private key")
    public void test_1(){
        SecurityBCrypt securityBCrypt = new SecurityBCrypt();
        UUID uuid = UUID.randomUUID();
        SecurityData encrypt = securityBCrypt.encrypt(uuid);

        assertThat(encrypt.key()).hasSize(36);
        assertThat(encrypt.data()).hasSizeLessThanOrEqualTo(36);
    }

    @Test
    @DisplayName("Decrypts encrpyted cookie Value of client. Encryption is same as value pass")
    public void test_2(){
        SecurityBCrypt securityBCrypt = new SecurityBCrypt();
        UUID uuid = UUID.randomUUID();
        SecurityData encrypt = securityBCrypt.encrypt(uuid);

        boolean decrypt = securityBCrypt.decrypt(encrypt.key(), uuid, encrypt.data());

        assertThat(decrypt).isTrue();

    }

    @Test
    @DisplayName("Decrypts encrpyted cookie Value of client. Encryption is not same as value don't pass - private key has changed")
    public void test_3(){
        SecurityBCrypt securityBCrypt = new SecurityBCrypt();
        UUID uuid = UUID.randomUUID();
        SecurityData encrypt = securityBCrypt.encrypt(uuid);

        SecurityBCrypt securityBCrypt1 = new SecurityBCrypt();
        boolean decrypt = securityBCrypt1.decrypt("abce1934deZZKdabce1934deZZKdabce1934", uuid, encrypt.data());

        assertThat(decrypt).isFalse();
    }

    @Test
    @DisplayName("Decrypts encrpyted cookie value of client. Encryption is not same as value don't pass - cookie value has changed")
    public void test_4(){
        SecurityBCrypt securityBCrypt = new SecurityBCrypt();
        UUID uuid = UUID.randomUUID();
        SecurityData encrypt = securityBCrypt.encrypt(uuid);

        uuid = UUID.randomUUID();
        SecurityBCrypt securityBCrypt1 = new SecurityBCrypt();
        boolean decrypt = securityBCrypt1.decrypt(encrypt.key(), uuid, encrypt.data());

        assertThat(decrypt).isFalse();
    }
}
