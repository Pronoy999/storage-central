package com.storage.central.api.test;

import com.storage.central.api.entity.Credential;
import com.storage.central.api.entity.User;
import com.storage.central.common.model.requests.CreateUserRequest;
import com.storage.central.common.model.requests.LoginUserRequest;

import java.util.ArrayList;
import java.util.List;

public class TestDataGenerator {
    public static User generateUser() {
        User user = new User();
        user.setFirstName("Pronoy");
        user.setLastName("Silva");
        return user;
    }

    public static Credential generateCredential() {
        Credential credential = new Credential();
        credential.setUserGuid("dummyGuid");
        credential.setEmailId("email@email.com");
        credential.setPassword("password");
        credential.setActive(true);
        return credential;
    }

    public static CreateUserRequest generateCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmailId("email@email.com");
        createUserRequest.setPassword("password");
        createUserRequest.setFirstName("Pronoy");
        createUserRequest.setLastName("Silva");
        return createUserRequest;
    }

    public static LoginUserRequest generateLoginUserRequest() {
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmailId("email@email.com");
        loginUserRequest.setPassword("password");
        return loginUserRequest;
    }

    public static List<Credential> generateCredentialList() {
        List<Credential> credentialList = new ArrayList<Credential>();
        credentialList.add(generateCredential());
        return credentialList;
    }
}

