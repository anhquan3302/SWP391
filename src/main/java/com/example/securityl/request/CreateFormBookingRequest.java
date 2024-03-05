package com.example.securityl.request;

import java.time.LocalDateTime;

public class CreateFormBookingRequest {

        private LocalDateTime dateTime;
        private String designDescription;
//        private int designerId;
        private int userId;


        private String meetingTime;


        private String meetingDate;

        public LocalDateTime getDateTime() {
                return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
                this.dateTime = dateTime;
        }

        public String getDesignDescription() {
                return designDescription;
        }

        public void setDesignDescription(String designDescription) {
                this.designDescription = designDescription;
        }

//        public int getDesignerId() {
//                return designerId;
//        }
//
//        public void setDesignerId(int designerId) {
//                this.designerId = designerId;
//        }

        public int getUserId() {
                return userId;
        }

        public void setUserId(int userId) {
                this.userId = userId;
        }

        public String getMeetingTime() {
                return meetingTime;
        }

        public void setMeetingTime(String meetingTime) {
                this.meetingTime = meetingTime;
        }

        public String getMeetingDate() {
                return meetingDate;
        }

        public void setMeetingDate(String meetingDate) {
                this.meetingDate = meetingDate;
        }
}
