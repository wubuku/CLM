// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.clm.domain.tagpair;


public class DeleteTagPairDto extends AbstractTagPairCommandDto implements TagPairCommand.DeleteTagPair {

    public DeleteTagPairDto() {
        this.commandType = COMMAND_TYPE_DELETE;
    }

    @Override
    public String getCommandType() {
        return COMMAND_TYPE_DELETE;
    }

    public TagPairCommand.DeleteTagPair toDeleteTagPair()
    {
        AbstractTagPairCommand.SimpleDeleteTagPair command = new AbstractTagPairCommand.SimpleDeleteTagPair();
        ((AbstractTagPairCommandDto)this).copyTo(command);
        return command;
    }
}

