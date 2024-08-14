package com.linhnm.common;

import com.linhnm.security.UserInfoDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by linhnm on August 2024
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Context {
    private UserDetails user;

    public String getUsername() {
        return user.getUsername();
    }
}
