package com.mogsev.androidplugins.model;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Eugene Sikaylo (mogsev@gmail.com)
 */
public class Algorithm {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            Type.SCRYPT,
            Type.SHA256,
            Type.SCRYPT_NF,
            Type.X11,
            Type.X13,
            Type.KECCAK,
            Type.X15,
            Type.NIST5,
            Type.NEO_SCRYPT,
            Type.LYRA_2RE,
            Type.WHIRPOOLX,
            Type.QUBIT,
            Type.QUARK,
            Type.AXIOM,
            Type.LYRA2RE_V2,
            Type.SCRYPT_JANE_NF16,
            Type.BLAKE_256_R8,
            Type.BLAKE_256_R14,
            Type.BLAKE_256_R8_VNL,
            Type.HODL,
            Type.DAGGER_HASHIMOTO,
            Type.DECRED,
            Type.CRYPTO_NIGHT,
            Type.LBRY,
            Type.EQUIHASH,
            Type.PASCAL,
            Type.X11_GOST
    })
    public @interface Type {
        public static final int SCRYPT = 0;
        public static final int SHA256 = 1;
        public static final int SCRYPT_NF = 2;
        public static final int X11 = 3;
        public static final int X13 = 4;
        public static final int KECCAK = 5;
        public static final int X15 = 6;
        public static final int NIST5 = 7;
        public static final int NEO_SCRYPT = 8;
        public static final int LYRA_2RE = 9;
        public static final int WHIRPOOLX = 10;
        public static final int QUBIT = 11;
        public static final int QUARK = 12;
        public static final int AXIOM = 13;
        public static final int LYRA2RE_V2 = 14;
        public static final int SCRYPT_JANE_NF16 = 15;
        public static final int BLAKE_256_R8 = 16;
        public static final int BLAKE_256_R14 = 17;
        public static final int BLAKE_256_R8_VNL = 18;
        public static final int HODL = 19;
        public static final int DAGGER_HASHIMOTO = 20;
        public static final int DECRED = 21;
        public static final int CRYPTO_NIGHT = 22;
        public static final int LBRY = 23;
        public static final int EQUIHASH = 24;
        public static final int PASCAL = 25;
        public static final int X11_GOST = 26;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            Name.SCRYPT,
            Name.SHA256,
            Name.SCRYPT_NF,
            Name.X11,
            Name.X13,
            Name.KECCAK,
            Name.X15,
            Name.NIST5,
            Name.NEO_SCRYPT,
            Name.LYRA_2RE,
            Name.WHIRPOOLX,
            Name.QUBIT,
            Name.QUARK,
            Name.AXIOM,
            Name.LYRA2RE_V2,
            Name.SCRYPT_JANE_NF16,
            Name.BLAKE_256_R8,
            Name.BLAKE_256_R14,
            Name.BLAKE_256_R8_VNL,
            Name.HODL,
            Name.DAGGER_HASHIMOTO,
            Name.DECRED,
            Name.CRYPTO_NIGHT,
            Name.LBRY,
            Name.EQUIHASH,
            Name.PASCAL,
            Name.X11_GOST,
            Name.NO_NAME
    })
    public @interface Name {
        public static final String SCRYPT = "Scrypt";
        public static final String SHA256 = "SHA256";
        public static final String SCRYPT_NF = "ScryptNf";
        public static final String X11 = "X11";
        public static final String X13 = "X13";
        public static final String KECCAK = "Keccak";
        public static final String X15 = "X15";
        public static final String NIST5 = "Nist5";
        public static final String NEO_SCRYPT = "NeoScrypt";
        public static final String LYRA_2RE = "Lyra2RE";
        public static final String WHIRPOOLX = "WhirlpoolX";
        public static final String QUBIT = "Qubit";
        public static final String QUARK = "Quark";
        public static final String AXIOM = "Axiom";
        public static final String LYRA2RE_V2 = "Lyra2REv2";
        public static final String SCRYPT_JANE_NF16 = "ScryptJaneNf16";
        public static final String BLAKE_256_R8 = "Blake256r8";
        public static final String BLAKE_256_R14 = "Blake256r14";
        public static final String BLAKE_256_R8_VNL = "Blake256r8vnl";
        public static final String HODL = "Hodl";
        public static final String DAGGER_HASHIMOTO = "DaggerHashimoto";
        public static final String DECRED = "Decred";
        public static final String CRYPTO_NIGHT = "CryptoNight";
        public static final String LBRY = "Lbry";
        public static final String EQUIHASH = "Equihash";
        public static final String PASCAL = "Pascal";
        public static final String X11_GOST = "X11Gost";
        public static final String NO_NAME = "No name";
    }

    @Algorithm.Name
    public static String getName(@Type int type) {
        switch (type) {
            case Type.SCRYPT:
                return Name.SCRYPT;
            case Type.SHA256:
                return Name.SHA256;
            case Type.SCRYPT_NF:
                return Name.SCRYPT_NF;
            case Type.X11:
                return Name.X11;
            case Type.X13:
                return Name.X13;
            case Type.KECCAK:
                return Name.KECCAK;
            case Type.X15:
                return Name.X15;
            case Type.NIST5:
                return Name.NIST5;
            case Type.NEO_SCRYPT:
                return Name.NEO_SCRYPT;
            case Type.LYRA_2RE:
                return Name.LYRA_2RE;
            case Type.WHIRPOOLX:
                return Name.WHIRPOOLX;
            case Type.QUBIT:
                return Name.QUBIT;
            case Type.QUARK:
                return Name.QUARK;
            case Type.AXIOM:
                return Name.AXIOM;
            case Type.LYRA2RE_V2:
                return Name.LYRA2RE_V2;
            case Type.SCRYPT_JANE_NF16:
                return Name.SCRYPT_JANE_NF16;
            case Type.BLAKE_256_R8:
                return Name.BLAKE_256_R8;
            case Type.BLAKE_256_R14:
                return Name.BLAKE_256_R14;
            case Type.BLAKE_256_R8_VNL:
                return Name.BLAKE_256_R8_VNL;
            case Type.HODL:
                return Name.HODL;
            case Type.DAGGER_HASHIMOTO:
                return Name.DAGGER_HASHIMOTO;
            case Type.DECRED:
                return Name.DECRED;
            case Type.CRYPTO_NIGHT:
                return Name.CRYPTO_NIGHT;
            case Type.LBRY:
                return Name.LBRY;
            case Type.EQUIHASH:
                return Name.EQUIHASH;
            case Type.PASCAL:
                return Name.PASCAL;
            case Type.X11_GOST:
                return Name.X11_GOST;
            default:
                return Name.NO_NAME;
        }
    }
}
