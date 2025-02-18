package org.ikasan.spec.scheduled.context.model;

import java.io.Serializable;
import java.util.List;

public interface ContextDependency extends Serializable {

    String getContextIdentifier();

    void setContextIdentifier(String contextIdentifier);

    String getContextDependencyName();

    void setContextDependencyName(String contextDependencyName);

    LogicalGrouping getLogicalGrouping();

    void setLogicalGrouping(LogicalGrouping logicalGrouping);
}
