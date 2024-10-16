// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.tagdeviceassociation;

import java.util.*;
import org.dddml.clm.domain.*;
import java.time.OffsetDateTime;
import org.dddml.clm.domain.AbstractCommand;

public abstract class AbstractTagDeviceAssociationCommand extends AbstractCommand implements TagDeviceAssociationCommand {

    private TagDeviceAssociationId tagDeviceAssociationId;

    public TagDeviceAssociationId getTagDeviceAssociationId()
    {
        return this.tagDeviceAssociationId;
    }

    public void setTagDeviceAssociationId(TagDeviceAssociationId tagDeviceAssociationId)
    {
        this.tagDeviceAssociationId = tagDeviceAssociationId;
    }

    private Long version;

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }


    public static abstract class AbstractCreateOrMergePatchTagDeviceAssociation extends AbstractTagDeviceAssociationCommand implements CreateOrMergePatchTagDeviceAssociation
    {

        private Boolean active;

        public Boolean getActive()
        {
            return this.active;
        }

        public void setActive(Boolean active)
        {
            this.active = active;
        }

    }

    public static abstract class AbstractCreateTagDeviceAssociation extends AbstractCreateOrMergePatchTagDeviceAssociation implements CreateTagDeviceAssociation
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

    }

    public static abstract class AbstractMergePatchTagDeviceAssociation extends AbstractCreateOrMergePatchTagDeviceAssociation implements MergePatchTagDeviceAssociation
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
        }

        private Boolean isPropertyActiveRemoved;

        public Boolean getIsPropertyActiveRemoved()
        {
            return this.isPropertyActiveRemoved;
        }

        public void setIsPropertyActiveRemoved(Boolean removed)
        {
            this.isPropertyActiveRemoved = removed;
        }


    }

    public static class SimpleCreateTagDeviceAssociation extends AbstractCreateTagDeviceAssociation
    {
    }

    
    public static class SimpleMergePatchTagDeviceAssociation extends AbstractMergePatchTagDeviceAssociation
    {
    }

    
    public static class SimpleDeleteTagDeviceAssociation extends AbstractTagDeviceAssociationCommand implements DeleteTagDeviceAssociation
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_DELETE;
        }
    }

    

}

