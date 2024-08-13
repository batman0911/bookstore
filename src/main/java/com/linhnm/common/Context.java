package com.linhnm.common;

import com.linhnm.security.UserInfoDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by linhnm on August 2024
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Context {
    private UserInfoDetails user = new UserInfoDetails();

    public String getUsername() {
        return user.getUsername();
    }
}
