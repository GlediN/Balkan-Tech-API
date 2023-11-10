package com.sda.wrapper;

public class UserWrapper {

    /*UserWrapper user = new UserWrapper(10,"abc","123123","asdasd@gmail.com","true");*/
    private Integer id;
    private String contactNumber;
    private String email;


    public UserWrapper(Integer id, String contactNumber, String email) {
        this.id = id;
        this.contactNumber = contactNumber;
        this.email = email;

    }
}
