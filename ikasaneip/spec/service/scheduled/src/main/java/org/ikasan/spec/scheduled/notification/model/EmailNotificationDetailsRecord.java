package org.ikasan.spec.scheduled.notification.model;

import java.io.Serializable;

public interface EmailNotificationDetailsRecord extends Serializable {

    String getId();

    void setId(String id);

    String getJobName();

    void setJobName(String jobName);

    String getContextName();

    void setContextName(String contextName);

    String getMonitorType();

    void setMonitorType(String monitorType);

    EmailNotificationDetails getEmailNotificationDetails();

    void setEmailNotificationDetails(EmailNotificationDetails emailNotificationDetails);

    long getTimestamp();

    void setTimestamp(long timestamp);

    long getModifiedTimestamp();

    void setModifiedTimestamp(long modifiedTimestamp);

    String getModifiedBy();

    void setModifiedBy(String modifiedBy);
}
