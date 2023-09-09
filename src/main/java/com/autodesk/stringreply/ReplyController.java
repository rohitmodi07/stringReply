package com.autodesk.stringreply;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.security.MessageDigest;

@RestController
public class ReplyController {

    @GetMapping("/reply")
    public ReplyMessage replying() {
        return new ReplyMessage("Message is empty");
    }

    @GetMapping("/reply/{message}")
    public ReplyMessage replying(@PathVariable String message) {
        return new ReplyMessage(message);
    }

    @GetMapping("/v2/reply/{message}")
    public ResponseEntity<ReplyMessage> replyingV2(@PathVariable String message){
          if(message == null || message.isEmpty()){
              return new ResponseEntity<ReplyMessage>(
                      new ReplyMessage("Invalid input"), HttpStatus.BAD_REQUEST);
          }
          String[] messageArr = message.split("-");
          char[] keyArr = messageArr[0].toCharArray();
          String messageRes = "";
          HttpStatus status = null;

          for(int i=0; i<keyArr.length; i++){
              messageRes = messageRes.isEmpty() ? messageArr[1] : messageRes;
              if(keyArr[i] == '1'){
                  StringBuilder sb = new StringBuilder();
                  sb.append(messageRes).reverse();
                  messageRes = sb.toString();
                  status = HttpStatus.OK;
              }else if(keyArr[i] == '2'){
                  messageRes = convertMessageToMD5Format(messageRes);
                  status = HttpStatus.OK;
              }else {
                  messageRes = "Invalid input";
                  status = HttpStatus.BAD_REQUEST;
              }
          }
          return new ResponseEntity<ReplyMessage>(
                new ReplyMessage(messageRes), status);
    }

    public String convertMessageToMD5Format(String message){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            byte[] messageDigestBytes = digest.digest(message.getBytes());

            BigInteger no = new BigInteger(1, messageDigestBytes);

            // Convert message digest into hex value
            String hashtextInHex = no.toString(16);
            while (hashtextInHex.length() < 32) {
                hashtextInHex = "0" + hashtextInHex;
            }
            return hashtextInHex;
        }
        catch (Exception e){
            throw new RuntimeException("exception occurred while converting string to hash :: "+e);
        }
    }

}