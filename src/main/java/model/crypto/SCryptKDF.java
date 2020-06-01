package model.crypto;

import java.security.GeneralSecurityException;

import com.google.common.primitives.Bytes;
import com.lambdaworks.crypto.SCrypt;
import com.lambdaworks.crypto.SCryptUtil;

public class SCryptKDF extends KDFAdvanced {

    private static final int ROUNDS = 10;
    private static final int DEFAULT_MEMORY = 32768;
    private static final int DEFAULT_PARALLELISM = 2;
    private static final int BLOCK_SIZE = 8;
    private int memory = DEFAULT_MEMORY;
    private int parallelism = DEFAULT_PARALLELISM;

    @Override
    public final byte[] generateKey(final byte[] password, final byte[] salt, final int rounds) {
        byte[] composite = Bytes.concat(password, salt);
        String hash = SCryptUtil.scrypt(new String(composite), 10, this.memory, this.parallelism);
        SCrypt script = new SCrypt();
        try {
            script.scrypt(password, salt, rounds, BLOCK_SIZE, this.parallelism, 32);
        } catch (GeneralSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hash.getBytes();
    }

    @Override
    public final int getDefaultRounds() {
        return SCryptKDF.ROUNDS; 
    }

    @Override
    public final boolean isTweakable() {
        return super.isTweakble();
    }

    @Override
    public final void setMemory(final int memory) {
        try {
            this.memory = checkMemory(memory);
        } catch (Exception e) {
        }
    }

    @Override
    public final void setParallelism(final int parallelism) {
        try {
            this.parallelism = checkParallelism(parallelism);
        } catch (Exception e) {
        }
    }

    @Override
    public void setKeySize(int keySize) {
        // TODO Auto-generated method stub
        
    }

}
