package com.ecom.notificationservice.exception.custom;

/** * Thrown when requested logs for a user/ID do not exist. 
 */
public class NotificationNotFoundException extends NotificationException {
    public NotificationNotFoundException(String message) {
        super(message);
    }
}
