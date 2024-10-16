// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.cabinet;

import java.time.OffsetDateTime;
import org.dddml.clm.domain.*;
import org.dddml.clm.domain.AbstractCommand;

public abstract class AbstractCabinetCommandDto extends AbstractCommand {

    /**
     * Cabinet Id
     */
    private String cabinetId;

    public String getCabinetId()
    {
        return this.cabinetId;
    }

    public void setCabinetId(String cabinetId)
    {
        this.cabinetId = cabinetId;
    }

    /**
     * Version
     */
    private Long version;

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }


    public void copyTo(CabinetCommand command) {
        command.setCabinetId(this.getCabinetId());
        command.setVersion(this.getVersion());
        
        command.setRequesterId(this.getRequesterId());
        command.setCommandId(this.getCommandId());
    }

}
