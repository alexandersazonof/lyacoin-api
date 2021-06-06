package com.lyacoin.api.utils;

import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.params.MainNetParams;
import org.spongycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;


@Component
public class SignUtils {

    public String signBtc(String msg, String wif) {
        DumpedPrivateKey dpk = DumpedPrivateKey.fromBase58(null, wif);
        ECKey key = dpk.getKey();

        // checking our key object
        NetworkParameters main =  MainNetParams.get();
        String check = key.getPrivateKeyAsWiF(main);
        System.out.println(wif.equals(check));  // true

        // creating Sha object from string
        Sha256Hash hash = Sha256Hash.wrap(msg);

        // creating signature
        ECKey.ECDSASignature sig = key.sign(hash);

        // encoding
        byte[] res = sig.encodeToDER();

        // converting to hex
        return Hex.toHexString(res);
    }
}
