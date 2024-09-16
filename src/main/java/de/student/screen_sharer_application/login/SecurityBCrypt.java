package de.student.screen_sharer_application.login;

import de.student.screen_sharer_application.services.SecurityData;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

public class SecurityBCrypt{

    private final List<String> data = List.of("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r"
            ,"s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I"
            ,"J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");

    private String private_key = "";

    public SecurityBCrypt() {
        auto_generate_private_key();
    }

    public SecurityData encrypt(UUID id){
        StringBuilder encryptedData = new StringBuilder();
        String s = id.toString();
        for(int i = 0; i < s.length(); i++){
            encryptedData.append((char) (s.charAt(i) + private_key.charAt(i)));
        }
        return new SecurityData(encryptedData.toString(),private_key);
    }

    public boolean decrypt(String private_key, UUID userID, String cookie_Value){
        this.private_key = private_key;
        String data = encrypt(userID).data();
        return data.equals(cookie_Value);
    }

    /*private List<SecurityData> pos_Bits(byte[] a, byte[] b){
        if(a.length != b.length) return null;
        List<Integer> power_of_two = List.of(1,2,4,8,16,32,64,128);
        boolean id_Zero = false;
        boolean private_key_Zero = false;
        List<Byte> bytes = new ArrayList<>();
        for(int i = 0; i < a.length; i++){
            byte fa = a[i], fb = b[i];
            byte ex = 0;
            for(Integer k : power_of_two){
                if((fa & k) == 0){
                    id_Zero = true;
                }
                if((fb & k) == 0){
                    private_key_Zero = true;
                }
                if(id_Zero && private_key_Zero){
                    if(bytes.isEmpty()){
                        bytes.add((byte) (ex | k));
                        continue;
                    }
                    int s = bytes.size();
                    for(int l = 0; l < s; l++){
                        Byte currentByte = bytes.get(l);
                        ex = (byte) (currentByte | k);
                        bytes.add(ex);
                    }
                }
                else if(!private_key_Zero && !id_Zero){
                    if(bytes.isEmpty()){
                        bytes.add((byte) (ex | k));
                        continue;
                    }
                    for(Byte bi : bytes){
                        bytes.add((byte) (bi | k));
                    }
                }
                else{
                    if(bytes.isEmpty()){
                        bytes.add(ex);
                        continue;
                    }
                }
                id_Zero = false;
                private_key_Zero = false;
            }
        }
    }
     */

    private void auto_generate_private_key(){
        Random random = new Random();
        IntStream randomIndexes = random.ints(36, 0, data.size() - 1);
        randomIndexes.forEach(x -> private_key+=data.get(x));
    }
}
