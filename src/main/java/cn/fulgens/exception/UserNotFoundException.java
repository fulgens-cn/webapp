package cn.fulgens.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFoundException extends RuntimeException {

    private String uid;

    public UserNotFoundException(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
}
