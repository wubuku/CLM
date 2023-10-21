import java.util.Base64;
import java.util.UUID;
import java.math.BigInteger;

public class UUIDTest {

    public static void main(String[] args) {
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();
        // Print the UUID
        System.out.println("UUID: " + uuid);
        // Remove the "-" symbols from the UUID
        String hex = uuid.toString().replace("-", "");
        // Split the hex string into two substrings
        String hex1 = hex.substring(0, 16);
        String hex2 = hex.substring(16, 32);
        // Convert the substrings into long values
        BigInteger num1 = new BigInteger(hex1, 16);
        BigInteger num2 = new BigInteger(hex2, 16);
        // XOR the two values
        BigInteger xor = num1.xor(num2);
//        // Convert the result into a hex string
//        String compressedHex = xor.toString(16);
//        //ce37e5be1f46ae36
//        // Pad with zeros if necessary
//        compressedHex = String.format("%020x", xor);
//        // Print the compressed hex string
//        System.out.println("Compressed Hex: " + compressedHex);
//        //0000020a7f1cc6ab5c77
        // Convert the result into a Base64 encoded string
        byte[] bytes = new byte[9];
        for (int i = 0; i < 8; i++) {
            // >>> (8 * (7 - i)));
            bytes[i] = (byte) (xor.shiftRight(8 * (7 - i)).intValue() & 0xFF);
        }
        bytes[8] = (byte) (Integer.parseInt(hex1.substring(0, 2), 16) & 0xFF);
        String compressedBase64 = Base64.getEncoder().encodeToString(bytes);
        // Print the compressed Base64 string
        System.out.println("Compressed Base64: " + compressedBase64);
    }
}