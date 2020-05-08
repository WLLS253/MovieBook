package com.movie.Serivce;


import com.movie.Entity.Tokeners;
import com.movie.Repository.TokenersRepository;
import com.movie.Repository.TokenersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class TokenService  {

    @Autowired
    public TokenersRepository tokenersRepository;

    public  String generateToken(String uuid) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        int uuidValue = Integer.valueOf(uuid);
        int randomDelta = (int)(Math.random() * uuidValue);
        boolean randomIsFormer = (Math.random() * 2 >= 1);
        String key1 = base64Encoder.encode(("" + (uuidValue - randomDelta) * (uuidValue + randomDelta)).getBytes());
        String key2, key3;
        String key4 = String.valueOf(randomDelta);
        if (randomIsFormer) {
            key2 = base64Encoder.encode(String.valueOf(randEven()).getBytes());
            key3 = base64Encoder.encode(("" + (uuidValue + randomDelta)).getBytes());
        } else {
            key2 = base64Encoder.encode(String.valueOf(randOdd()).getBytes());
            key3 = base64Encoder.encode(("" + (uuidValue - randomDelta)).getBytes());
        }
        String noise = base64Iters(String.valueOf((int)(Math.random() * 900000000 + 100000000)), 3);
        int index1 = (int)(Math.random() * noise.length() / 3);
        int index2 = (int)(Math.random() * noise.length() / 3 + noise.length() / 3);
        int index3 = (int)(Math.random() * noise.length() / 3 + 2 * noise.length() / 3);
        String piece1 = noise.substring(0, index1);
        String piece2 = noise.substring(index1 + 1, index2);
        String piece3 = noise.substring(index2 + 1, index3);
        String piece4 = noise.substring(index3 + 1);
        String initResult = (piece1).concat(key1).concat(piece2).concat(key2).concat(piece3).concat(key3).concat(piece4).concat("." + String.valueOf(new Date().getTime()));
        String sindex1 = initResult.indexOf(key1) + "a" + key1.length();
        String sindex2 = initResult.indexOf(key2) + "a" + key2.length();
        String sindex3 = initResult.indexOf(key3) + "a" + key3.length();
        String pindex1 = base64Encoder.encode((sindex1).getBytes());
        String pindex2 = base64Encoder.encode((sindex2).getBytes());
        String pindex3 = base64Encoder.encode((sindex3).getBytes());
        initResult = initResult.concat("." + pindex1 + "." + pindex2 + "." + pindex3).concat("." + key4);
        String midResult = base64Iters(initResult, 2);
        midResult = midResult.substring(midResult.length() / 2).concat(midResult.substring(0, midResult.length() / 2));
        StringBuilder finalResultBuilder = new StringBuilder();
        for (int i = 0; i < midResult.length(); i++) {
            finalResultBuilder.append((char)(midResult.charAt(i) + i * i % 3));
        }
        String result = finalResultBuilder.toString();
        Tokeners tokens=new Tokeners();
        tokens.setUuid(uuid);
        List<Tokeners> tokeners=tokenersRepository.findByUuid(uuid);
        if (tokeners.size()>0) {
            Tokeners target = tokeners.get(0);
            target.setToken(result);
            tokenersRepository.save(target);
        } else {
            tokens.setToken(result);
            tokenersRepository.save(tokens);
        }
        return result;
    }

    public  String validateToken(String token, Long expiredSeconds) {
        Tokeners tokens = new Tokeners();
        tokens.setToken(token);
        List<Tokeners>tokenersList=tokenersRepository.findAllByToken(token);
        if (tokenersList.size()==0) {
            System.out.println("uuid==nill");
            return null;
        }
        try {
            StringBuilder finalResultBuilder = new StringBuilder();
            for (int i = 0; i < token.length(); i++) {
                finalResultBuilder.append((char)(token.charAt(i) - i * i % 3));
            }
            token = finalResultBuilder.toString();
            String base64MidResult = token.substring((token.length() + 1) / 2).concat(token.substring(0, (token.length() + 1) / 2));
            String initResult = reBase64Iters(base64MidResult, 2);
            String[] splittedResults = initResult.split("\\.");
            String keys = splittedResults[0];
            String time = splittedResults[1];

            if (new Date().getTime() - Long.valueOf(time) > expiredSeconds * 1000)
                return null;

            String pindex1 = reBase64Iters(splittedResults[2], 1);
            String pindex2 = reBase64Iters(splittedResults[3], 1);
            String pindex3 = reBase64Iters(splittedResults[4], 1);
            String index1 = pindex1.split("[a-z]")[0];
            String len1 = pindex1.split("[a-z]")[1];
            String index2 = pindex2.split("[a-z]")[0];
            String len2 = pindex2.split("[a-z]")[1];
            String index3 = pindex3.split("[a-z]")[0];
            String len3 = pindex3.split("[a-z]")[1];

            String key1 = keys.substring(Integer.valueOf(index1), Integer.valueOf(index1) + Integer.valueOf(len1));
            String key2 = keys.substring(Integer.valueOf(index2), Integer.valueOf(index2) + Integer.valueOf(len2));
            String key3 = keys.substring(Integer.valueOf(index3), Integer.valueOf(index3) + Integer.valueOf(len3));
            String key4 = splittedResults[5];

            int sum = Integer.valueOf(reBase64Iters(key1, 1));
            int formerFlag = Integer.valueOf(reBase64Iters(key2, 1));
            int multipler = Integer.valueOf(reBase64Iters(key3, 1));
            int delta = Integer.valueOf(key4);

            String uuid;
            if (formerFlag % 2 == 0) uuid = "" + (sum / multipler + delta);
            else uuid = "" + (sum / multipler - delta);

            return uuid;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int randEven() {
        return (int)(Math.random() * 100000) * 2;
    }

    private static int randOdd() {
        return (int)(Math.random() * 100000) * 2 + 1;
    }

    private static String base64Iters(String origin, int iters) {
        Base64.Encoder base64Encoder = Base64.getEncoder();
        String o = origin;
        for (int i = 0; i < iters; i++)
            o = new String(base64Encoder.encode(o.getBytes()), Charset.forName("UTF-8"));
        return o;
    }


    private static String reBase64Iters(String target, int iters) {
        Base64.Decoder base64Decoder = Base64.getDecoder();
        String t = target;
        for (int i = 0; i < iters; i++) {
            t = new String(base64Decoder.decode(t), Charset.forName("UTF-8"));
        }
        return t;
    }
}
