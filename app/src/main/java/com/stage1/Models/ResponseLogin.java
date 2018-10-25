package com.stage1.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ResponseLogin {
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

        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("role_id")
        @Expose
        private Integer roleId;
        @SerializedName("user_detail")
        @Expose
        private UserDetail userDetail;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        public UserDetail getUserDetail() {
            return userDetail;
        }

        public void setUserDetail(UserDetail userDetail) {
            this.userDetail = userDetail;
        }
        public class UserDetail {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("contact_number")
            @Expose
            private String contactNumber;
            @SerializedName("password")
            @Expose
            private String password;
            @SerializedName("profile_pic")
            @Expose
            private String profilePic;
            @SerializedName("device_id")
            @Expose
            private String deviceId;
            @SerializedName("role_id")
            @Expose
            private Integer roleId;
            @SerializedName("bar_id")
            @Expose
            private Integer barId;
            @SerializedName("lat")
            @Expose
            private String lat;
            @SerializedName("long")
            @Expose
            private String _long;
            @SerializedName("current_status")
            @Expose
            private Integer currentStatus;
            @SerializedName("status")
            @Expose
            private Integer status;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getContactNumber() {
                return contactNumber;
            }

            public void setContactNumber(String contactNumber) {
                this.contactNumber = contactNumber;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getProfilePic() {
                return profilePic;
            }

            public void setProfilePic(String profilePic) {
                this.profilePic = profilePic;
            }

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            public Integer getRoleId() {
                return roleId;
            }

            public void setRoleId(Integer roleId) {
                this.roleId = roleId;
            }

            public Integer getBarId() {
                return barId;
            }

            public void setBarId(Integer barId) {
                this.barId = barId;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLong() {
                return _long;
            }

            public void setLong(String _long) {
                this._long = _long;
            }

            public Integer getCurrentStatus() {
                return currentStatus;
            }

            public void setCurrentStatus(Integer currentStatus) {
                this.currentStatus = currentStatus;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

        }
    }
}
