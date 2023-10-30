package buzz.yixiaobai.usercenter.domain.convert.struct;

import buzz.yixiaobai.usercenter.model.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

/**
 * 用户转换接口
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserStruct {

    @Mappings({
            @Mapping(target = "userPassword", ignore = true)
    })
    User convertUser(User user);
}
