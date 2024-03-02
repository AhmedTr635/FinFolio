package Entity;

import java.security.Timestamp;

public class Message {


        private int message_id;
        private int sender_id;
        private int receiver_id;
        private String message;
        private Timestamp timestamp;

        // Constructor
        public Message(int sender_id, int receiver_id, String message) {
            this.sender_id = sender_id;
            this.receiver_id = receiver_id;
            this.message = message;
        }

        // Getters and setters
        public int getMessage_id() {
            return message_id;
        }

        public void setMessage_id(int message_id) {
            this.message_id = message_id;
        }

        public int getSender_id() {
            return sender_id;
        }

        public void setSender_id(int sender_id) {
            this.sender_id = sender_id;
        }

        public int getReceiver_id() {
            return receiver_id;
        }

        public void setReceiver_id(int receiver_id) {
            this.receiver_id = receiver_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

//        public java.sql.Timestamp getTimestamp() {
//            return timestamp;
//        }
//
//        public void setTimestamp(Timestamp timestamp) {
//            this.timestamp = timestamp;
//        }
}
