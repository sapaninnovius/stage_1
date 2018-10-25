package com.stage1.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseRegistration {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;
    @SerializedName("message")
    @Expose
    private String message;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("email")
        @Expose
        private List<String> email = null;
        @SerializedName("name")
        @Expose
        private List<String> name = null;
        @SerializedName("contact_number")
        @Expose
        private List<String> contactNumber = null;
        @SerializedName("role_id")
        @Expose
        private List<String> roleId = null;
        @SerializedName("password")
        @Expose
        private List<String> password = null;

        public List<String> getEmail() {
            return email;
        }

        public void setEmail(List<String> email) {
            this.email = email;
        }

        public List<String> getName() {
            return name;
        }

        public void setName(List<String> name) {
            this.name = name;
        }

        public List<String> getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(List<String> contactNumber) {
            this.contactNumber = contactNumber;
        }

        public List<String> getRoleId() {
            return roleId;
        }

        public void setRoleId(List<String> roleId) {
            this.roleId = roleId;
        }

        public List<String> getPassword() {
            return password;
        }

        public void setPassword(List<String> password) {
            this.password = password;
        }

    }
}
