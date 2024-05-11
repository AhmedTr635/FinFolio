package com.example.finfolio.UsrController;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioAPI {

    public TwilioAPI()
    {
        Twilio.init(
                "ACfc574ca79d4c6fc281ca14001673d189",
                "123ed1fb12d13fb8f8567e5dacd39959");
    }

    public void sendSMS(String number,String message)
    {
        Message msg = Message.creator(
                        new PhoneNumber(number),
                        new PhoneNumber("+16509107225"),
                        message)
                .create();
    }
}
