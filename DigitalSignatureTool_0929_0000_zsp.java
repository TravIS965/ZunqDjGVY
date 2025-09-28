// 代码生成时间: 2025-09-29 00:00:45
public class DigitalSignatureTool {

    /**
     * Signs the given message with the provided private key.
     *
     * @param message The message to be signed.
     * @param privateKey The private key used for signing.
     * @return The signed message in Base64 encoding.
     * @throws Exception If any error occurs during the signing process.
     */
    public String signMessage(String message, PrivateKey privateKey) throws Exception {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(message.getBytes("UTF-8"));
            byte[] signedMessage = signature.sign();
            return Base64.getEncoder().encodeToString(signedMessage);
        } catch (Exception e) {
            throw new Exception("Error signing message", e);
        }
    }

    /**
     * Verifies the signature of the given message with the provided public key.
     *
     * @param message The original message.
     * @param signature The signature to be verified.
     * @param publicKey The public key used for verification.
     * @return True if the signature is valid, false otherwise.
     * @throws Exception If any error occurs during the verification process.
     */
    public boolean verifySignature(String message, String signature, PublicKey publicKey) throws Exception {
        try {
            Signature signatureVerification = Signature.getInstance("SHA256withRSA");
            signatureVerification.initVerify(publicKey);
            signatureVerification.update(message.getBytes("UTF-8"));
            return signatureVerification.verify(Base64.getDecoder().decode(signature));
        } catch (Exception e) {
            throw new Exception("Error verifying signature", e);
        }
    }

    // Additional methods and logic for generating keys, managing certificates, etc., can be added here.
}
